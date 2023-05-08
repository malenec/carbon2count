package facades;

import dtos.QuoteDTO;
import dtos.UserDTO;
import entities.Quote;
import entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class InternalApiFacadeTest {

    @PersistenceUnit(unitName = "puTest")

    private static EntityManagerFactory emf;
    private static InternalApiFacade facade;
    User user = new User("user", "test123");

    public InternalApiFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = InternalApiFacade.getInternalApiFacade(emf);
    }

    @BeforeEach
    void setUp() {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Quote.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void createQuote() {
        EntityManager em = emf.createEntityManager();
        Quote expectedQuote = new Quote("jeg er sej");

        QuoteDTO actualQuote = new QuoteDTO(expectedQuote);
        actualQuote = facade.createQuote(actualQuote);

        assertEquals(expectedQuote.getQuote(), actualQuote.getQuote());
    }

    @Test
    void addQuote() {
        EntityManager em = emf.createEntityManager();
        QuoteDTO expectedQuote1 = new QuoteDTO(new Quote("jeg er sej"));
        QuoteDTO expectedQuote2 = new QuoteDTO(new Quote("jeg er mega sej"));
        expectedQuote1 = facade.createQuote(expectedQuote1);
        expectedQuote2 = facade.createQuote(expectedQuote2);

        try {
            em.getTransaction().begin();
            user = em.find(User.class, user.getUserName());
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        facade.addQuote("user", expectedQuote1.getId());
        UserDTO userDTO = facade.addQuote("user", expectedQuote2.getId());

        userDTO.getQuotes().forEach(quoteDTO -> System.out.println(quoteDTO.getQuote()));

        assertEquals(expectedQuote2.getQuote(), userDTO.getQuotes().get(1).getQuote());

    }

    @Test
    void removeQuoteFromUser() {
        EntityManager em = emf.createEntityManager();

        QuoteDTO expectedQuote1 = new QuoteDTO(new Quote("jeg er sej"));
        QuoteDTO expectedQuote2 = new QuoteDTO(new Quote("jeg er mega sej"));

        expectedQuote1 = facade.createQuote(expectedQuote1);
        expectedQuote2 = facade.createQuote(expectedQuote2);

        try {
            em.getTransaction().begin();
            user = em.find(User.class, user.getUserName());
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        facade.addQuote("user", expectedQuote1.getId());
        UserDTO userDTO = facade.addQuote("user", expectedQuote2.getId());

        //checks what's in the list BEFORE remove method is called
        userDTO.getQuotes().forEach(quoteDTO -> System.out.println(quoteDTO.getQuote()));

        int listSizeBeforeRemove = userDTO.getQuotes().size();

        assertEquals(listSizeBeforeRemove, userDTO.getQuotes().size());

        userDTO = facade.removeQuoteFromUser("user", expectedQuote1.getId());

        //checks what's in the list AFTER remove method is called
        userDTO.getQuotes().forEach(quoteDTO -> System.out.println(quoteDTO.getQuote()));

        assertEquals(false, userDTO.getQuotes().contains(expectedQuote1.getQuote()));
        assertEquals(listSizeBeforeRemove-1, userDTO.getQuotes().size());

    }
}