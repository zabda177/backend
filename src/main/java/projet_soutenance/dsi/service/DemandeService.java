package projet_soutenance.dsi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;
import projet_soutenance.dsi.DTO.*;
;
import projet_soutenance.dsi.mapper.DemandeMapper;

import projet_soutenance.dsi.mapper.PersonneMoraleMapper;
import projet_soutenance.dsi.mapper.PersonnePhysiqueMapper;
import projet_soutenance.dsi.model.Demande;
import projet_soutenance.dsi.repositorie.DemandeRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DemandeService {

    private final DemandeRepository demandeRepository;
    private final DemandeMapper demandeMapper;
    private final PersonnePhysiqueMapper personnePhysiqueMapper;
    private final PersonneMoraleMapper personneMoraleMapper;

    @Autowired
    public DemandeService(
            DemandeRepository demandeRepository,
            DemandeMapper demandeMapper,
            PersonnePhysiqueMapper personnePhysiqueMapper,
            PersonneMoraleMapper personneMoraleMapper) {
        this.demandeRepository = demandeRepository;
        this.demandeMapper = demandeMapper;
        this.personnePhysiqueMapper = personnePhysiqueMapper;
        this.personneMoraleMapper = personneMoraleMapper;
    }

    public List<DemandeDTO> getAllDemandes() {
        return demandeRepository.findAllWithPieceJointe()
                .stream()
                .map(demandeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<DemandeDTO> getDemandesByStatut(String statut) {
        return demandeRepository.findByStatutAndDeletedFalse(statut)
                .stream()
                .map(demandeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DashboardStatsDTO getDashboardStats() {
        long totalDemandes = demandeRepository.countByDeletedFalse();
        long enCours = demandeRepository.countByStatutAndDeletedFalse("EN_COURS");
        long acceptees = demandeRepository.countByStatutAndDeletedFalse("ACCEPTÉ");
        long rejetees = demandeRepository.countByStatutAndDeletedFalse("REJETÉ");
        long validees = demandeRepository.countByStatutAndDeletedFalse("VALIDÉ");

        return new DashboardStatsDTO(totalDemandes, enCours, acceptees, rejetees, validees);
    }

    @Transactional
    public Optional<DemandeDTO> getDemandeById(Long id) {
        return demandeRepository.findById(id)
                .filter(demande -> !demande.isDeleted())
                .map(demandeMapper::toDTO);
    }

    @Transactional
    public Optional<DemandeDTO> getDemandeByNumeroAndCode(Long numeroDemande, String codeDemande) {
        return demandeRepository.findByIdAndCodeDemande(numeroDemande, codeDemande)
                .filter(demande -> !demande.isDeleted())
                .map(demandeMapper::toDTO);
    }

    private String generateCodeDemande() {
        LocalDate now = LocalDate.now();
        long count = demandeRepository.count() + 1;
        return String.format("DEM-%d-%02d-%05d",
                now.getYear(),
                now.getMonthValue(),
                count);
    }

    @Transactional
    public DemandeDTO createDemande(DemandeDTO demandeDTO) {
        Demande demande = demandeMapper.toEntity(demandeDTO);
        demande.setDeleted(false);
        demande.setDateDepot(LocalDate.now());
        // Génération d'un code de demande unique
        demande.setCodeDemande(generateCodeDemande());
        // Toujours définir le statut à EN_COURS pour les nouvelles demandes
        demande.setStatut("EN_COURS");
        Demande savedDemande = demandeRepository.save(demande);
        return demandeMapper.toDTO(savedDemande);
    }



    @Transactional
    public DemandeDTO createDemandeWithDemandeurPhysique(PersonnePhysiqueDTO personnePhysiqueDTO) {
        // Créer une nouvelle demande avec le demandeur physique
        DemandeDTO demandeDTO = new DemandeDTO();
        demandeDTO.setPersonnePhysiqueDTO(personnePhysiqueDTO);
        demandeDTO.setTypeDemandeur("Personne physique"); // Important pour identifier le type
        return createDemande(demandeDTO);
    }


    @Transactional
    public DemandeDTO createDemandeWithDemandeurMorale(PersonneMoraleDTO personneMoraleDTO) {
        // Créer une nouvelle demande avec le demandeur moral
        DemandeDTO demandeDTO = new DemandeDTO();
        demandeDTO.setPersonneMoraleDTO(personneMoraleDTO);
        demandeDTO.setTypeDemandeur("Personne morale"); // Important pour identifier le type
        return createDemande(demandeDTO);
    }
    @Transactional
    public Optional<DemandeDTO> updateDemande(Long id, DemandeDTO demandeDTO) {
        return demandeRepository.findById(id)
                .filter(demande -> !demande.isDeleted())
                .map(demande -> {
                    // Préserver certaines valeurs qui ne doivent pas être modifiées
                    LocalDate dateDepotOriginal = demande.getDateDepot();
                    String codeDemandeOriginal = demande.getCodeDemande();

                    // Mettre à jour avec les nouvelles valeurs
                    Demande updatedDemande = demandeMapper.toEntity(demandeDTO);
                    updatedDemande.setId(id);
                    updatedDemande.setDateDepot(dateDepotOriginal);
                    updatedDemande.setCodeDemande(codeDemandeOriginal);

                    return demandeMapper.toDTO(demandeRepository.save(updatedDemande));
                });
    }

    @Transactional
    public Optional<DemandeDTO> changeStatut(Long id, String statut, String motifRejet) {
        return demandeRepository.findById(id)
                .filter(demande -> !demande.isDeleted())
                .map(demande -> {
                    demande.setStatut(statut);
                    if ("VALIDÉ".equalsIgnoreCase(statut)) {
                        demande.setDateValidation(LocalDate.now());
                    } else if ("REJETÉ".equalsIgnoreCase(statut)) {
                        demande.setDateRejet(LocalDate.now());
                        demande.setMotifRejet(motifRejet);
                    }
                    return demandeMapper.toDTO(demandeRepository.save(demande));
                });
    }

    // Méthode de commodité pour les changements de statut sans motif de rejet
    @Transactional
    public Optional<DemandeDTO> changeStatut(Long id, String statut) {
        return changeStatut(id, statut, null);
    }

    @Transactional
    public boolean deleteDemande(Long id) {
        return demandeRepository.findById(id)
                .map(demande -> {
                    demande.setDeleted(true);
                    demandeRepository.save(demande);
                    return true;
                })
                .orElse(false);
    }


    @Transactional
    public Optional<DemandeDTO> getDemandeByCode(String codeDemande) {
        return demandeRepository.findByCodeDemande(codeDemande)
                .filter(demande -> !demande.isDeleted())
                .map(demande -> {
                    DemandeDTO demandeDTO = demandeMapper.toDTO(demande);
                    // Ajout des informations sur l'étape actuelle pour le suivi
                    int step = mapStatutToStep(demande.getStatut());
                    demandeDTO.setStep(step);
                    demandeDTO.setStatusLabel(getStatusLabel(step));
                    return demandeDTO;
                });
    }

    /**
     * Convertit le statut de la demande en numéro d'étape pour le suivi
     * @param statut le statut actuel de la demande
     * @return le numéro d'étape correspondant
     */
    private int mapStatutToStep(String statut) {
        if (statut == null) return 0;

        switch (statut.toUpperCase()) {
            case "REJETÉ": return -1;
            case "EN_COURS": return 1;
            case "ACCEPTÉ": return 2;
            case "VALIDÉ": return 3;
            case "APPROUVÉ": return 4;
            case "DISPONIBLE": return 5;
            default: return 0;
        }
    }

    /**
     * Obtient le libellé du statut en fonction de l'étape
     * @param step Numéro de l'étape
     * @return Le libellé correspondant à l'étape
     */
    private String getStatusLabel(int step) {
        switch (step) {
            case -1: return "Demande Rejetée";
            case 0: return "État Initial";
            case 1: return "Demande Soumise";
            case 2: return "Demande Acceptée pour Traitement";
            case 3: return "Demande Validée par le Sous Comité";
            case 4: return "Approbation du Ministère";
            case 5: return "Agrément Disponible";
            default: return "Inconnu";
        }
    }



}


