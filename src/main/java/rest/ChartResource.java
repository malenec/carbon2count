package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.ChartFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("chart")
public class ChartResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final ChartFacade FACADE =  ChartFacade.getChartFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String showLineChart() {

        try {
          //  String chartImage = FACADE.postHttpResponse("https://quickchart.io/chart?cht=bvg&chd=t:10,20,30,40&chs=500x300");


            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "No chart found";
        }
    }

    @POST
    @Produces("image/png")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createChart(String requestData) {
        try {
            String chartImage = FACADE.postHttpResponse("https://quickchart.io/chart", requestData);
            return Response.ok().entity(chartImage).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Failed to create chart").build();
        }
    }
}
