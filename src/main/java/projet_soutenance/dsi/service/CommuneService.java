package projet_soutenance.dsi.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet_soutenance.dsi.DTO.CommuneDDTO;
import projet_soutenance.dsi.mapper.CommuneMapper;
import projet_soutenance.dsi.model.Commune;
import projet_soutenance.dsi.repositorie.CommuneRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommuneService {
    @Autowired
    private final CommuneRepository communeRepository;
    @Autowired
    private final CommuneMapper communeMapper;

    @Autowired
    public CommuneService(CommuneRepository communeRepository, CommuneMapper communeMapper) {
        this.communeRepository = communeRepository;
        this.communeMapper = communeMapper;
    }

    public List<CommuneDDTO> getAllCommunes() {
        return communeRepository.findAll().stream()
                .filter(commune -> !commune.getDelete())
                .map(communeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CommuneDDTO> getCommuneById(Long id) {
        return communeRepository.findById(id)
                .filter(commune -> !commune.getDelete())
                .map(communeMapper::toDTO);
    }

    @Transactional
    public CommuneDDTO createCommune(CommuneDDTO communeDDTO) {
        Commune commune = communeMapper.toEntity(communeDDTO);
        commune.setDelete(false);
        Commune savedCommune = communeRepository.save(commune);
        return communeMapper.toDTO(savedCommune);
    }

    @Transactional
    public Optional<CommuneDDTO> updateCommune(Long id, CommuneDDTO communeDDTO) {
        return communeRepository.findById(id)
                .filter(commune -> !commune.getDelete())
                .map(commune -> {
                    commune.setLibelle(communeDDTO.getLibelle());
                    commune.setCodeDgess(communeDDTO.getCodeDgess());
                    return communeMapper.toDTO(communeRepository.save(commune));
                });
    }

    @Transactional
    public boolean deleteCommune(Long id) {
        return communeRepository.findById(id)
                .map(commune -> {
                    commune.setDelete(true);
                    communeRepository.save(commune);
                    return true;
                })
                .orElse(false);
    }
}
