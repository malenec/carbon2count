package facades;

import dtos.QuoteDTO;
import dtos.UserDTO;

import entities.Quote;
import entities.User;


import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class InternalApiFacade {

    private static InternalApiFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private InternalApiFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static InternalApiFacade getInternalApiFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new InternalApiFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

        //This method is both a create and update method
    public UserDTO addAge(String username, int age){
        EntityManager em = emf.createEntityManager();
        User u = em.find(User.class, username);
        if(u == null)
            throw new IllegalArgumentException("User not found");
        u.setAge(age);
        try {
            em.getTransaction().begin();
            em.merge(u);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(u);
    }


    public QuoteDTO createQuote(QuoteDTO qdto){
        Quote q = new Quote(qdto.getQuote());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(q);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new QuoteDTO(q);
    }

    public UserDTO removeQuoteFromUser(String username, Long QuoteId){
        EntityManager em = getEntityManager();
        User u = em.find(User.class, username);
        Quote q = em.find(Quote.class, QuoteId);
        if(u == null || q == null)
            throw new IllegalArgumentException("user or Quote not found");
        u.removeQuote(q);
        try {
            em.getTransaction().begin();
            em.merge(u);
            //em.merge(h);
            em.getTransaction().commit();
        }catch (Exception e){
            System.out.println("No user or quote with that id found");
            return null;
        } finally {
            em.close();
        }
        return new UserDTO(u);
    }


    //Adds Quote to user
    public UserDTO addQuote(String username, Long QuoteId){
        EntityManager em = getEntityManager();
        User u = em.find(User.class, username);
        Quote q = em.find(Quote.class, QuoteId);
        if(u == null || q == null)
            throw new IllegalArgumentException("user or Quote not found");
        u.addQuote(q);
        try {
            em.getTransaction().begin();
            em.merge(u);
            //em.merge(h);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(u);
    }

    public List<QuoteDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Quote> query = em.createQuery("SELECT q FROM Quote q", Quote.class);
        List<Quote> quotes = query.getResultList();
        return QuoteDTO.getDtos(quotes);
    }
    
    

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        InternalApiFacade iaf = getInternalApiFacade(emf);
        iaf.getAll().forEach(dto->System.out.println(dto));

//        QuoteDTO qdto = new QuoteDTO(new Quote("test"));
//        iaf.createQuote(qdto);
//        iaf.getAll().forEach(dto->System.out.println(dto));

//        iaf.addQuote("user", 2L);
        iaf.addAge("user", 30);
    }

}
