package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dtos.*;
import entities.Grocery;
import entities.User;
import facades.GroceryFacade;
import facades.GroceryListFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("grocerylist")
public class GroceryListResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final GroceryListFacade FACADE =  GroceryListFacade.getGroceryListFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @POST
    @Path("create/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGroceryLine(@PathParam("username") String username, String jsonString) {

        List<GroceryLineDTO> groceryLineDTOList = new ArrayList<>();

        try {
            GroceryListDTO groceryListDTO = GSON.fromJson(jsonString, GroceryListDTO.class);
            User user = FACADE.getUser(username);
            UserDTO userDTO = new UserDTO(user);

            for (GroceryLineDTO groceryLineDTO : groceryListDTO.getGroceryLineDTOs()){
                System.out.println(groceryLineDTO);
                groceryLineDTOList.add(groceryLineDTO);
            }

            GroceryListDTO groceryListDTO2 = new GroceryListDTO(groceryLineDTOList);
            FACADE.createGroceryList(userDTO, groceryListDTO2);

            return Response.ok().entity(groceryListDTO2).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Failed to create grocery lines. Error: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("all/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGroceryListsByUsername(@PathParam("username") String username) {

        try {
            List<GroceryListDTO> groceryListDTOList = FACADE.getAllGroceryListsByUsername(username);
            return Response.ok().entity(groceryListDTOList).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Failed to get grocery lists. Error: " + e.getMessage())
                    .build();
        }
    }
}
