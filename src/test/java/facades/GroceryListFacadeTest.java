package facades;

import dtos.GroceryDTO;
import dtos.GroceryListDTO;
import dtos.QuoteDTO;
import dtos.UserDTO;
import entities.Grocery;
import entities.GroceryList;
import entities.Quote;
import entities.User;
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
    User user = new User("user", "test123");
    private static Grocery g1, g2;
    private static List<GroceryDTO> listWithGrocieries = new ArrayList<>();



    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = GroceryListFacade.getGroceryListFacade(emf);
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();

        g1 = new Grocery("id1","Ost", "Mælkeprodukter", "kg",  0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 1.2);
        g2 = new Grocery("id2","OstHaps", "Mælkeprodukter", "kg",  0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 1.8);


       try {
           em.getTransaction().begin();
           em.createNamedQuery("User.deleteAllRows").executeUpdate();
           em.createNamedQuery("Grocery.deleteAllRows").executeUpdate();
           em.persist(g1);
           em.persist(g2);
           em.persist(user);
           em.getTransaction().commit();
        } finally {
            em.close();
        }
        GroceryDTO groceryDTO1 = new GroceryDTO(g1);
        GroceryDTO groceryDTO2 = new GroceryDTO(g2);

        listWithGrocieries.add(groceryDTO1);
        listWithGrocieries.add(groceryDTO2);
    }

    @Test
    void createGroceryList() {
        GroceryDTO expectedGrocery = listWithGrocieries.get(0);
        GroceryListDTO groceryListDTO = new GroceryListDTO(user.getUserName(), listWithGrocieries);
        GroceryListDTO facadeGroceryList = facade.createGroceryList(groceryListDTO);

        Boolean isInList = facadeGroceryList.getGroceries().contains(expectedGrocery);

        assertEquals(true, isInList);
    }

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
