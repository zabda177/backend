package dsi.soutenance.controleur;

import dsi.soutenance.dto.RegionDto;
import dsi.soutenance.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/region")
@CrossOrigin
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody RegionDto regionDto) {
        return ResponseEntity.ok(regionService.save(regionDto));
    }

    @PostMapping("/multiple")
    public ResponseEntity<String> createMultiple(@RequestBody List<RegionDto> regionMutiple) {
        regionService.saveAll(regionMutiple);
        return ResponseEntity.ok("Operation success!");
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody RegionDto regionDto) {
        return ResponseEntity.ok(regionService.save(regionDto));
    }

    @GetMapping
    public ResponseEntity<List<RegionDto>> getAll() {
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(regionService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        regionService.deleteById(id);
        return ResponseEntity.ok("Region supprimer");
    }

    @DeleteMapping("/multiple")
    public ResponseEntity<String> deleteAll(@RequestBody List<RegionDto> regions) {
        regionService.deleteAll(regions);
        return ResponseEntity.ok("Liste de Region supprimer");
    }
}
