package wad.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import wad.repository.AccountRepository;
import wad.domain.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AccountService {
    /*
        Services for Account handling:
        
        getLoggedAccount()
        - Retrieve currently logged in account.
        
        createAccount()
        - Creates new account for the application.
    
        autoLogin()
        - Log in account automatically after registration.
    
        logout()
        - Log out account.
    */
    
    @Autowired
    private AccountRepository accountRepository;
    
    
    @Autowired
    private HttpServletRequest hsr;
    
    public Account getLoggedAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account a = accountRepository.findByUsername(username);
        return a;
    }
    
    public void createAccount(String username, String password) {
        if (!accountRepository.findAll().contains(accountRepository.findByUsername(username))) {
            Account a = new Account();
            a.setUsername(username);
            BCryptPasswordEncoder pwe = new BCryptPasswordEncoder();
            a.setPassword(pwe.encode(password));
            accountRepository.save(a);
        }
    }
    
    public void autoLogin(String username, String password) {
        try {
            hsr.login(username, password);
        } catch (ServletException e) {
        }
    }
    
    public void logout() {
        try {
            hsr.logout();
        } catch (ServletException e) {
        }
    }
}
