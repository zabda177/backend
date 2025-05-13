package projet_soutenance.dsi.controleur;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_soutenance.dsi.DTO.ProvinceDTO;
import projet_soutenance.dsi.service.ProvinceService;


import java.util.List;

@RestController
@RequestMapping("/api/province")
public class ProvinceController {

    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping
    public ResponseEntity<List<ProvinceDTO>> getAllProvinces() {
        List<ProvinceDTO> provinces = provinceService.getAllProvinces();
        return ResponseEntity.ok(provinces);
    }

    @GetMapping("/province/{id}")
    public ResponseEntity<List<ProvinceDTO>> getProvincesByRegionId(@PathVariable Long regionId) {
        List<ProvinceDTO> provinces = provinceService.getProvincesByRegionId(regionId);
        return ResponseEntity.ok(provinces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinceDTO> getProvinceById(@PathVariable Long id) {
        return provinceService.getProvinceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProvinceDTO> createProvince(@RequestBody ProvinceDTO provinceDTO) {
        try {
            ProvinceDTO createdProvince = provinceService.createProvince(provinceDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProvince);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProvinceDTO> updateProvince(@PathVariable Long id, @RequestBody ProvinceDTO provinceDTO) {
        try {
            return provinceService.updateProvince(id, provinceDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
        if (provinceService.deleteProvince(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
