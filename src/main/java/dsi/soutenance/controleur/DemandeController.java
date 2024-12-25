package dsi.soutenance.controleur;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import dsi.soutenance.model.Demande;
import dsi.soutenance.model.DemandeurPhysique;
import dsi.soutenance.model.PieceJointe;
import dsi.soutenance.repositorie.DemandeRepository;
import dsi.soutenance.repositorie.DemandeurMoraleRepository;
import dsi.soutenance.repositorie.DemandeurPhysiqueRepository;
import dsi.soutenance.repositorie.PieceJointeRepository;
import dsi.soutenance.service.PieceJointeService;
import dsi.soutenance.serviceimpl.DemandeurPhysiqueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import dsi.soutenance.dto.DashboardStats;
import dsi.soutenance.dto.DemandeAvecFichiersDto;
import dsi.soutenance.dto.DemandeDto;
import dsi.soutenance.dto.DemandeurMoraleDto;
import dsi.soutenance.dto.PieceJointeDto;
import dsi.soutenance.dto.SoumissionDto;
import dsi.soutenance.model.DemandeurMorale;
import dsi.soutenance.service.DemandeService;
import dsi.soutenance.service.FileStorageService;
import dsi.soutenance.serviceimpl.DemandeurMoraleServiceImpl;

@RestController
@RequestMapping("/api/demande")
//@CrossOrigin("*")
@CrossOrigin(origins = {"http://localhost:4200",  "http://127.0.0.1:4200"})
public class DemandeController {
    @Autowired
    DemandeurMoraleRepository demandeurMoraleRepository;
    @Autowired
    DemandeurPhysiqueRepository demandeurPhysiqueRepository;
    @Autowired
    DemandeRepository demandeRepository;
    @Autowired
    PieceJointeRepository pieceJointeRepository;

    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    private DemandeService demandeService;
    @Autowired
    private PieceJointeService pieceJointeService;
    @Autowired
    private final DemandeurMoraleServiceImpl demandeurMoraleService;


    public DemandeController(dsi.soutenance.serviceimpl.DemandeurMoraleServiceImpl demandeurMoraleService) {
        this.demandeurMoraleService = demandeurMoraleService;
    }



    @PostMapping(value = "/soumission")
    public ResponseEntity<DemandeDto> soumissionDto(@ModelAttribute SoumissionDto soumissionDto) {
            DemandeDto demandeDto = demandeService.saveSoumission(soumissionDto);
            return ResponseEntity.ok(demandeDto);
    }

//    @PostMapping(value = "send-demande-files", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//    public ResponseEntity<?> sendFile(@ModelAttribute List<PieceJointeDto> pieceJointeDtos, long demandeId) {
//        DemandeDto demandeDto = demandeService.saveSoumission(pieceJointeDto);
//        return ResponseEntity.ok(demandeDto);
//  }
@PostMapping(value = "send-files", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//public ResponseEntity<?> sendFile(@ModelAttribute List<PieceJointeDto> pieceJointeDtos, @RequestParam long demandeId) {
//    try {
//        // Sauvegarde des pièces jointes
//        List<PieceJointeDto> savedPieces = pieceJointeService.savePiecesJointes(pieceJointeDtos, demandeId);
//
//        // Récupérer la demande mise à jour
//        DemandeDto demandeDto = demandeService.getById(demandeId);
//
//        return ResponseEntity.ok(demandeDto);
//    } catch (Exception e) {
//        // Gestion des exceptions
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la sauvegarde des pièces jointes : " + e.getMessage());
//    }
//}

public ResponseEntity<?> sendFile(
        @RequestPart("files") List<MultipartFile> files,
        @RequestParam long demandeId) {
    try {
        // Convertir les fichiers en DTO ou entités
        List<PieceJointeDto> pieceJointeDtos = files.stream().map(file -> {
            PieceJointeDto dto = new PieceJointeDto();
//            dto.setNomFichier(file.getOriginalFilename());
//            dto.setContenu(file.getBytes());
            return dto;
        }).toList();

        // Sauvegarde des pièces jointes
        List<PieceJointeDto> savedPieces = pieceJointeService.savePiecesJointes(pieceJointeDtos, demandeId);

        // Récupérer la demande mise à jour
        DemandeDto demandeDto = demandeService.getById(demandeId);

        return ResponseEntity.ok(demandeDto);
    } catch (Exception e) {
        // Gestion des exceptions
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur lors de la sauvegarde des pièces jointes : " + e.getMessage());
    }
}

    @GetMapping("/listSoumission")
    public List<SoumissionDto> findAll() {
        return demandeService.findAll();
    }

    // liste des elements d'une region
    @GetMapping("region/{regionId}")
    public List<DemandeDto> getDemandesByRegion(@PathVariable Long regionId) {
        return demandeService.getDemandesByRegion(regionId);
    }

    @GetMapping("/demandes/acceptees/{Id}")
    public List<SoumissionDto> findById(@PathVariable Long Id) {
        return demandeService.findById(Id);
    }

    @GetMapping("/demande/en-cours")
    public ResponseEntity<List<SoumissionDto>> getDemandeEnCours() {
        List<SoumissionDto> demandes = demandeService.findByIdAndStatutEnCoursDeTraitement();
        return ResponseEntity.ok(demandes);
    }
    @PostMapping("/demande")
    public ResponseEntity<DemandeDto> saveSomission(@RequestBody  SoumissionDto soumissionDto) {
        // Enregistrer les données de demandes
        DemandeDto demandeDto= demandeService.saveSoumission(soumissionDto);
        return ResponseEntity.ok(demandeDto);
    }



    @PostMapping("/demandes")
    public ResponseEntity<Long> create(@RequestBody DemandeDto demandeDto) {
        // Enregistrer les ddonnées de demandes

        return ResponseEntity.ok(demandeService.save(demandeDto));
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> createMultiple(@RequestBody List<DemandeDto> demandes) {
        demandeService.saveAll(demandes);
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/demande")
    public ResponseEntity<Long> update(@RequestBody DemandeDto demandeDto) {
        return ResponseEntity.ok(demandeService.save(demandeDto));
    }

    @GetMapping("/listDemande")
    public ResponseEntity<List<DemandeDto>> getAll() {
        return ResponseEntity.ok(demandeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(demandeService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        demandeService.deleteById(id);
        return ResponseEntity.ok("Demande Supprimer");
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteAll(@RequestBody List<DemandeDto> demandes) {
        demandeService.deleteAll(demandes);
        return ResponseEntity.ok("Liste de demande supprimer");
    }

    @GetMapping("/demande/{numeroDemande}/{codeDemande}")
    public List<SoumissionDto> rechercheDemande(
            @PathVariable Long numeroDemande,
            @PathVariable String codeDemande) {
        return demandeService.findDemandeByNumeroAndCode(numeroDemande, codeDemande);
    }

    @PutMapping("/{id}/accepte")
    public ResponseEntity<SoumissionDto> accepterDemande(@PathVariable Long id) {
        SoumissionDto demandeAccepte = demandeService.accepteDemande(id);
        return ResponseEntity.ok(demandeAccepte);
    }

    @PutMapping("/{id}/rejete")
    public ResponseEntity<SoumissionDto> rejeterDemande(@PathVariable Long id) {
        SoumissionDto demandeRejete = demandeService.rejeteDemande(id);
        return ResponseEntity.ok(demandeRejete);

    }

    @GetMapping("demande-details/{id}")
    public ResponseEntity<Map<String, Object>> getDemandeDetails(@PathVariable Long id) {
        Map<String, Object> response = demandeService.getDemandeDetails(id);
        return ResponseEntity.ok(response);
    }



    @PutMapping("/{id}/valide")
    public ResponseEntity<SoumissionDto> valideDemande(@PathVariable Long id) {
        SoumissionDto demandeValide = demandeService.valideDemande(id);
        return ResponseEntity.ok(demandeValide);
    }

    @GetMapping("demandes/acceptees")
    public ResponseEntity<List<SoumissionDto>> getDemandeAcceptee() {
        List<SoumissionDto> demandeAcceptee = demandeService.getDemandeAcceptee();
        return ResponseEntity.ok(demandeAcceptee);
    }

    @GetMapping("/rejetee")
    public ResponseEntity<List<SoumissionDto>> getDemandeRejetee() {
        List<SoumissionDto> demandeRejetee = demandeService.getDemandeRejetee();
        return ResponseEntity.ok(demandeRejetee);
    }

    @GetMapping("/validee")
    public ResponseEntity<List<SoumissionDto>> getDemandeValide() {
        List<SoumissionDto> demandeValidee = demandeService.getDemandeValide();
        return ResponseEntity.ok(demandeValidee);
    }

    @GetMapping("/stats")
    public DashboardStats getDashboardStats() {
        return demandeService.getDashboardStats();
    }



    @PostMapping("/envoyer-demande-avec-fichiers")
    public ResponseEntity<String> recevoirDemande(@ModelAttribute DemandeAvecFichiersDto formulaire) {
        // Traitement des données
        System.out.println("Formulaire reçu : " + formulaire);

        // Exemple : Sauvegarder les fichiers
        formulaire.getPieceJointes().forEach(piece -> {
            MultipartFile fichier = piece.getFichier();
            System.out.println("Fichier reçu : " + fichier.getOriginalFilename());
            // Ajoutez votre logique de sauvegarde ici
        });

        // Enregistrer les fichiers
        enregistrerFichiers(formulaire.getPieceJointes());

        // Créer l'entité DemandeurMorale à partir du DTO
        DemandeurMorale demandeurMorale = formulaire.toEntity();
        // Enregistrer les données du formulaire dans la base de données
        Long demandeurMoraleId = demandeurMoraleService.save(DemandeurMoraleDto.fromEntity(demandeurMorale));
        // creation de demande

        return ResponseEntity.status(HttpStatus.OK).body("Formulaire reçu avec succès.");
    }

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
