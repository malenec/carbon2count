package dtos;

import entities.GroceryList;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class GroceryListDTO {

    private Long id;
    private String userName;
    private LocalDateTime created;
    private List<GroceryDTO> groceries;

    //denne konstruktor bruges til at sende objektet i db første gang
    public GroceryListDTO(String userName, List<GroceryDTO> groceries) {
        this.userName = userName;
        this.groceries = groceries;
    }

    //denne konstruktor bruges til at hente objektet op fra db
    public GroceryListDTO(GroceryList groceryList) {
        this.userName = groceryList.getUser().getUserName();
        this.created = groceryList.getCreated();
        if(groceryList.getGroceries() != null)
            this.groceries = groceryList.getGroceries().stream().map(g -> new GroceryDTO(g)).collect(Collectors.toList());
    }

    //denne metode bruges til at hente alle lister til en given user op fra db
    public static List<GroceryListDTO> getDtos(List<GroceryList> groceryLists){
        return groceryLists.stream().map(groceryList->new GroceryListDTO(groceryList)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<GroceryDTO> getGroceries() {
        return groceries;
    }

    public void setGroceries(List<GroceryDTO> groceries) {
        this.groceries = groceries;
    }

    @Override
    public String toString() {
        return "GroceryListDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", created=" + created +
                ", groceries=" + groceries +
                '}';
    }
}
