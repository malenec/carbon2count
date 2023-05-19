package facades;

import dtos.*;
import entities.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroceryListFacadeTest {

    private static EntityManagerFactory emf;
    private static GroceryListFacade facade;
    User user1;
    private static Grocery g1, g2;
    private static UserDTO userDTO1;
    private static GroceryList groceryList1, groceryList2, groceryList3;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = GroceryListFacade.getGroceryListFacade(emf);
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();

        user1 = new User("user", "test123");
        g1 = new Grocery("Ra00468", "Ost", "Mælkeprodukter", "kg", 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 1.2);
        g2 = new Grocery("Ra00469", "OstHaps", "Mælkeprodukter", "kg", 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 1.8);

        try {

            em.getTransaction().begin();
            em.createNamedQuery("GroceryLine.deleteAllRows").executeUpdate();
            em.createNamedQuery("Grocery.deleteAllRows").executeUpdate();
            em.createNamedQuery("GroceryList.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();

            em.persist(g1);
            em.persist(g2);
            em.persist(user1);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
        userDTO1 = new UserDTO(user1);
    }

    @AfterAll
    public static void tearDownClass() {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("GroceryLine.deleteAllRows").executeUpdate();
            em.createNamedQuery("Grocery.deleteAllRows").executeUpdate();
            em.createNamedQuery("GroceryList.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void createGroceryList() {

        GroceryLineDTO groceryLineDTO1 = new GroceryLineDTO("Ra00468", 200L);
        GroceryLineDTO groceryLineDTO2 = new GroceryLineDTO("Ra00469", 400L);

        GroceryListDTO groceryListDTO = new GroceryListDTO();

        groceryListDTO.addGroceryLineDTO(groceryLineDTO1);
        groceryListDTO.addGroceryLineDTO(groceryLineDTO2);

        GroceryListDTO facadeGroceryList = facade.createGroceryList(userDTO1, groceryListDTO);

        assertEquals("Ra00468", facadeGroceryList.getGroceryLineDTOs().get(0).getGroceryId());
    }


    @Test
    void getAllGroceryListsByUsername() {

        EntityManager em = emf.createEntityManager();

        em.find(User.class, user1.getUserName());

        GroceryLine groceryLine1 = new GroceryLine();
        groceryLine1.setGrocery(g1);
        groceryLine1.setGroceryQuantity(200L);
        groceryList1 = new GroceryList();
        groceryList1.addGroceryLine(groceryLine1);
        user1.addGroceryList(groceryList1);

        GroceryLine groceryLine2 = new GroceryLine();
        groceryLine2.setGrocery(g2);
        groceryLine2.setGroceryQuantity(10L);
        groceryList2 = new GroceryList();
        groceryList2.addGroceryLine(groceryLine2);
        user1.addGroceryList(groceryList2);

        GroceryLine groceryLine3 = new GroceryLine();
        groceryLine3.setGrocery(g1);
        groceryLine3.setGroceryQuantity(90L);
        groceryList3 = new GroceryList();
        groceryList3.addGroceryLine(groceryLine3);
        user1.addGroceryList(groceryList3);

        try {

            em.getTransaction().begin();
            em.merge(user1);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        int expected = 3;
        int actual = facade.getAllGroceryListsByUsername(user1.getUserName()).size();

        System.out.println("Here is the size of the list of lists: " + actual);

        assertEquals(expected, actual);
    }
}