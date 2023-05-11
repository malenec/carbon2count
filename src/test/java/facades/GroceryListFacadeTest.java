package facades;

import dtos.*;
import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroceryListFacadeTest {

    @PersistenceUnit(unitName = "puTest")

    private static EntityManagerFactory emf;
    private static GroceryListFacade facade;
    User user1 = new User("user", "test123");
    private static Grocery g1, g2;
    private static GroceryDTO groceryDTO1, groceryDTO2;
    private static UserDTO userDTO1;
    private static List<GroceryDTO> listWithGrocieries = new ArrayList<>();



    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = GroceryListFacade.getGroceryListFacade(emf);
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();

        g1 = new Grocery("Ra00468","Ost", "Mælkeprodukter", "kg",  0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 1.2);
        g2 = new Grocery("Ra00469","OstHaps", "Mælkeprodukter", "kg",  0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 1.8);


       try {
           em.getTransaction().begin();
           //em.createNamedQuery("GroceryList.deleteAllRows").executeUpdate();
           em.createNamedQuery("GroceryLine.deleteAllRows").executeUpdate();
           //em.createNamedQuery("User.deleteAllRows").executeUpdate();
           em.createNamedQuery("Grocery.deleteAllRows").executeUpdate();
           em.persist(g1);
           em.persist(g2);
           em.persist(user1);
           em.getTransaction().commit();
        } finally {
            em.close();
        }
        groceryDTO1 = new GroceryDTO(g1);
        groceryDTO2 = new GroceryDTO(g2);

        userDTO1 = new UserDTO(user1);


//        listWithGrocieries.add(groceryDTO1);
//        listWithGrocieries.add(groceryDTO2);
    }

//    @Test
//    void createGroceryList() {
//
//        GroceryLineDTO groceryLineDTO1 = new GroceryLineDTO("Ra00468", 200L);
//        GroceryLineDTO groceryLineDTO2 = new GroceryLineDTO("Ra00469", 400L);
//
//        GroceryListDTO groceryListDTO = new GroceryListDTO();
//
//        groceryListDTO.addGroceryLineDTO(groceryLineDTO1);
//        groceryListDTO.addGroceryLineDTO(groceryLineDTO2);
//
//        GroceryListDTO facadeGroceryList = facade.createGroceryList(userDTO1, groceryListDTO);
//
//        assertEquals("Ra00468", facadeGroceryList.getGroceryLineDTOs().get(0).getGroceryId());
//    }


//    @Test
//    void addQuote() {
//        EntityManager em = emf.createEntityManager();
//        QuoteDTO expectedQuote1 = new QuoteDTO(new Quote("jeg er sej"));
//        QuoteDTO expectedQuote2 = new QuoteDTO(new Quote("jeg er mega sej"));
//        expectedQuote1 = facade.createQuote(expectedQuote1);
//        expectedQuote2 = facade.createQuote(expectedQuote2);
//
//        try {
//            em.getTransaction().begin();
//            user = em.find(User.class, user.getUserName());
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//
//        facade.addQuote("user", expectedQuote1.getId());
//        UserDTO userDTO = facade.addQuote("user", expectedQuote2.getId());
//
//        userDTO.getQuotes().forEach(quoteDTO -> System.out.println(quoteDTO.getQuote()));
//
//        assertEquals(expectedQuote2.getQuote(), userDTO.getQuotes().get(1).getQuote());
//
//    }
//
//    @Test
//    void removeQuoteFromUser() {
//        EntityManager em = emf.createEntityManager();
//
//        QuoteDTO expectedQuote1 = new QuoteDTO(new Quote("jeg er sej"));
//        QuoteDTO expectedQuote2 = new QuoteDTO(new Quote("jeg er mega sej"));
//
//        expectedQuote1 = facade.createQuote(expectedQuote1);
//        expectedQuote2 = facade.createQuote(expectedQuote2);
//
//        try {
//            em.getTransaction().begin();
//            user = em.find(User.class, user.getUserName());
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//
//        facade.addQuote("user", expectedQuote1.getId());
//        UserDTO userDTO = facade.addQuote("user", expectedQuote2.getId());
//
//        //checks what's in the list BEFORE remove method is called
//        userDTO.getQuotes().forEach(quoteDTO -> System.out.println(quoteDTO.getQuote()));
//
//        int listSizeBeforeRemove = userDTO.getQuotes().size();
//
//        assertEquals(listSizeBeforeRemove, userDTO.getQuotes().size());
//
//        userDTO = facade.removeQuoteFromUser("user", expectedQuote1.getId());
//
//        //checks what's in the list AFTER remove method is called
//        userDTO.getQuotes().forEach(quoteDTO -> System.out.println(quoteDTO.getQuote()));
//
//        assertEquals(false, userDTO.getQuotes().contains(expectedQuote1.getQuote()));
//        assertEquals(listSizeBeforeRemove-1, userDTO.getQuotes().size());
//
//    }
}
