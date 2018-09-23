package wad.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import wad.repository.*;
import wad.domain.*;
import java.util.List;
import java.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntryService {
    /*
        Services for Entry handling:
    
        add()
        - Add Entry for given Subject.
    
        edit()
        - Modify given Entry.
    
        delete()
        - Delete given Entry.
    */
    
    @Autowired
    private EntryRepository entryRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    public void add(Long id, String title, LocalDate entrydate, String textfield) {
        
        Entry e = new Entry();
        Subject s = subjectRepository.getOne(id);

        e.setTitle(title);
        e.setTextfield(textfield);
        if (entrydate == null) {
            e.setEntrydate(LocalDate.now());
        } else {
            e.setEntrydate(entrydate);
        }
        e.setEntrysubject(s);
        entryRepository.save(e);
        
        List<Entry> el = s.getEntries();
        el.add(e);
        s.setEntries(el);
        subjectRepository.save(s);
    }
    
    public void edit(Long eid, String title, LocalDate entrydate, String textfield) {
        
        Entry e = entryRepository.getOne(eid);
        e.setTitle(title);
        e.setTextfield(textfield);
        e.setEntrydate(entrydate);
        entryRepository.save(e);
    }
    
    @Transactional
    public void delete(Long id, Long eid) {
        Subject s = subjectRepository.getOne(id);
        Entry e = entryRepository.getOne(eid);
        List<Entry> el = s.getEntries();
        el.remove(e);
        s.setEntries(el);
        subjectRepository.save(s);
        entryRepository.delete(e);
    }
}
