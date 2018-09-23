package wad.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Subject extends AbstractPersistable<Long> {
    /*
        Subject object for the Application.
    */
    
    @NotEmpty
    private String name;
    
    @ManyToOne
    private Account account;
    
    @OneToMany(orphanRemoval=true)
    private List<Entry> entries;
}
