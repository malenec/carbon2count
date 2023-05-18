package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dtos.MathDTO;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.net.http.HttpRequest.BodyPublishers;

import static facades.MathFacade.createMathDTOS;

public class ChartFacade {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static ChartFacade instance;
    private static EntityManagerFactory emf;
//    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
//    private static final GroceryFacade FACADE =  GroceryFacade.getGroceryFacade(EMF);



    //Private Constructor to ensure Singleton
    private ChartFacade() {
    }


    public static ChartFacade getChartFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ChartFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public static String getHttpResponse(String url) throws Exception {
        System.out.println("Do I pass this point?");
        HttpClient client = HttpClient.newHttpClient();
        System.out.println("What about this point?");
        HttpRequest request = HttpRequest.newBuilder()
                .header("accept", "application/json")
                .uri(URI.create(url))
                .build();
        System.out.println("Am I passed this point?");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // {"data":{"mal_id":18771,"url":"https:\/\/myanimelist.net\/anime\/18771\/Gifuu_Doudou__Kanetsugu_to_Keiji","images":{"jpg":{"image_url":"https:\/\/cdn.myanimelist.net\/images\/anime\/5\/51427.jpg",
        System.out.println("How about this point?");
        System.out.println(response.body().toString());
        System.out.println("Did I get passed this point?");
//        DataDTO dataDTO = new Gson().fromJson(response.body(), DataDTO.class);
        System.out.println("And this point?");
//        JsonObject jo = dataDTO.data;
        System.out.println("Last point?");
        return response.body();
    }
    class DataDTO{
        public JsonObject data;
    }

    public static String postHttpResponse(String url, String requestBody) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .uri(URI.create(url))
                .POST(BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body().toString());

        return response.body();
    }

    public static void main(String[] args) throws Exception {
        emf = EMF_Creator.createEntityManagerFactory();

        GroceryListFacade groceryListFacade = GroceryListFacade.getGroceryListFacade(emf);


        ArrayList<MathDTO> mdto = createMathDTOS(groceryListFacade.getAllGroceryListsByUsername("user"));

        System.out.println("test");
        System.out.println(mdto);
        System.out.println("test");
    }
}








