package dsi.soutenance.controleur;

import dsi.soutenance.dto.ProvinceDto;
import dsi.soutenance.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/province")
@CrossOrigin("*")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ProvinceDto provinceDto) {
        System.out.println(provinceDto.getLibelle());
        return ResponseEntity.ok(provinceService.save(provinceDto));
    }

    @PostMapping("/multiple")
    public ResponseEntity<?> createMultiple(@RequestBody List<ProvinceDto> provinces) {
        provinceService.saveAll(provinces);
        return ResponseEntity.ok("OK");
    }

    @PutMapping
    public ResponseEntity<Long> update(ProvinceDto provinceDto) {
        return ResponseEntity.ok(provinceService.save(provinceDto));
    }

    @GetMapping
    public ResponseEntity<List<ProvinceDto>> getAll() {
        return ResponseEntity.ok(provinceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(provinceService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        provinceService.deleteById(id);
        return ResponseEntity.ok("Province Supprimer");
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteAll(@RequestBody List<ProvinceDto> provinces) {
        provinceService.deleteAll(provinces);
        return ResponseEntity.ok("Liste de Province supprimer");
    }
}
