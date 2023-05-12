package facades;

import dtos.GroceryListDTO;
import dtos.UserDTO;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GroceryListFacade {

    private static GroceryListFacade instance;
    private static EntityManagerFactory emf;

    public static GroceryListFacade getGroceryListFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GroceryListFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public GroceryListDTO createGroceryList(UserDTO userDTO, GroceryListDTO groceryListDTO) {

        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, userDTO.getUsername());

        GroceryList groceryList = new GroceryList();

        groceryListDTO.getGroceryLineDTOs().forEach(groceryLineDTO -> {

            Grocery grocery = em.find(Grocery.class, groceryLineDTO.getGroceryId());
            GroceryLine groceryLine = new GroceryLine();
            groceryLine.setGroceryQuantity(groceryLineDTO.getGroceryQuantity());
            groceryLine.setGrocery(grocery);
            groceryList.addGroceryLine(groceryLine);
        });

        user.addGroceryList(groceryList);

        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new GroceryListDTO(groceryList);
    }

    public User getUser(String username){
        EntityManager em = emf.createEntityManager();
        return em.find(User.class, username);
    }

//    public static List<GroceryListDTO> getAllGroceriesLists(){
//        EntityManager em = emf.createEntityManager();
//        TypedQuery<Grocery> grocery = em.createQuery("SELECT g FROM Grocery g", Grocery.class);
//        List<Grocery> groceries = grocery.getResultList();
//        return GroceryDTO.getDtos(groceries);
//    }
}
