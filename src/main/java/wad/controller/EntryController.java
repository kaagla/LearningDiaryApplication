package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import wad.repository.*;
import wad.service.EntryService;
import wad.service.AccountService;

@Controller
public class EntryController {
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private EntryRepository entryRepository;
    
    @Autowired
    private EntryService entryService;
    
    @Autowired
    private AccountService accountService;
    
    @GetMapping("/subjects/{id}/entries")
    public String list(Model model, @PathVariable Long id) {
        if (accountService.getLoggedAccount() == subjectRepository.getOne(id).getAccount()) {
            model.addAttribute("subject", subjectRepository.getOne(id));
            model.addAttribute("entries", 
                subjectRepository.getOne(id).getEntries());
            return "entries";
        } else {
            return "redirect:/subjects";
        }
    }
    
    @GetMapping("/subjects/{id}/entries/{eid}")
    public String getOne(Model model, @PathVariable Long id, @PathVariable Long eid) {
        if (accountService.getLoggedAccount() == subjectRepository.getOne(id).getAccount()) {
            model.addAttribute("subject", subjectRepository.getOne(id));
            model.addAttribute("entry", entryRepository.getOne(eid));
            return "entry";
        } else {
            return "redirect:/subjects";
        }
    }
    
    @PostMapping("/subjects/{id}/entries")
    public String post(RedirectAttributes redirectAttributes,
            @PathVariable Long id, 
            @RequestParam String title,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entrydate,
            @RequestParam String textfield) {
        
        if (accountService.getLoggedAccount() == subjectRepository.getOne(id).getAccount()) {
            entryService.add(id, title, entrydate, textfield);
            redirectAttributes.addAttribute("id", subjectRepository.getOne(id).getId());
            return "redirect:/subjects/{id}/entries";
        } else {
            return "redirect:/subjects";
        }
    }
    
    @PostMapping("/subjects/{id}/entries/{eid}")
    public String edit(RedirectAttributes redirectAttributes,
            @PathVariable Long id,
            @PathVariable Long eid,
            @RequestParam String title,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entrydate,
            @RequestParam String textfield) {
        
        if (accountService.getLoggedAccount() == subjectRepository.getOne(id).getAccount()) {
            entryService.edit(eid, title, entrydate, textfield);
            redirectAttributes.addAttribute("id", subjectRepository.getOne(id).getId());
            return "redirect:/subjects/{id}/entries";
        } else {
            return "redirect:/subjects";
        }
    }
    
    @DeleteMapping("/subjects/{id}/entries/{eid}")
    public String delete(RedirectAttributes redirectAttributes,
            @PathVariable Long id, @PathVariable Long eid) {
        if (accountService.getLoggedAccount() == subjectRepository.getOne(id).getAccount()) {
            entryService.delete(id, eid);
            redirectAttributes.addAttribute("id", id);
            return "redirect:/subjects/{id}/entries";
        } else {
            return "redirect:/subjects";
        }
    }
}
