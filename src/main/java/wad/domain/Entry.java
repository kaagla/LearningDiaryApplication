package wad.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.time.LocalDate;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Entry extends AbstractPersistable<Long> {
    /*
        Entry object for the Application.
    */
    
    @NotEmpty
    private String title;
    
    @NotEmpty
    private String textfield;
    
    private LocalDate entrydate;
    
    @ManyToOne
    private Subject entrysubject;
}
