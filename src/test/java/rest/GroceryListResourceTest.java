package rest;

import dtos.GroceryDTO;
import dtos.GroceryLineDTO;
import dtos.GroceryListDTO;
import dtos.UserDTO;
import entities.Grocery;
import entities.GroceryLine;
import entities.GroceryList;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;

public class GroceryListResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    User user1;
    private static Grocery g1, g2;
    private static GroceryList groceryList1, groceryList2, groceryList3;
    private static GroceryLine groceryLine1, groceryLine2, groceryLine3;
    private static UserDTO userDTO1;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {

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

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
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

            em.persist(user1);
            em.persist(g1);
            em.persist(g2);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
        userDTO1 = new UserDTO(user1);
    }

    @Test
    public void createGroceryListTest() throws Exception {

        GroceryLineDTO groceryLineDTO1 = new GroceryLineDTO("Ra00468", 200L);
        GroceryLineDTO groceryLineDTO2 = new GroceryLineDTO("Ra00469", 400L);

        GroceryListDTO groceryListDTO = new GroceryListDTO();

        groceryListDTO.addGroceryLineDTO(groceryLineDTO1);
        groceryListDTO.addGroceryLineDTO(groceryLineDTO2);

        given()
                .contentType("application/json")
                .body(groceryListDTO)
                .when()
                .post("/grocerylist/create/" + userDTO1.getUsername())
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("groceryLineDTOs.size()", org.hamcrest.Matchers.is(2));
    }

    @Test
    public void getAllGroceryListsByUsernameTest() throws Exception {

        EntityManager em = emf.createEntityManager();

        em.find(User.class, user1.getUserName());

        groceryLine1 = new GroceryLine();
        groceryLine1.setGrocery(g1);
        groceryLine1.setGroceryQuantity(200L);
        groceryList1 = new GroceryList();
        groceryList1.addGroceryLine(groceryLine1);
        user1.addGroceryList(groceryList1);

        groceryLine2 = new GroceryLine();
        groceryLine2.setGrocery(g2);
        groceryLine2.setGroceryQuantity(10L);
        groceryList2 = new GroceryList();
        groceryList2.addGroceryLine(groceryLine2);
        user1.addGroceryList(groceryList2);

        groceryLine3 = new GroceryLine();
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

        given()
                .contentType("application/json")
                .get("/grocerylist/all/" + user1.getUserName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", org.hamcrest.Matchers.is(3));
    }
}
