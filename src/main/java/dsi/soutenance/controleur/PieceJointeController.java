package dsi.soutenance.controleur;

import dsi.soutenance.dto.DemandeDto;
import dsi.soutenance.dto.PieceJointeDto;
import dsi.soutenance.service.DemandeService;
import dsi.soutenance.service.PieceJointeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pieceJointe")
//@CrossOrigin("*")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PieceJointeController {
    @Autowired
    private PieceJointeService pieceJointeService;
    @Autowired
    private DemandeService demandeService;
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody PieceJointeDto pieceJointeDto) {
        return ResponseEntity.ok(pieceJointeService.save(pieceJointeDto));
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> createMultiple(@RequestBody List<PieceJointeDto> pieceJointes) {
        pieceJointeService.saveAll(pieceJointes);
        return ResponseEntity.ok("OK");
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody PieceJointeDto pieceJointeDto) {
        return ResponseEntity.ok(pieceJointeService.save(pieceJointeDto));
    }

    @GetMapping
    public ResponseEntity<List<PieceJointeDto>> getAll() {
        return ResponseEntity.ok(pieceJointeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pieceJointeService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        pieceJointeService.deleteById(id);
        return ResponseEntity.ok("Piecejointe Supprimer");
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteAll(@RequestBody List<PieceJointeDto> pieceJointes) {
        pieceJointeService.deleteAll(pieceJointes);
        return ResponseEntity.ok("Liste de Piece supprimer");
    }



    @PostMapping(value = "send-files", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> sendFile(@RequestParam("files") List<MultipartFile> files,
                                      @RequestParam("libelles") List<String> libelles,
                                      @RequestParam("demandeId") Long demandeId) {
        try {
            List<PieceJointeDto> pieceJointeDtos = new ArrayList<>();

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                String libelle = i < libelles.size() ? libelles.get(i) : file.getOriginalFilename();

                PieceJointeDto pieceJointeDto = PieceJointeDto.builder()
                        .libelle(libelle)
                        .fichier(file)
                        .demandeId(demandeId)
                        .url(null) // L'URL sera générée par votre service
                        .build();

                pieceJointeDtos.add(pieceJointeDto);
            }

            List<PieceJointeDto> savedPieces = pieceJointeService.savePiecesJointes(pieceJointeDtos, demandeId);
            DemandeDto demandeDto = demandeService.getById(demandeId);

            return ResponseEntity.ok(demandeDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la sauvegarde des pièces jointes : " + e.getMessage());
        }
    }


//public ResponseEntity<?> sendFile(@ModelAttribute List<PieceJointeDto> pieceJointeDtos, @RequestParam long demandeId) {
//    try {
//        // Sauvegarde des pièces jointes
//        List<PieceJointeDto> savedPieces = pieceJointeService.savePiecesJointes(pieceJointeDtos, demandeId);
//
//        // Récupérer la demande mise à jour
//        DemandeDto demandeDto = demandeService.getById(demandeId);
//
//        return ResponseEntity.ok(demandeDto);
//        } catch (Exception e) {
//        // Gestion des exceptions
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la sauvegarde des pièces jointes : " + e.getMessage());
//    }
//}



}
