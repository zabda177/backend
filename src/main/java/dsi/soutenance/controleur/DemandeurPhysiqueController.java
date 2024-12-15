package dsi.soutenance.controleur;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dsi.soutenance.dto.DemandeurPhysiqueDto;
import dsi.soutenance.service.DemandeurPhysiqueService;

@RestController
@RequestMapping("/api/demandeurPhysique")
@CrossOrigin("*")

public class DemandeurPhysiqueController {
    @Autowired
    private DemandeurPhysiqueService demandeurPhysiqueService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody DemandeurPhysiqueDto demandeurPhysiqueDto) {
        return ResponseEntity.ok(demandeurPhysiqueService.save(demandeurPhysiqueDto));
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> createMultiple(@RequestBody List<DemandeurPhysiqueDto> demandeurPhysiques) {
        demandeurPhysiqueService.saveAll(demandeurPhysiques);
        return ResponseEntity.ok("OK");
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody DemandeurPhysiqueDto demandeurPhysiqueDto) {
        return ResponseEntity.ok(demandeurPhysiqueService.save(demandeurPhysiqueDto));
    }

    @GetMapping
    public ResponseEntity<List<DemandeurPhysiqueDto>> getAll() {
        return ResponseEntity.ok(demandeurPhysiqueService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(demandeurPhysiqueService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        demandeurPhysiqueService.deleteById(id);
        return ResponseEntity.ok("Demandeurs Supprimer");
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteAll(@RequestBody List<DemandeurPhysiqueDto> demandeurPhysiques) {
        demandeurPhysiqueService.deleteAll(demandeurPhysiques);
        return ResponseEntity.ok("Liste de Piece supprimer");
    }

}
