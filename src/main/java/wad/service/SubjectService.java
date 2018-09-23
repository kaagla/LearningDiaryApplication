package wad.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import wad.repository.*;
import wad.domain.*;
import java.util.List;

@Service
public class SubjectService {
    /*
        Services for Subject handling:
    
        add()
        - Add Subject for given Account.
    
        edit()
        - Modify given Subject.
    
        delete()
        - Delete given Subject.
    */
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountService accountService;
    
    public void add(String name, Account account) {
        if (!name.trim().equals("")) {
            Subject s = new Subject();
            s.setName(name);
            s.setAccount(account);
            subjectRepository.save(s);
            
            List<Subject> sl = account.getSubjects();
            sl.add(s);
            account.setSubjects(sl);
            accountRepository.save(account);
        }
    }
    
    public void edit(Long id, String name) {
        if (!name.trim().equals("")) {
            Subject s = subjectRepository.getOne(id);
            s.setName(name);
            subjectRepository.save(s);
        }
    }
    
    @Transactional
    public void delete(Long id) {
        Account a = accountService.getLoggedAccount();
        Subject s = subjectRepository.getOne(id);
        List<Subject> sl = a.getSubjects();
        sl.remove(s);
        a.setSubjects(sl);
        accountRepository.save(a);
        subjectRepository.delete(s);
    }
    
}
