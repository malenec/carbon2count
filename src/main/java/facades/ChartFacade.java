package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.MathDTO;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
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

    public static byte[] postChartImage(String requestBody) throws Exception {
        // Construct the QuickChart.io URL
        String quickChartUrl = "https://quickchart.io/chart";

        // Create the connection
        URL url = new URL(quickChartUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send the request body
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
        }

        // Fetch the chart image data
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (InputStream inputStream = connection.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } finally {
            connection.disconnect();
        }

        // Return the chart image data as byte array
        return output.toByteArray();
    }

    public static byte[] getChartImage(String quickChartUrl) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("accept", "image/png")
                .uri(URI.create(quickChartUrl))
                .build();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        try (InputStream inputStream = response.body();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        }
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








