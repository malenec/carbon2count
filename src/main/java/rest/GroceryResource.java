package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.GroceryFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("produce")
public class GroceryResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final GroceryFacade FACADE =  GroceryFacade.getGroceryFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAll() {
        try {
            return GSON.toJson(FACADE.getAllGroceries());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "No groceries found";
    }
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getGroceryCount() {
        try {
            return GSON.toJson(FACADE.getGroceryCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "You counted wrong!";
    }
}
