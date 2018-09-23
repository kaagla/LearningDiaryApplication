package wad.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account extends AbstractPersistable<Long> {
    /*
        Account object for the Application.
    */
    
    @NotEmpty
    @Column(unique=true)
    private String username;
    
    @NotEmpty
    private String password;
    
    @OneToMany(orphanRemoval=true)
    private List<Subject> subjects;
}
