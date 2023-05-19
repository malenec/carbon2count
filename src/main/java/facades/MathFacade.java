package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dtos.GroceryLineDTO;
import dtos.GroceryListDTO;
import dtos.MathDTO;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MathFacade {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static MathFacade instance;
    private static EntityManagerFactory emf;
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final GroceryFacade GROCERY_FACADE =  GroceryFacade.getGroceryFacade(EMF);
    private static final GroceryListFacade GROCERYLIST_FACADE =  GroceryListFacade.getGroceryListFacade(EMF);


    //Private Constructor to ensure Singleton
    private MathFacade() {
    }

    public static MathFacade getMathFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MathFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ArrayList<MathDTO> createMathDTOS(String username) throws Exception {

        List<GroceryListDTO> groceryListDTOS = GROCERYLIST_FACADE.getAllGroceryListsByUsername(username);

        ArrayList<MathDTO> mathDTOS = new ArrayList<>();
        for (GroceryListDTO groceryListDTO: groceryListDTOS) {


        String apiUrl = "http://api.mathjs.org/v4/";

        // Create the JSON request body
        String body = "";
        for (GroceryLineDTO groceryLineDTO: groceryListDTO.getGroceryLineDTOs()) {
            //System.out.println("id " + groceryLineDTO.getGroceryId());
            System.out.println("Quantity " + groceryLineDTO.getGroceryQuantity());
            //System.out.println("Kg " + FACADE.getGroceryById(groceryLineDTO.getGroceryId()).getTotalKgCo2EqKg() );
           body += groceryLineDTO.getGroceryQuantity() + "*" + GROCERY_FACADE.getGroceryById(groceryLineDTO.getGroceryId()).getTotalKgCo2EqKg() + " + ";
        }
        body += 0;
        String requestBody = "{\"expr\": \"" + body + "\"}";

        // Set up the HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // Create the POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());



        // Handle the response
        if (response.statusCode() == 200) {
            JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);
            Object resultObj = jsonObject.get("result");
            String resultStr = resultObj.toString().replaceAll("\"", "");
            double result = Double.parseDouble(resultStr);
            MathDTO mathDTO = new MathDTO();
            mathDTO.setGroceryListTotalCo2(result);
            mathDTO.setCreated(groceryListDTO.getCreated().toString());
            mathDTOS.add(mathDTO);

            // extract result variable as an integer from response body

            response.body();

            // Parse and process the response as needed
        }
            //"Request failed with status code: " + response.statusCode();
        }
        return mathDTOS;
    }
}








