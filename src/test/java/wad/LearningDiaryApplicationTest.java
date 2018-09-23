package wad;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.fluentlenium.adapter.junit.FluentTest;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.springframework.boot.web.server.LocalServerPort;
import java.time.LocalDate;
import wad.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LearningDiaryApplicationTest extends FluentTest {
    /*
        UI functional tests with Fluentlenium:
    
        testRegistrationAndLogout()
        - Register account and logout.
    
        subjectCreateEditDelete()
        - Create subject, edit and delete it.
    
        entryCreateEditDelete()
        - Create entry, edit and delete it.
    */
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private WebApplicationContext webAppContext;
    
    private MockMvc mockMvc;
    
    @LocalServerPort
    private Integer port;
    
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }
    
    @Test
    public void testRegistrationAndLogout() throws Exception {
        goTo("http://localhost:" + port);
        
        find("#reguser").fill().with("user1");
        find("#regpw").fill().with("user1pw");
        find("#register").submit();
        
        assertTrue(pageSource().contains("Add new subject:"));
        
        goTo("http://localhost:" + port + "/logout");
    }
    
    @Test
    public void subjectCreateEditDelete() throws Exception {
        accountService.createAccount("user2", "user2pw");
        
        goTo("http://localhost:" + port);
        
        find("#loguser").fill().with("user2");
        find("#logpw").fill().with("user2pw");
        find("#login").submit();
        
        assertTrue(pageSource().contains("Add new subject:"));
        
        find("#subname").fill().with("Subject1");
        find("#add").submit();
        
        assertTrue(pageSource().contains("Subject1"));
        
        find("#edit").submit();
        
        find("#sname").clear();
        find("#sname").fill().with("Subject2");
        find("#edit").submit();
        
        assertTrue(pageSource().contains("Subject2"));
        assertFalse(pageSource().contains("Subject1"));
        
        find("#delete").submit();
        
        assertFalse(pageSource().contains("Subject2"));
    }
    
    @Test
    public void entryCreateEditDelete() throws Exception {
        accountService.createAccount("user3", "user3pw");
        
        goTo("http://localhost:" + port);
        
        find("#loguser").fill().with("user3");
        find("#logpw").fill().with("user3pw");
        find("#login").submit();
        
        assertTrue(pageSource().contains("Add new subject:"));
        
        find("#subname").fill().with("Subject3");
        find("#add").submit();
        
        find("#subentries").click();
        
        find("#title").fill().with("Subject title");
        find("#textfield").fill().with("This is entrytext!");
        find("#add").submit();
        
        assertTrue(pageSource().contains("Subject title"));
        assertTrue(pageSource().contains(LocalDate.now().toString()));
        assertTrue(pageSource().contains("This is entrytext!"));

        find("#edit").submit();
        
        assertTrue(pageSource().contains("Edit entry:"));
        
        find("#textfield").fill().with("The entrytext is changed!");
        find("#edit").submit();
        
        assertTrue(pageSource().contains("Subject title"));
        assertTrue(pageSource().contains(LocalDate.now().toString()));
        assertTrue(pageSource().contains("The entrytext is changed!"));
        assertFalse(pageSource().contains("This is entrytext!"));
        
        find("#delete").submit();
        
        assertFalse(pageSource().contains("Subject title"));
        assertFalse(pageSource().contains(LocalDate.now().toString()));
        assertFalse(pageSource().contains("The entrytext is changed!"));

    }
}
