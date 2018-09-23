package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import wad.service.AccountService;
import wad.repository.AccountRepository;
import wad.domain.Account;

@Controller
public class DefaultController {
    /*
        Handle default Get requests:
        - If user has logged in, user is directed to /subjects,
          if user is not authenticated, user is directed to
          /index.
    */
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountService accountService;
    
    @GetMapping("/")
    public String getDefault() {
        Account a = accountService.getLoggedAccount();
        if (!accountRepository.findAll().contains(a)) {
            return "index";
        } else {
            return "redirect:/subjects";
        } 
    }
}
