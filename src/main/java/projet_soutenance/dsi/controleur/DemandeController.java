package projet_soutenance.dsi.controleur;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import projet_soutenance.dsi.DTO.DashboardStatsDTO;
import projet_soutenance.dsi.DTO.DemandeDTO;

import projet_soutenance.dsi.DTO.PersonneMoraleDTO;
import projet_soutenance.dsi.DTO.PersonnePhysiqueDTO;
import projet_soutenance.dsi.model.DashboardStats;
import projet_soutenance.dsi.service.DemandeService;
import projet_soutenance.dsi.service.PieceJointeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/demande")
@CrossOrigin("*") // Pour correspondre au frontend
public class DemandeController {
    private final DemandeService demandeService;
    private final PieceJointeService pieceJointeService;
    @Autowired
    public DemandeController(DemandeService demandeService, PieceJointeService pieceJointeService) {
        this.demandeService = demandeService;
        this.pieceJointeService = pieceJointeService; // Initialisation correcte
    }


    @GetMapping
    public ResponseEntity<List<DemandeDTO>> getAllDemandes() {
        List<DemandeDTO> demandes = demandeService.getAllDemandes();
        return ResponseEntity.ok(demandes);
    }
    @Transactional
    @GetMapping("/status/listSoumission")
    public ResponseEntity<List<DemandeDTO>> getDemandeEncours() {
        List<DemandeDTO> demandes = demandeService.getDemandesByStatut("EN_COURS");
        return ResponseEntity.ok(demandes);
    }
    @Transactional
    @GetMapping("/accepte")
    public ResponseEntity<List<DemandeDTO>> getDemandeAccepte() {
        List<DemandeDTO> demandes = demandeService.getDemandesByStatut("ACCEPTÉ");
        return ResponseEntity.ok(demandes);
    }
    @Transactional
    @GetMapping("/rejete")
    public ResponseEntity<List<DemandeDTO>> getDemandeRejete() {
        List<DemandeDTO> demandes = demandeService.getDemandesByStatut("REJETÉ");
        return ResponseEntity.ok(demandes);
    }
    @Transactional
    @GetMapping("/valide")
    public ResponseEntity<List<DemandeDTO>> getDemandeValide() {
        List<DemandeDTO> demandes = demandeService.getDemandesByStatut("VALIDÉ");
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        DashboardStatsDTO stats = demandeService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemandeDTO> getDemandeById(@PathVariable Long id) {
        return demandeService.getDemandeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/demandes{id}")
    public ResponseEntity<DemandeDTO> getDemandeByIdAlternateEndpoint(@PathVariable Long id) {
        // Endpoint alternatif pour correspondre au frontend
        return getDemandeById(id);
    }


    @PostMapping("/create")
    public ResponseEntity<DemandeDTO> createDemande(@RequestBody DemandeDTO demandeDTO) {
        DemandeDTO createdDemande = demandeService.createDemande(demandeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDemande);
    }

    @PostMapping("/demandeur/physique")
    public ResponseEntity<DemandeDTO> envoyerDonneesDemandeurPhysique(@RequestBody PersonnePhysiqueDTO personnePhysiqueDTO) {
        DemandeDTO createdDemande = demandeService.createDemandeWithDemandeurPhysique(personnePhysiqueDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDemande);
    }

    @PostMapping("/demandeur/morale")
    public ResponseEntity<DemandeDTO> envoyerDonneesDemandeurMorale(@RequestBody PersonneMoraleDTO personneMoraleDTO) {
        DemandeDTO createdDemande = demandeService.createDemandeWithDemandeurMorale(personneMoraleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDemande);
    }

    @PostMapping("/soumission-data")
    public ResponseEntity<DemandeDTO> envoyerDonneesDemande(@RequestBody DemandeDTO demandeDTO) {
        DemandeDTO createdDemande = demandeService.createDemande(demandeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDemande);
    }

    @PostMapping("/soumission")
    public ResponseEntity<DemandeDTO> envoyerDemande(@RequestBody DemandeDTO demandeDTO) {
        // Cette méthode pourrait inclure une logique spécifique pour le traitement des formulaires complexes
        DemandeDTO createdDemande = demandeService.createDemande(demandeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDemande);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DemandeDTO> updateDemande(@PathVariable Long id, @RequestBody DemandeDTO demandeDTO) {
        return demandeService.updateDemande(id, demandeDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/accepte")
    public ResponseEntity<DemandeDTO> accepterDemande(@PathVariable Long id) {
        return demandeService.changeStatut(id, "ACCEPTÉ")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/rejete")
    public ResponseEntity<DemandeDTO> rejeterDemande(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> rejetInfo) {

        String motifRejet = rejetInfo != null ? rejetInfo.get("motifRejet") : null;

        return demandeService.changeStatut(id, "REJETÉ", motifRejet)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}/valide")
    public ResponseEntity<DemandeDTO> validerDemande(@PathVariable Long id) {
        return demandeService.changeStatut(id, "VALIDÉ")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<DemandeDTO> updateStatut(@PathVariable Long id, @RequestBody Map<String, String> statutMap) {
        String statut = statutMap.get("statut");
        if (statut == null || statut.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return demandeService.changeStatut(id, statut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Long id) {
        if (demandeService.deleteDemande(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{numeroDemande}/{codeDemande}")
    public ResponseEntity<DemandeDTO> getDemandeDetails(
            @PathVariable Long numeroDemande,
            @PathVariable String codeDemande) {
        return demandeService.getDemandeByNumeroAndCode(numeroDemande, codeDemande)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
 }
    @PutMapping("/{id}/reconsiderer")
    public ResponseEntity<DemandeDTO> reconsidererDemande(@PathVariable Long id) {
        // Changer le statut de la demande de "REJETÉ" à "EN_COURS" ou autre statut approprié
        return demandeService.changeStatut(id, "EN_COURS")
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/soumission-complete")
    public ResponseEntity<DemandeDTO> soumissionComplete(
            @RequestParam(value = "demande", required = true) String demandeJson,
            @RequestParam(value = "personnePhysique", required = false) String personnePhysiqueJson,
            @RequestParam(value = "personneMorale", required = false) String personneMoraleJson,
            @RequestParam(value = "fichiers", required = false) MultipartFile[] fichiers) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Configurer l'ObjectMapper pour ignorer les propriétés inconnues
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // Enregistrer le module pour la prise en charge des dates Java 8
            objectMapper.registerModule(new JavaTimeModule());

            // Vérifier que les JSON ne sont pas vides ou malformés
            if (demandeJson == null || demandeJson.trim().isEmpty() || demandeJson.contains("undefined")) {
                return ResponseEntity.badRequest().body(null);
            }

            // Conversion des JSON en objets
            DemandeDTO demandeDTO;
            try {
                demandeDTO = objectMapper.readValue(demandeJson, DemandeDTO.class);
            } catch (Exception e) {
                // Log détaillé de l'erreur
                System.err.println("Erreur de parsing JSON pour demande: " + e.getMessage());
                System.err.println("JSON reçu: " + demandeJson);
                return ResponseEntity.badRequest().body(null);
            }

            // Déterminer le type de demandeur
            if (personnePhysiqueJson != null && !personnePhysiqueJson.trim().isEmpty() && !personnePhysiqueJson.contains("undefined")) {
                try {
                    PersonnePhysiqueDTO personnePhysiqueDTO = objectMapper.readValue(personnePhysiqueJson, PersonnePhysiqueDTO.class);
                    demandeDTO.setPersonnePhysiqueDTO(personnePhysiqueDTO);
                    demandeDTO.setTypeDemandeur("Personne physique");
                } catch (Exception e) {
                    System.err.println("Erreur de parsing JSON pour personne physique: " + e.getMessage());
                    return ResponseEntity.badRequest().body(null);
                }
            } else if (personneMoraleJson != null && !personneMoraleJson.trim().isEmpty() && !personneMoraleJson.contains("undefined")) {
                try {
                    PersonneMoraleDTO personneMoraleDTO = objectMapper.readValue(personneMoraleJson, PersonneMoraleDTO.class);
                    demandeDTO.setPersonneMoraleDTO(personneMoraleDTO);
                    demandeDTO.setTypeDemandeur("Personne morale");
                } catch (Exception e) {
                    System.err.println("Erreur de parsing JSON pour personne morale: " + e.getMessage());
                    return ResponseEntity.badRequest().body(null);
                }
            }

            // Créer la demande
            DemandeDTO createdDemande = demandeService.createDemande(demandeDTO);

            // Gérer les pièces jointes si présentes
            if (fichiers != null && fichiers.length > 0) {
                pieceJointeService.savePiecesJointes(createdDemande.getId(), fichiers);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(createdDemande);
        } catch (Exception e) {
            // Log l'exception complète pour le débogage
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/demandes/code/{codeDemande}")
    public ResponseEntity<DemandeDTO> getDemandeByCode(@PathVariable String codeDemande) {
        return demandeService.getDemandeByCode(codeDemande)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }





}




