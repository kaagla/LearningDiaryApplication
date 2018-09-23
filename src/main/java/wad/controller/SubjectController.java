package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import wad.service.SubjectService;
import wad.service.AccountService;
import wad.repository.SubjectRepository;
import wad.domain.Account;

@Controller
public class SubjectController {
    /*
        Handle GET, POST and DELETE requests for Subjects:
    
        - Account is verified, so that it can access only its own content.
        - SubjectService class is used in create, edit and delete.
    */
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private AccountService accountService;
    
    @GetMapping("/subjects")
    public String list(Model model) {
        Account a = accountService.getLoggedAccount();
        model.addAttribute("subjects", 
                subjectRepository.findByAccount(a));
        return "subjects";
    }
    
    @GetMapping("/subjects/{id}")
    public String getOne(Model model, @PathVariable Long id) {
        if (accountService.getLoggedAccount() == subjectRepository.getOne(id).getAccount()) {
            model.addAttribute("subject", subjectRepository.getOne(id));
            return "subject";
        } else {
            return "redirect:/subjects";
        }
    }
    
    @PostMapping("/subjects")
    public String post(@RequestParam String name) {
        Account a = accountService.getLoggedAccount();
        subjectService.add(name, a);
        return "redirect:/subjects";
    }
    
    @PostMapping("/subjects/{id}")
    public String edit(@PathVariable Long id, @RequestParam String name) {
        if (accountService.getLoggedAccount() == subjectRepository.getOne(id).getAccount()) {
            subjectService.edit(id, name);
            return "redirect:/subjects";
        } else {
            return "redirect:/subjects";
        }
    }
    
    @DeleteMapping("/subjects/{id}")
    public String delete(@PathVariable Long id) {
        if (accountService.getLoggedAccount() == subjectRepository.getOne(id).getAccount()) {
            subjectService.delete(id);
            return "redirect:/subjects";
        } else {
            return "redirect:/subjects";
        }
    }
}
