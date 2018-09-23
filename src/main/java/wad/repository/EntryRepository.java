package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Entry;
import wad.domain.Subject;
import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {

}
