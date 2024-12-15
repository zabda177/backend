package dsi.soutenance.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dsi.soutenance.dto.DashboardStats;
import dsi.soutenance.dto.DemandeDto;
import dsi.soutenance.dto.DemandeurMoraleDto;
import dsi.soutenance.dto.DemandeurPhysiqueDto;
import dsi.soutenance.dto.PieceJointeDto;
import dsi.soutenance.dto.SoumissionDto;
import dsi.soutenance.model.Demande;
import dsi.soutenance.model.DemandeurMorale;
import dsi.soutenance.model.DemandeurPhysique;
import dsi.soutenance.model.PieceJointe;
import dsi.soutenance.repositorie.CommuneRepository;
import dsi.soutenance.repositorie.DemandeRepository;
import dsi.soutenance.repositorie.DemandeurMoraleRepository;
import dsi.soutenance.repositorie.DemandeurPhysiqueRepository;
import dsi.soutenance.repositorie.MaterielRepository;
import dsi.soutenance.repositorie.PieceJointeRepository;
import dsi.soutenance.repositorie.ProvinceRepository;
import dsi.soutenance.repositorie.RegionRepository;
import dsi.soutenance.service.DemandeService;
import dsi.soutenance.service.FileStorageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class DemandeServiceImpl implements DemandeService {

    @Autowired
    private DemandeurPhysiqueRepository demandeurPhysiqueRepository;
    @Autowired
    private DemandeurMoraleRepository demandeurMoraleRepository;
    @Autowired
    private PieceJointeRepository pieceJointeRepository;
    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private RegionRepository RegionRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private DemandeRepository demandeRepository;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    // Générer un code de 12 caractères
    public String generateCode() {
        StringBuilder code = new StringBuilder();
        code.append(getRandomCharacter("ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789")); // Premier caractère

        for (int i = 1; i < 12; i++) {
            code.append(getRandomCharacter(CHARACTERS));
        }

        return code.toString();
    }

    // Générer un numéro de demande (Long) de 12 chiffres
    public Long generateNumeroDemande() {
        StringBuilder numero = new StringBuilder();
        numero.append(RANDOM.nextInt(9) + 1); // Assurer que le premier chiffre n'est pas 0

        for (int i = 1; i < 12; i++) {
            numero.append(RANDOM.nextInt(10)); // Ajout de chiffres de 0 à 9
        }

        return Long.valueOf(numero.toString());
    }

    private char getRandomCharacter(String characters) {
        int index = RANDOM.nextInt(characters.length());
        return characters.charAt(index);
    }

    public DemandeDto saveSomission(SoumissionDto soumissionDto) {

        // Construction des entités depuis SoumissionDto
        DemandeurPhysique demandeurPhysique = SoumissionDto.toDemandeurPhysique(soumissionDto);
        DemandeurMorale demandeurMorale = SoumissionDto.toDemandeurMorale(soumissionDto);
        Demande demande = SoumissionDto.toDemande(soumissionDto);

        // Sauvegarde des entités dans leurs repositories respectifs
        demandeurPhysique = demandeurPhysiqueRepository.save(demandeurPhysique);
        demandeurMorale = demandeurMoraleRepository.save(demandeurMorale);

        demande.setNumeroDemande(generateNumeroDemande());
        demande.setCodeDemande(generateCode());
        demande.setDemandeurPhysique(demandeurPhysique);
        demande.setDemandeurMorale(demandeurMorale);

        demande = demandeRepository.save(demande);

        // Sauvegarde des pièces jointes
        for (PieceJointeDto pieceJointeDto : soumissionDto.getPieceJointes()) {
            PieceJointe pieceJointe = PieceJointeDto.toEntity(pieceJointeDto);
            pieceJointe.setLibelle(pieceJointeDto.getLibelle());
            pieceJointe.setUrl(pieceJointeDto.getUrl());
            pieceJointe.setDemande(demande);

            String fileName = null;
            try {
                fileName = fileStorageService.storeFile(pieceJointeDto.getFichier(), "piece jointes",
                        demande.getId().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pieceJointe.setUrl(fileName);

            pieceJointeRepository.save(pieceJointe);
        }

        // Retour de l'objet DTO
        return DemandeDto.fromEntity(demande);
    }

    public List<SoumissionDto> findById(Long id) {
        return demandeRepository.findById(id) // Récupère un Optional<Demande>
                .stream() // Convertit en Stream<Demande> s'il existe
                .map(this::toSoumissionDto) // Transforme Demande en SoumettreDemandeDTO
                .collect(Collectors.toList()); // Retourne une liste de SoumettreDemandeDTO
    }

    public List<SoumissionDto> findByIdAndStatutEnCoursDeTraitement() {
        List<Demande> demandes = demandeRepository.findByIdAndStatutEnCoursDeTraitement("En cours de traitement");
        return demandes.stream()
                .map(this::toSoumissionDto)
                .collect(Collectors.toList());
    }

    // operation de recherche sur tous les elements de la commune
    public List<SoumissionDto> findAll() {
        return demandeRepository.findAll().stream()
                .map(this::toSoumissionDto)
                .collect(Collectors.toList());
    }

    public List<SoumissionDto> findDemandeByNumeroAndCode(Long numeroDemande, String codeDemande) {
        List<Demande> demandes = demandeRepository.findByNumeroDemandeAndCodeDemande(numeroDemande, codeDemande);

        // Conversion des Demandes en SoumettreDemandeDTO
        return demandes.stream()
                .map(this::toSoumissionDto)
                .collect(Collectors.toList());
    }

    // Méthode de conversion d'une Demande en SoumettreDemandeDTO
    private SoumissionDto toSoumissionDto(Demande demande) {
        return SoumissionDto.builder()
                .id(demande.getId())
                .categorie(demande.getCategorie())
                .typeDemande(demande.getTypeDemande())
                .statut(demande.getStatut())
                .prix(demande.getPrix())
                .codeDemande(demande.getCodeDemande())
                .numeroDemande(demande.getNumeroDemande())
                .dateDepot(demande.getDateDepot())
                .dateValidation(demande.getDateValidation())
                .build();

    }

    private DemandeurPhysiqueDto toDemandeurPhysique(DemandeurPhysique demandeurPhysique) {
        return DemandeurPhysiqueDto.builder()
                .id(demandeurPhysique.getId())
                .nom(demandeurPhysique.getNom())
                .prenom(demandeurPhysique.getPrenom())
                .genre(demandeurPhysique.getGenre())
                .dateNaisse((demandeurPhysique.getDateNaisse()))
                .lieuResidence((demandeurPhysique.getLieuResidence()))
                .telephone1((demandeurPhysique.getTelephoneP1()))
                .telephone2((demandeurPhysique.getTelephoneP2()))
                .mail((demandeurPhysique.getMailP()))
                .typePiece((demandeurPhysique.getTypePiece()))
                .numPiece(demandeurPhysique.getNumPiece())
                .build();
    }

    private DemandeurMoraleDto toDemandeurMorale(DemandeurMorale demandeurMorale) {
        return DemandeurMoraleDto.builder()
                .id((demandeurMorale.getId()))
                .nomResponsable((demandeurMorale).getNomResponsable())
                .ifu(demandeurMorale.getIfu())
                .denomination(demandeurMorale.getDenomination())
                .siege(demandeurMorale.getSiege())
                .mail((demandeurMorale.getMail()))
                .telephone1((demandeurMorale.getTelephone1()))
                .telephone2((demandeurMorale.getTelephone2()))
                .build();
    }

    private PieceJointeDto toPieceJointeDto(PieceJointe pieceJointe) {
        return PieceJointeDto.builder()
                .id((pieceJointe.getId()))
                .libelle(pieceJointe.getLibelle())
                .url((pieceJointe.getUrl()))
                .build();
    }

    // liste des demandes d'une region
    public List<DemandeDto> getDemandesByRegion(Long regionId) {
        List<Demande> demandes = demandeRepository.findByCommuneProvinceRegionId(regionId);
        return demandes.stream()
                .map(DemandeDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Long deleteByDemandeurPhysiqueId(Long id) {
        return demandeRepository.deleteByDemandeurPhysiqueId(id);
    }

    public Long deleteByDemandeurMoraleId(Long id) {
        return demandeRepository.deleteByDemandeurMoraleId(id);
    }

    public List<SoumissionDto> getDemandeAccepter() {
        // Rechercher toutes les demandes avec le statut "Acceptée"
        List<Demande> demandes = demandeRepository.findByStatut("Acceptée");
        return demandes.stream()
                .map(this::toSoumissionDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteDemandeById(Long id) {
        Optional<Demande> demande = demandeRepository.findById(id);
        if (demande.isPresent()) {
            demandeurMoraleRepository.deleteByDemandeId(id);
            pieceJointeRepository.deleteByDemandeId(id);
            demandeurMoraleRepository.deleteByDemandeId(id);
            demandeRepository.deleteById(id);

        } else {
            throw new EntityNotFoundException("Province not found with id " + id);
        }
    }

    @Transactional
    public SoumissionDto valideDemande(Long id) {
        // Rechercher la demande par ID
        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Demande introuvable avec l'ID " + id));

        // Mettre à jour le statut de la demande à "Acceptée"
        demande.setStatut("Acceptée");

        // Enregistrer la demande mise à jour
        demande = demandeRepository.save(demande);

        // Retourner le DTO mis à jour
        return toSoumissionDto(demande);
    }

    @Transactional
    public SoumissionDto rejeteDemande(Long id) {
        // Rechercher la demande par ID
        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Demande introuvable avec l'ID " + id));

        // Mettre à jour le statut de la demande à "Acceptée"
        demande.setStatut("Rejetée");

        // Enregistrer la demande mise à jour
        demande = demandeRepository.save(demande);

        // Retourner le DTO mis à jour
        return toSoumissionDto(demande);
    }

    @Transactional
    public SoumissionDto validerDemande(Long id) {
        // Rechercher la demande par ID
        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Demande introuvable avec l'ID " + id));

        // Mettre à jour le statut de la demande à "Acceptée"
        demande.setStatut("Validée");

        // Enregistrer la demande mise à jour
        demande = demandeRepository.save(demande);
        // Retourner le DTO mis à jour
        return toSoumissionDto(demande);
    }

    @Transactional
    public SoumissionDto accepteDemande(Long id) {
        // Rechercher la demande par ID
        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Demande introuvable avec l'ID " + id));

        // Mettre à jour le statut de la demande à "Acceptée"
        demande.setStatut("Acceptée");

        // Enregistrer la demande mise à jour
        demande = demandeRepository.save(demande);

        // Retourner le DTO mis à jour
        return toSoumissionDto(demande);
        // private SoumissionDto
    }

    public List<SoumissionDto> getDemandeAcceptee() {
        List<Demande> demandes = demandeRepository.findByStatut("Acceptée");
        return demandes.stream()
                .map(this::toSoumissionDto)
                .collect(Collectors.toList());
    }

    public List<SoumissionDto> getDemandeRejetee() {
        List<Demande> demandes = demandeRepository.findByStatut("Rejetée");
        return demandes.stream()
                .map(this::toSoumissionDto)
                .collect(Collectors.toList());
    }

    public List<SoumissionDto> getDemandeValide() {
        List<Demande> demandes = demandeRepository.findByStatut("Validée");
        return demandes.stream()
                .map(this::toSoumissionDto)
                .collect(Collectors.toList());
    }

    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();

        stats.setTotalDemandes(demandeRepository.count()); // Total des demandes

        // Utilisez des méthodes appropriées pour compter les demandes par état
        stats.setDemandesRejetees(demandeRepository.countByStatut("Rejetée"));
        stats.setDemandesValidees(demandeRepository.countByStatut("Validée"));
        stats.setDemandesAcceptees(demandeRepository.countByStatut("Acceptée"));
        stats.setDemandesEncours(demandeRepository.countByStatut("En cours de traitement"));

        return stats;
    }

    // Gestion des cruds

    @Override
    // public Long save(DemandeDto demandeDto) {
    // Demande demande = DemandeDto.toEntity(demandeDto);
    // return demandeRepository.save(demande).getId();

    public Long save(DemandeDto demandeDto) {
        Demande demande = demandeDto.toEntity();
        return demandeRepository.save(demande).getId();
    }

    @Override
    public void saveAll(List<DemandeDto> demandeDtos) {
        List<Demande> demandes = demandeDtos.stream()
                .map(dto -> dto.toEntity()) // Ou .map(DemandeDto::toEntity)
                .collect(Collectors.toList());
        demandeRepository.saveAll(demandes);
        // demandeDtos.forEach(
        // demandeDto -> {
        // Demande demande = new demandeDto().toEntity();
        // demandeRepository.save(demande);});
    }

    @Override
    public List<DemandeDto> getAll() {
        return demandeRepository.findAll().stream().map(DemandeDto::fromEntity).toList();
    }

    @Override
    public DemandeDto getById(Long id) {
        return demandeRepository.findById(id).map(DemandeDto::fromEntity).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        demandeRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<DemandeDto> demandeDtos) {
        List<Demande> demandes = demandeDtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        demandeRepository.deleteAll(demandes);
    }
    /*
     * public DemandeServiceImpl(DemandeurMoraleRepository
     * demandeurMoraleRepository, DemandeRepository demandeRepository,
     * DemandeurPhysique demandeurPhysique) {
     * this.demandeRepository = demandeRepository;
     * this.demandeurMoraleRepository = demandeurMoraleRepository;
     * this.demandeurPhysiqueRepository = demandeurPhysiqueRepository;
     * }
     * 
     * public void enregistrerDemande(DemandeAvecFichiersDto demandeAvecFichiersDto)
     * {
     * enregistrerFichiers(demandeAvecFichiersDto.getPieceJointes());
     * 
     * DemandeurMorale demandeurMorale = demandeAvecFichiersDto.toEntity();
     * 
     * DemandeurMorale saveDemandeurMorale =
     * demandeurMoraleRepository.save(demandeurMorale);
     * 
     * Demande demande = new Demande();
     * demande.setDemandeurMorale(saveDemandeurMorale);
     * demandeRepository.save(demande);
     * }
     */

    private void enregistrerFichiers(List<PieceJointeDto> pieceJointes) {
        if (pieceJointes != null) {
            for (PieceJointeDto pieceJointeDto : pieceJointes) {
                MultipartFile fichier = pieceJointeDto.getFichier();
                if (fichier != null && !fichier.isEmpty()) {
                    String nomFichier = fichier.getOriginalFilename();
                    String cheminFichier = "uploads/" + nomFichier;

                    File dossier = new File("uploads");
                    if (!dossier.exists()) {
                        dossier.mkdirs(); // Crée le répertoire 'uploads' s'il n'existe pas
                    }

                    try {
                        fichier.transferTo(new File(cheminFichier));
                        System.out.println("Fichier enregistré : " + nomFichier);
                        pieceJointeDto.setUrl(cheminFichier);
                    } catch (IOException e) {
                        System.err.println("Erreur lors de l'enregistrement du fichier : " + e.getMessage());
                        // Gérer l'erreur de manière appropriée
                    }
                }
            }
        }
    }

}
