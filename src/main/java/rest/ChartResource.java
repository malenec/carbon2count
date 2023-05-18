package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dtos.MathDTO;
import facades.ChartFacade;
import facades.MathFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.List;

@Path("chart")
public class ChartResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final ChartFacade CHART_FACADE = ChartFacade.getChartFacade(EMF);
    private static final MathFacade MATH_FACADE = MathFacade.getMathFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//
//    public String getChartImage() {
//        try {
//            String quickChartUrl = "https://quickchart.io/chart?cht=bvg&chd=t:10,20,30,40&chs=500x300";
//            byte[] chartImageBytes = FACADE.getChartImage(quickChartUrl);
//            String chartImageData = Base64.getEncoder().encodeToString(chartImageBytes);
//
//            return "{\"chartImageData\":\"" + chartImageData + "\"}";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "{\"error\":\"No chart found\"}";
//
//        }
//    }

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
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createChart(@PathParam("username") String username) {
        try {

            List<MathDTO> listOfMathDTOS = MATH_FACADE.createMathDTOS(username);

            byte[] chartImageBytes = CHART_FACADE.postChartImage(listOfMathDTOS);
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






