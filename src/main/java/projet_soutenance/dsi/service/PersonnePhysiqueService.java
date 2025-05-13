package projet_soutenance.dsi.service;


import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet_soutenance.dsi.DTO.PersonnePhysiqueDTO;
import projet_soutenance.dsi.mapper.PersonnePhysiqueMapper;
import projet_soutenance.dsi.model.PersonnePhysique;
import projet_soutenance.dsi.repositorie.PersonnePhysiqueRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonnePhysiqueService {

    private final PersonnePhysiqueRepository personnePhysiqueRepository;
    private final PersonnePhysiqueMapper personnePhysiqueMapper;

    @Autowired
    public PersonnePhysiqueService(PersonnePhysiqueRepository personnePhysiqueRepository, PersonnePhysiqueMapper personnePhysiqueMapper) {
        this.personnePhysiqueRepository = personnePhysiqueRepository;
        this.personnePhysiqueMapper = personnePhysiqueMapper;
    }

    public List<PersonnePhysiqueDTO> getAll() {
        return personnePhysiqueRepository.findAll().stream()
                .filter(personne -> personne.getDeleted() == null || !personne.getDeleted())
                .map(personnePhysiqueMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PersonnePhysiqueDTO> getById(Long id) {
        return personnePhysiqueRepository.findById(id)
                .filter(personne -> personne.getDeleted() == null || !personne.getDeleted())
                .map(personnePhysiqueMapper::toDTO);
    }

    @Transactional
    public PersonnePhysiqueDTO create(PersonnePhysiqueDTO personnePhysiqueDTO) {
        PersonnePhysique personnePhysique = personnePhysiqueMapper.toEntity(personnePhysiqueDTO);
        personnePhysique.setDeleted(false);
        PersonnePhysique savedPersonne = personnePhysiqueRepository.save(personnePhysique);
        return personnePhysiqueMapper.toDTO(savedPersonne);
    }

    @Transactional
    public Optional<PersonnePhysiqueDTO> update(Long id, PersonnePhysiqueDTO personnePhysiqueDTO) {
        return personnePhysiqueRepository.findById(id)
                .filter(personne -> personne.getDeleted() == null || !personne.getDeleted())
                .map(personne -> {
                    personne.setNom(personnePhysiqueDTO.getNom());
                    personne.setPrenom(personnePhysiqueDTO.getPrenom());
                    personne.setGenre(personnePhysiqueDTO.getGenre());
                    personne.setDateNaissance(personnePhysiqueDTO.getDateNaissance());
                    personne.setNationalite(personnePhysiqueDTO.getNationalite());
                    personne.setVilleResidance(personnePhysiqueDTO.getVilleResidance());
                    personne.setTelephone1PersonnePhysique(personnePhysiqueDTO.getTelephone1PersonnePhysique());
                    personne.setTelephone2PersonnePhysique(personnePhysiqueDTO.getTelephone2PersonnePhysique());
                    personne.setMailPersonnePhysique(personnePhysiqueDTO.getMailPersonnePhysique());
                    personne.setTypePiece(personnePhysiqueDTO.getTypePiece());
                    personne.setNumPiece(personnePhysiqueDTO.getNumPiece());
                    return personnePhysiqueMapper.toDTO(personnePhysiqueRepository.save(personne));
                });
    }

    @Transactional
    public boolean delete(Long id) {
        return personnePhysiqueRepository.findById(id)
                .map(personne -> {
                    personne.setDeleted(true);
                    personnePhysiqueRepository.save(personne);
                    return true;
                })
                .orElse(false);
    }


}
