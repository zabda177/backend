package projet_soutenance.dsi.controleur;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_soutenance.dsi.DTO.CommuneDDTO;
import projet_soutenance.dsi.service.CommuneService;


import java.util.List;

@RestController
@RequestMapping("/api/communes")
public class CommuneController {
    private final CommuneService communeService;

    @Autowired
    public CommuneController(CommuneService communeService) {
        this.communeService = communeService;
    }

    @GetMapping
    public ResponseEntity<List<CommuneDDTO>> getAllCommunes() {
        List<CommuneDDTO> communes = communeService.getAllCommunes();
        return ResponseEntity.ok(communes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommuneDDTO> getCommuneById(@PathVariable Long id) {
        return communeService.getCommuneById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CommuneDDTO> createCommune(@RequestBody CommuneDDTO communeDDTO) {
        CommuneDDTO createdCommune = communeService.createCommune(communeDDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommune);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommuneDDTO> updateCommune(@PathVariable Long id, @RequestBody CommuneDDTO communeDDTO) {
        return communeService.updateCommune(id, communeDDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommune(@PathVariable Long id) {
        if (communeService.deleteCommune(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
