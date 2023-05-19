package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
import java.util.List;


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

    public byte[] postChartImage(List<MathDTO> mathDTOS) throws Exception {


        ArrayList<String> kategorier = new ArrayList<>();
        ArrayList<String> co2 = new ArrayList<>();

        for (MathDTO mathDTO: mathDTOS) {

            kategorier.add("'" + mathDTO.getCreated() + "'");
            co2.add(String.valueOf(mathDTO.getGroceryListTotalCo2()));

        }

        String requestBody = "{\"backgroundColor\": \"transparent\","
                + "\"width\": 500,"
                + "\"height\": 300,"
                + "\"format\": \"png\","
                + "\"chart\": \"{type:'line',data:{labels:" + kategorier + ",datasets:[{label:'Samlet CO2 udledning',data:" + co2 + ", backgroundColor: 'rgb(75, 192, 192)', borderColor: 'rgb(75, 192, 192)', fill: false, pointRadius: 5, pointHoverRadius: 30}]},options:{scales:{yAxes:[{ticks:{callback:function(value){return+value+'kg CO2';}}}]}}}\""
                + "}";

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
//        emf = EMF_Creator.createEntityManagerFactory();

        ArrayList<String> frugt = new ArrayList<>();
        frugt.add("'æble'");
        frugt.add("'pære'");
        frugt.add("banan");
        frugt.add("appelsin");

        System.out.println(frugt);


        System.out.println("test");

        System.out.println("test");
    }
}








