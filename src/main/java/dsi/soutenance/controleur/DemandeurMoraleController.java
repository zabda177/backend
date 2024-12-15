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

import dsi.soutenance.dto.DemandeurMoraleDto;
import dsi.soutenance.service.DemandeurMoraleService;

@RestController
@RequestMapping("/api/demandeurMorale")
@CrossOrigin("*")
public class DemandeurMoraleController {

    @Autowired
    private DemandeurMoraleService demandeurMoraleService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody DemandeurMoraleDto demandeurMoraleDto) {
        return ResponseEntity.ok(demandeurMoraleService.save(demandeurMoraleDto));
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> createMultiple(@RequestBody List<DemandeurMoraleDto> demandeurMorales) {
        demandeurMoraleService.saveAll(demandeurMorales);
        return ResponseEntity.ok("OK");
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody DemandeurMoraleDto demandeurMoraleDto) {
        return ResponseEntity.ok(demandeurMoraleService.save(demandeurMoraleDto));
    }

    @GetMapping
    public ResponseEntity<List<DemandeurMoraleDto>> getAll() {
        return ResponseEntity.ok(demandeurMoraleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(demandeurMoraleService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        demandeurMoraleService.deleteById(id);
        return ResponseEntity.ok("Demandeur  Supprimer");
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteAll(@RequestBody List<DemandeurMoraleDto> demandeurMorales) {
        demandeurMoraleService.deleteAll(demandeurMorales);
        return ResponseEntity.ok("Liste de Demande supprimer");
    }
    
}
