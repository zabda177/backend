package projet_soutenance.dsi.controleur;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_soutenance.dsi.DTO.PersonnePhysiqueDTO;
import projet_soutenance.dsi.service.PersonnePhysiqueService;


import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonnePhysiqueController {
    private final PersonnePhysiqueService personnePhysiqueService;

    @Autowired
    public PersonnePhysiqueController(PersonnePhysiqueService personnePhysiqueService) {
        this.personnePhysiqueService = personnePhysiqueService;
    }

    @GetMapping
    public ResponseEntity<List<PersonnePhysiqueDTO>> getAll() {
        List<PersonnePhysiqueDTO> personnes = personnePhysiqueService.getAll();
        return ResponseEntity.ok(personnes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonnePhysiqueDTO> getById(@PathVariable Long id) {
        return personnePhysiqueService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/physique")
    public ResponseEntity<PersonnePhysiqueDTO> create(@RequestBody PersonnePhysiqueDTO personnePhysiqueDTO) {
        PersonnePhysiqueDTO createdPersonne = personnePhysiqueService.create(personnePhysiqueDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonnePhysiqueDTO> update(@PathVariable Long id, @RequestBody PersonnePhysiqueDTO personnePhysiqueDTO) {
        return personnePhysiqueService.update (id, personnePhysiqueDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (personnePhysiqueService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/test")
    public String test() {
        return "Le contrôleur fonctionne!";
    }
}
