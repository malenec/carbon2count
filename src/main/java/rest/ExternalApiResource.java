package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AgeDTO;
import dtos.PersonDTO;
import dtos.QuoteDTO;
import dtos.UserDTO;
import facades.ExternalApiFacade;
import facades.InternalApiFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author lam@cphbusiness.dk
 */
@Path("ext")
public class ExternalApiResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ExternalApiFacade EXTERNAL_API_FACADE =  ExternalApiFacade.getExternalApiFacade(EMF);
    private static final InternalApiFacade INTERNAL_API_FACADE =  InternalApiFacade.getInternalApiFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getInfoForAll() {
//        return "{\"msg\":\"Hello anonymous person\"}";
//    }
//

    //ensures we can parse servers of rest-test : endpoint /ext
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("kanye")
    @Produces(MediaType.APPLICATION_JSON)
    public String getKanyeQuote() throws Exception {

        try {
//            return EXTERNAL_API_FACADE.getKanyeQuote("https://api.kanye.rest");
            return GSON.toJson(EXTERNAL_API_FACADE.getKanyeQuote("https://api.kanye.rest"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Quote not found";
    }

    @GET
    @Path("jokes")
    @Produces("application/json")
    public String getJokes() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.chucknorris.io/jokes/random"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }


    @POST
    @Path("/kanye/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addQuoteToUser(@PathParam("username") String userName, String quote) {
        System.out.println("THIS IS YOUR QUOTE: " + quote);
        QuoteDTO quoteDTO = GSON.fromJson(quote, QuoteDTO.class);
        quoteDTO = INTERNAL_API_FACADE.createQuote(quoteDTO);
        INTERNAL_API_FACADE.addQuote(userName, quoteDTO.getId());
        System.out.println("Quote added to user" + quoteDTO);
        return Response.ok().entity(quoteDTO).build();
    }


    @GET
    @Path("/age/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String predictAge(@PathParam("name") String name) throws Exception {

        try {
            return GSON.toJson(EXTERNAL_API_FACADE.predictAge("https://api.agify.io/?name=", name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "your name is not in the database";
    }

    @POST
    @Path("/age/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addAgeToUser(@PathParam("username") String userName, String age) {
        AgeDTO ageDTO = GSON.fromJson(age, AgeDTO.class);
        UserDTO userDTO = INTERNAL_API_FACADE.addAge(userName, ageDTO.getAge());
        return Response.ok().entity(userDTO).build();
    }

    // Den udkommenterede metode virker ligesom den ovenover, men gør brug af 2 path params i stedet for en json string

//    @POST
//    @Path("/age/{username}/{age}")
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response addAgeToUser(@PathParam("username") String userName, @PathParam("age") int age) {
//        UserDTO userDTO = INTERNAL_API_FACADE.addAge(userName, age);
//        return Response.ok().entity(userDTO).build();
//    }

    @DELETE
    @Path("/delete/{username}/{quoteId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteQuoteFromUser(@PathParam("username") String userName, @PathParam("quoteId") Long quoteId) {
        UserDTO userDTO = INTERNAL_API_FACADE.removeQuoteFromUser(userName, quoteId);
        return Response.ok().entity(userDTO).build();
    }
}