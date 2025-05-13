    package projet_soutenance.dsi.controleur;


    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import projet_soutenance.dsi.DTO.RegionDTO;
    import projet_soutenance.dsi.service.RegionService;


    import java.util.List;

    @RestController
    @RequestMapping("/api/regions")
    public class RegionController {

        private final RegionService regionService;

        @Autowired
        public RegionController(RegionService regionService) {
            this.regionService = regionService;
        }

        @GetMapping
        public ResponseEntity<List<RegionDTO>> getAllRegions() {
            List<RegionDTO> regions = regionService.getAllRegions();
            return ResponseEntity.ok(regions);
        }

        @GetMapping("/{id}")
        public ResponseEntity<RegionDTO> getRegionById(@PathVariable Long id) {
            return regionService.getRegionById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<RegionDTO> createRegion(@RequestBody RegionDTO regionDTO) {
            RegionDTO createdRegion = regionService.createRegion(regionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRegion);
        }

        @PutMapping("/{id}")
        public ResponseEntity<RegionDTO> updateRegion(@PathVariable Long id, @RequestBody RegionDTO regionDTO) {
            return regionService.updateRegion(id, regionDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
            if (regionService.deleteRegion(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        }

    }
