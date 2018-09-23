package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Subject;
import wad.domain.Account;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByAccount(Account account);
}
