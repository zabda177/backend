package projet_soutenance.dsi.service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet_soutenance.dsi.DTO.PersonneMoraleDTO;
import projet_soutenance.dsi.mapper.PersonneMoraleMapper;
import projet_soutenance.dsi.model.PersonneMorale;
import projet_soutenance.dsi.repositorie.PersonneMoraleRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonneMoraleService {
    private final PersonneMoraleRepository personneMoraleRepository;
    private final PersonneMoraleMapper personneMoraleMapper;

    @Autowired
    public PersonneMoraleService(PersonneMoraleRepository personneMoraleRepository, PersonneMoraleMapper personneMoraleMapper) {
        this.personneMoraleRepository = personneMoraleRepository;
        this.personneMoraleMapper = personneMoraleMapper;
    }

    public List<PersonneMoraleDTO> getAll() {
        return personneMoraleRepository.findAll().stream()
                .filter(personne -> !personne.isDeleted())
                .map(personneMoraleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PersonneMoraleDTO> getById(Long id) {
        return personneMoraleRepository.findById(id)
                .filter(personne -> !personne.isDeleted())
                .map(personneMoraleMapper::toDTO);
    }

    @Transactional
    public PersonneMoraleDTO create(PersonneMoraleDTO personneMoraleDTO) {
        PersonneMorale personneMorale = personneMoraleMapper.toEntity(personneMoraleDTO);
        personneMorale.setDeleted(false);
        PersonneMorale savedPersonne = personneMoraleRepository.save(personneMorale);
        return personneMoraleMapper.toDTO(savedPersonne);
    }

    @Transactional
    public Optional<PersonneMoraleDTO> update(Long id, PersonneMoraleDTO personneMoraleDTO) {
        return personneMoraleRepository.findById(id)
                .filter(personne -> !personne.isDeleted())
                .map(personne -> {
                    personne.setNomResponsable(personneMoraleDTO.getNomResponsable());
                    personne.setPrenomResponsable(personneMoraleDTO.getNomResponsable());
                    personne.setDenomination(personneMoraleDTO.getDenomination());
                    personne.setSiege(personneMoraleDTO.getSiege());
                    personne.setIfu(personneMoraleDTO.getIfu());
                    personne.setMailPersonneMorale(personneMoraleDTO.getMailPersonneMorale());
                    personne.setTelephone1PersonneMorale(personneMoraleDTO.getTelephone1PersonneMorale());
                    personne.setTelephone2PersonneMorale(personneMoraleDTO.getTelephone2PersonneMorale());
                    return personneMoraleMapper.toDTO(personneMoraleRepository.save(personne));
                });
    }

    @Transactional
    public boolean delete(Long id) {
        return personneMoraleRepository.findById(id)
                .map(personne -> {
                    personne.setDeleted(true);
                    personneMoraleRepository.save(personne);
                    return true;
                })
                .orElse(false);
    }

}
