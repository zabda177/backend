package dsi.soutenance.controleur;

import dsi.soutenance.dto.MaterielDto;
import dsi.soutenance.service.MaterielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiel")
@CrossOrigin("*")
public class MaterielController {

    @Autowired
    private MaterielService materielService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody MaterielDto materielDto) {
        return ResponseEntity.ok(materielService.save(materielDto));
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> createMultiple(@RequestBody List<MaterielDto> materiels) {
        materielService.saveAll(materiels);
        return ResponseEntity.ok("OK");
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody MaterielDto materielDto) {
        return ResponseEntity.ok(materielService.save(materielDto));
    }

    @GetMapping
    public ResponseEntity<List<MaterielDto>> getAll() {
        return ResponseEntity.ok(materielService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(materielService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        materielService.deleteById(id);
        return ResponseEntity.ok("Materiels Supprimer");
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteAll(@RequestBody List<MaterielDto> materiels) {
        materielService.deleteAll(materiels);
        return ResponseEntity.ok("Liste de Materiels supprimer");
    }
}
