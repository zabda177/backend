package dsi.soutenance.controleur;

import dsi.soutenance.dto.PieceJointeDto;
import dsi.soutenance.service.PieceJointeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pieceJointe")
@CrossOrigin("*")
public class PieceJointeController {
    @Autowired
    private PieceJointeService pieceJointeService;

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
}
