package utils;


import entities.Quote;
import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
//
//    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
//    // CHANGE the three passwords below, before you uncomment and execute the code below
//    // Also, either delete this file, when users are created or rename and add to .gitignore
//    // Whatever you do DO NOT COMMIT and PUSH with the real passwords


//        STEP 1: CREATES USERS *REMEMBER DROP & CREATE IF YOU'VE MADE CHANGES TO THE USER CLASS IN ENTITIES*
//    User user = new User("user", "test123");
//    User admin = new User("admin", "test123");
//    User both = new User("user_admin", "test123");
//
//    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
//      throw new UnsupportedOperationException("You have not changed the passwords");
//
//    em.getTransaction().begin();
//    Role userRole = new Role("user");
//    Role adminRole = new Role("admin");
//    user.addRole(userRole);
//    admin.addRole(adminRole);
//    both.addRole(userRole);
//    both.addRole(adminRole);
//    em.persist(userRole);
//    em.persist(adminRole);
//    em.persist(user);
//    em.persist(admin);
//    em.persist(both);
//    em.getTransaction().commit();
//    System.out.println("PW: " + user.getUserPass());
//    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
//    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
//    System.out.println("Created TEST Users");
//    System.out.println("Testing user with OK password: " + user.verifyPassword("test123"));


//        STEP 2: CREATES QUOTE FOR THE FIRST TIME *REMEMBER TO RETURN TO CREATE MODE IN PERSISTENCE.XML*
//    em.getTransaction().begin();
//    Quote quote = new Quote("jeg er sej");
//    em.persist(quote);
//    em.getTransaction().commit();


//        STEP 3: ADDS QUOTE TO A USER
//    em.getTransaction().begin();
//    User user = em.find(User.class, "admin");
//    Quote quote = em.find(Quote.class, 1L);
//    System.out.println(user);
//    System.out.println(quote);
//    user.addQuote(quote);
//    em.merge(user);
//    em.getTransaction().commit();
   
  }

}
