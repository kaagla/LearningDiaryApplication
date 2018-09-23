package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.service.AccountService;
import wad.repository.AccountRepository;

@Controller
public class AccountController {
    /*
        Handle POST requests for registration and login:
    
        create()
        - Create new account with given username and password,
          and login the account automatically. If the username
          already exists, redirect to index.
    
        login()
        - Login the user with given username and password. If login
          failes, redirect to index.
    */
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountService accountService;
    
    @PostMapping("/registration")
    public String create(@RequestParam String username, 
            @RequestParam String password) {
        if (!accountRepository.findAll().contains(accountRepository.findByUsername(username))) {
            accountService.createAccount(username, password);
            accountService.autoLogin(username, password);
            return "redirect:/subjects";
        } else {
            return "redirect:/";
        }
    }
    
    @PostMapping("/login")
    public String userLogin(@RequestParam String username,
            @RequestParam String password) {
        try {
            accountService.autoLogin(username, password);
            return "redirect:/subjects";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/logout")
    public String logout() {
        accountService.logout();
        return "redirect:/";
    }
}
