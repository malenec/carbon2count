package facades;

import dtos.GroceryDTO;
import dtos.GroceryListDTO;
import dtos.RenameMeDTO;
import entities.Grocery;
import entities.GroceryList;
import entities.RenameMe;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

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

    public RenameMeDTO create(RenameMeDTO rm){
        RenameMe rme = new RenameMe(rm.getDummyStr1(), rm.getDummyStr2());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rme);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new RenameMeDTO(rme);
    }

    public GroceryListDTO createGroceryList(GroceryListDTO groceryListDTO) {

        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, groceryListDTO.getUserName());

        GroceryList groceryList = new GroceryList();

        groceryListDTO.getGroceries().forEach(gDto -> {
            Grocery grocery = em.find(Grocery.class, gDto.getIdRa500prod());
            groceryList.addGrocery(grocery);
        });

        user.addGroceryList(groceryList);

        try {
            em.getTransaction().begin();
            em.persist(groceryList);
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new GroceryListDTO(groceryList);
    }

//    public static List<GroceryListDTO> getAllGroceriesLists(){
//        EntityManager em = emf.createEntityManager();
//        TypedQuery<Grocery> grocery = em.createQuery("SELECT g FROM Grocery g", Grocery.class);
//        List<Grocery> groceries = grocery.getResultList();
//        return GroceryDTO.getDtos(groceries);
//    }
}
