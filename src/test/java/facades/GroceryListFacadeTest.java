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

        g1 = new Grocery("Ra00468", "Ost", "Mælkeprodukter", "kg", 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 1.2);
        g2 = new Grocery("Ra00469", "OstHaps", "Mælkeprodukter", "kg", 0.3, 0.3, 0.3, 0.3, 0.3, 0.3, 1.8);


        try {

            em.getTransaction().begin();
            System.out.println("før GroceryLine.deleteAllRows");
            em.createNamedQuery("GroceryLine.deleteAllRows").executeUpdate();
            System.out.println("før Grocery.deleteAllRows");
            em.createNamedQuery("Grocery.deleteAllRows").executeUpdate();
            System.out.println("før GroceryList.deleteAllRows");
            em.createNamedQuery("GroceryList.deleteAllRows").executeUpdate();
            System.out.println("før User.deleteAllRows");
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            System.out.println("efter alt er slettet");

            em.persist(g1);
            System.out.println("efter grocery persist");
            em.persist(g2);
            System.out.println("efter grocery persist2");
            em.persist(user1);
            System.out.println("efter user persist");

            em.getTransaction().commit();
            System.out.println("efter commit");
        } finally {
            em.close();
            System.out.println("efter em closed");
        }
        groceryDTO1 = new GroceryDTO(g1);
        groceryDTO2 = new GroceryDTO(g2);

        userDTO1 = new UserDTO(user1);

        System.out.println("til sidst i before each");
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
//
//        System.out.println("create groceryList test done");
//    }

}