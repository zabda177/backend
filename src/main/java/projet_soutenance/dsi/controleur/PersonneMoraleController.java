package projet_soutenance.dsi.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projet_soutenance.dsi.DTO.PersonneMoraleDTO;
import projet_soutenance.dsi.service.PersonneMoraleService;


import java.util.List;

@RestController
@RequestMapping("/api/personnes-morales")
public class PersonneMoraleController {
    private final PersonneMoraleService personneMoraleService;

    @Autowired
    public PersonneMoraleController(PersonneMoraleService personneMoraleService) {
        this.personneMoraleService = personneMoraleService;
    }

    @GetMapping
    public ResponseEntity<List<PersonneMoraleDTO>> getAll() {
        List<PersonneMoraleDTO> personnes = personneMoraleService.getAll();
        return ResponseEntity.ok(personnes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonneMoraleDTO> getById(@PathVariable Long id) {
        return personneMoraleService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<PersonneMoraleDTO> create(@RequestBody PersonneMoraleDTO personneMoraleDTO) {
        PersonneMoraleDTO createdPersonne = personneMoraleService.create(personneMoraleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonneMoraleDTO> update(@PathVariable Long id, @RequestBody PersonneMoraleDTO personneMoraleDTO) {
        return personneMoraleService.update(id, personneMoraleDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (personneMoraleService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



}
