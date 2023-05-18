package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import facades.ChartFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;

@Path("chart")
public class ChartResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final ChartFacade FACADE = ChartFacade.getChartFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getChartImage() {
        try {
            String quickChartUrl = "https://quickchart.io/chart?cht=bvg&chd=t:10,20,30,40&chs=500x300";
            byte[] chartImageBytes = FACADE.getChartImage(quickChartUrl);
            String chartImageData = Base64.getEncoder().encodeToString(chartImageBytes);

            return "{\"chartImageData\":\"" + chartImageData + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"No chart found\"}";
        }
    }

//    @POST
//    @Produces("image/png")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createChart(String requestData) {
//        try {
//            String chartImage = FACADE.postHttpResponse("https://quickchart.io/chart", requestData);
//            return Response.ok().entity(chartImage).build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Response.serverError().entity("Failed to create chart").build();
//        }
//    }

//    String requestBody = "{\"backgroundColor\": \"transparent\","
//            + "\"width\": 500,"
//            + "\"height\": 300,"
//            + "\"format\": \"png\","
//            + "\"chart\": \"{type:'bar',data:{labels:['January','February','March','April','May'],datasets:[{label:'Dogs',data:[50,60,70,180,190]}]},options:{scales:{yAxes:[{ticks:{callback:function(value){return'$'+value;}}}]}}}\""
//            + "}";


    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChart() {
        try {



            String requestBody = "{\"backgroundColor\": \"transparent\","
                    + "\"width\": 500,"
                    + "\"height\": 300,"
                    + "\"format\": \"png\","
                    + "\"chart\": \"{type:'line',data:{labels:['January','February','March','April','May'],datasets:[{label:'Dogs',data:[50,100,70,180,190]}]},options:{scales:{yAxes:[{ticks:{callback:function(value){return'$'+value;}}}]}}}\""
                    + "}";

            byte[] chartImageBytes = FACADE.postChartImage(requestBody);
            String chartImageData = Base64.getEncoder().encodeToString(chartImageBytes);

            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("chartImageData", chartImageData);

            return Response.ok(responseJson.toString()).build();
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorJson = new JsonObject();
            errorJson.addProperty("error", "No chart found");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorJson.toString()).build();
        }
    }
}






