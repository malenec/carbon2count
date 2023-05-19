package facades;

import dtos.GroceryDTO;
import entities.Grocery;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class GroceryFacade {

    private static GroceryFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private GroceryFacade() {}

    public static GroceryFacade getGroceryFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GroceryFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getGroceryCount(){
        EntityManager em = getEntityManager();
        try{
            long GroceryCount = (long)em.createQuery("SELECT COUNT(g) FROM Grocery g").getSingleResult();
            return GroceryCount;
        }finally{
            em.close();
        }
    }

    public List<GroceryDTO> getAllGroceries(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Grocery> grocery = em.createQuery("SELECT g FROM Grocery g", Grocery.class);
        List<Grocery> groceries = grocery.getResultList();
        return GroceryDTO.getDtos(groceries);
    }

    public  GroceryDTO getGroceryById(String id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Grocery> grocery = em.createQuery("select g from Grocery g WHERE g.idRa500prod = :id", Grocery.class);
        grocery.setParameter("id", id);
        GroceryDTO groceryDTO1 = new GroceryDTO(grocery.getSingleResult());

        return groceryDTO1;
    }

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
       //getGroceryById("Ra00468");

    }

}
