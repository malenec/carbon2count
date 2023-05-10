package dtos;


import entities.GroceryList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GroceryListDTO {

    private Long groceryListId;
    private LocalDateTime created;
    private String userName;
    private List<GroceryLineDTO> groceryLineDTOs = new ArrayList<>();
//    private List<GroceryDTO> groceries;

    public GroceryListDTO() {
    }

    //denne konstruktor bruges til at sende objektet i db første gang
    public GroceryListDTO(List<GroceryLineDTO> groceryLineDTOs) {
        this.groceryLineDTOs = groceryLineDTOs;
    }

    //denne konstruktor bruges til at hente objektet op fra db
    public GroceryListDTO(GroceryList groceryList) {
        this.userName = groceryList.getUser().getUserName();
        this.created = groceryList.getCreated();
        if(groceryList.getGroceryLines() != null){
            this.groceryLineDTOs = GroceryLineDTO.getDtos(groceryList.getGroceryLines());
        }
    }

    //denne metode bruges til at hente alle lister til en given user op fra db
    public static List<GroceryListDTO> getDtos(List<GroceryList> groceryLists){
        return groceryLists.stream().map(groceryList->new GroceryListDTO(groceryList)).collect(Collectors.toList());
  }


    public Long getGroceryListId() {
        return groceryListId;
    }

    public void setGroceryListId(Long groceryListId) {
        this.groceryListId = groceryListId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<GroceryLineDTO> getGroceryLineDTOs() {
        return groceryLineDTOs;
    }

    public void setGroceryLineDTOs(List<GroceryLineDTO> groceryLineDTOs) {
        this.groceryLineDTOs = groceryLineDTOs;
    }

    public void addGroceryLineDTO(GroceryLineDTO groceryLineDTO){
        this.groceryLineDTOs.add(groceryLineDTO);
    }

    @Override
    public String toString() {
        return "GroceryListDTO{" +
                "groceryListId=" + groceryListId +
                ", created=" + created +
                ", userName='" + userName + '\'' +
                ", groceryLineDTOs=" + groceryLineDTOs +
                '}';
    }
}
