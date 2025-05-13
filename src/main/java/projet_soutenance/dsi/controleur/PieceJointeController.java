package projet_soutenance.dsi.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projet_soutenance.dsi.DTO.PieceJointeDTO;
import projet_soutenance.dsi.service.PieceJointeService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/piece")
@CrossOrigin("*")
public class PieceJointeController {

    private final PieceJointeService pieceJointeService;

    @Autowired
    public PieceJointeController(PieceJointeService pieceJointeService) {
        this.pieceJointeService = pieceJointeService;
    }

    @GetMapping
    public ResponseEntity<List<PieceJointeDTO>> getAllPieceJointes() {
        List<PieceJointeDTO> piecesJointes = pieceJointeService.getAllPieceJointes();
        return ResponseEntity.ok(piecesJointes);
    }

    @GetMapping("/demande/{demandeId}")
    public ResponseEntity<List<PieceJointeDTO>> getPieceJointesByDemandeId(@PathVariable Long demandeId) {
        List<PieceJointeDTO> piecesJointes = pieceJointeService.getPieceJointesByDemandeId(demandeId);
        return ResponseEntity.ok(piecesJointes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PieceJointeDTO> getPieceJointeById(@PathVariable Long id) {
        return pieceJointeService.getPieceJointeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @PostMapping("/fichiers")
    public ResponseEntity<List<PieceJointeDTO>> createMultiplePiecesJointes(
            @RequestParam("demandeId") Long demandeId,
            @RequestParam Map<String, MultipartFile> fichiers) {

        List<PieceJointeDTO> createdPiecesJointes = new ArrayList<>();

        try {
            for (Map.Entry<String, MultipartFile> entry : fichiers.entrySet()) {
                if (!entry.getKey().equals("demandeId")) {
                    String libelle = entry.getKey().replace("fichiers[", "").replace("].fichier", "");
                    MultipartFile fichier = entry.getValue();

                    PieceJointeDTO pieceJointeDTO = pieceJointeService.createPieceJointe(demandeId, libelle, fichier);
                    createdPiecesJointes.add(pieceJointeDTO);
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(createdPiecesJointes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/fichiers-demande")
    public ResponseEntity<List<PieceJointeDTO>> createFichiersDemande(
            @RequestParam("demandeId") Long demandeId,
            @RequestParam Map<String, MultipartFile> allRequestParams) {

        List<PieceJointeDTO> createdPiecesJointes = new ArrayList<>();

        try {
            for (Map.Entry<String, MultipartFile> entry : allRequestParams.entrySet()) {
                if (!entry.getKey().equals("demandeId") && entry.getKey().contains(".fichier")) {
                    // Extraire l'index
                    String index = entry.getKey().substring(entry.getKey().indexOf("[") + 1, entry.getKey().indexOf("]"));

                    // Chercher le libellé correspondant
                    String libelleKey = "fichiers[" + index + "].libelle";
                    String libelle = allRequestParams.containsKey(libelleKey) ?
                            allRequestParams.get(libelleKey).toString() :
                            "Pièce " + index;

                    MultipartFile fichier = entry.getValue();

                    PieceJointeDTO pieceJointeDTO = pieceJointeService.createPieceJointe(demandeId, libelle, fichier);
                    createdPiecesJointes.add(pieceJointeDTO);
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(createdPiecesJointes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PieceJointeDTO> updatePieceJointe(
            @PathVariable Long id,
            @RequestParam(value = "libelle", required = false) String libelle,
            @RequestParam(value = "fichier", required = false) MultipartFile fichier) {
        try {
            return pieceJointeService.updatePieceJointe(id, libelle, fichier)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePieceJointe(@PathVariable Long id) {
        if (pieceJointeService.deletePieceJointe(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    // Méthode pour télécharger un fichier par son nom
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // Récupérer les informations sur le fichier à partir du nom
            Optional<PieceJointeDTO> pieceJointeOpt = pieceJointeService.getPieceJointeByFileName(fileName);

            if (pieceJointeOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            PieceJointeDTO pieceJointe = pieceJointeOpt.get();

            // Créer une ressource à partir du chemin du fichier
            File file = new File(pieceJointe.getUrl());
            Resource resource = new FileSystemResource(file);

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Détecter le type MIME du fichier
            String contentType = Files.probeContentType(Paths.get(file.getAbsolutePath()));
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // Configurer les en-têtes de la réponse
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}