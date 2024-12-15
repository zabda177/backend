package dsi.soutenance.controleur;

import dsi.soutenance.dto.CommuneDto;
import dsi.soutenance.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commune")
@CrossOrigin("*")
public class CommuneController {

    @Autowired
    private CommuneService communeService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CommuneDto communeDto) {
        return ResponseEntity.ok(communeService.save(communeDto));
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> createMultiple(@RequestBody List<CommuneDto> communes) {
        communeService.saveAll(communes);
        return ResponseEntity.ok("OK");
    }


    @PutMapping
    public ResponseEntity<Long> update(CommuneDto communeDto) {
        return ResponseEntity.ok(communeService.save(communeDto));
    }

    @GetMapping
    public ResponseEntity<List<CommuneDto>> getAll() {
        return ResponseEntity.ok(communeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(communeService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        communeService.deleteById(id);
        return ResponseEntity.ok("Commune Supprimer");
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteAll(@RequestBody List<CommuneDto> communes) {
        communeService.deleteAll(communes);
        return ResponseEntity.ok("Liste de Communes supprimer");
    }

}
