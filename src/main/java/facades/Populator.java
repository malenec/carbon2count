/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.RenameMeDTO;
import entities.Hobby;
import entities.Person;
import entities.RenameMe;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade pf = PersonFacade.getPersonFacade(emf);
        PersonDTO pdto1 = new PersonDTO(new Person("Holger", 3));
        pdto1 = pf.create(pdto1);
        pf.create(new PersonDTO(new Person("Vera", 5)));
        pf.create(new PersonDTO(new Person("Olga", 7)));
        HobbyDTO hdto1 = new HobbyDTO(new Hobby("Hobby1", "Hobby1"));
        HobbyDTO hdto2 = new HobbyDTO(new Hobby("Hobby2", "Hobby2"));
        HobbyDTO hdto3 = new HobbyDTO(new Hobby("Hobby3", "Hobby3"));
        HobbyDTO hdto4 = new HobbyDTO(new Hobby("Hobby4", "Hobby4"));
        hdto1 = pf.createHobby(hdto1);
        hdto2 = pf.createHobby(hdto2);
        hdto3 = pf.createHobby(hdto3);
        hdto4 = pf.createHobby(hdto4);
        pf.addHobby(pdto1.getId(), hdto1.getId());
        pf.addHobby(pdto1.getId(), hdto2.getId());
        pf.addHobby(pdto1.getId(), hdto3.getId());
        pf.addHobby(pdto1.getId(), hdto4.getId());
        pf.removeHobbyFromPerson(pdto1.getId(), hdto1.getId());
        pf.removeHobbyFromPerson(pdto1.getId(), hdto2.getId());
        pf.removeHobbyFromPerson(pdto1.getId(), hdto3.getId());
        pf.removeHobbyFromPerson(pdto1.getId(), hdto4.getId());
    }
    
    public static void main(String[] args) {
        populate();
    }
}
