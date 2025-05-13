package projet_soutenance.dsi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import projet_soutenance.dsi.model.Region;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SQLDelete(sql = "UPDATE province SET deleted=true WHERE id=?")

public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long codeDgess;
    private String libelle;
    private Boolean delete =false;


    @ManyToOne
    @JoinColumn(name = "region_id")
    private  Region region;

    @OneToMany(mappedBy = "province")
    private List<Commune> communes;

}
