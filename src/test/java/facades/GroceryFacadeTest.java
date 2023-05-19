package facades;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroceryFacadeTest {

    private static EntityManagerFactory emf;
    private static GroceryFacade facade;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = GroceryFacade.getGroceryFacade(emf);
    }

    public GroceryFacadeTest() {
    }

    @Test
    public void testGetAllGroceries() {
        assertEquals(facade.getAllGroceries().size(), facade.getGroceryCount(), "Expects 499 rows in the database");
    }
}
