package dtos;

import entities.GroceryLine;
import entities.GroceryList;

import java.util.List;
import java.util.stream.Collectors;

public class GroceryLineDTO {

    private Long groceryLineId;
    private Long groceryListId;
    private String groceryId;
    private Long groceryQuantity;

    //denne konstruktor bruges til at sende objektet i db første gang
    public GroceryLineDTO(String groceryId, Long groceryQuantity) {
        this.groceryId = groceryId;
        this.groceryQuantity = groceryQuantity;
    }

    //denne konstruktor bruges til at hente objektet op fra db
    public GroceryLineDTO(GroceryLine groceryLine) {
        this.groceryLineId = groceryLine.getId();
        this.groceryListId = groceryLine.getGroceryList().getId();
        this.groceryId = groceryLine.getGrocery().getIdRa500prod();
        this.groceryQuantity = groceryLine.getGroceryQuantity();
    }

    //denne metode bruges til at hente alle lister til en given user op fra db
    public static List<GroceryLineDTO> getDtos(List<GroceryLine> groceryLines){
        return groceryLines.stream().map(groceryLine->new GroceryLineDTO(groceryLine)).collect(Collectors.toList());
    }

    public Long getGroceryLineId() {
        return groceryLineId;
    }

    public void setGroceryLineId(Long groceryLineId) {
        this.groceryLineId = groceryLineId;
    }

    public Long getGroceryListId() {
        return groceryListId;
    }

    public void setGroceryListId(Long groceryListId) {
        this.groceryListId = groceryListId;
    }

    public String getGroceryId() {
        return groceryId;
    }

    public void setGroceryId(String groceryId) {
        this.groceryId = groceryId;
    }

    public Long getGroceryQuantity() {
        return groceryQuantity;
    }

    public void setGroceryQuantity(Long groceryQuantity) {
        this.groceryQuantity = groceryQuantity;
    }

    @Override
    public String toString() {
        return "GroceryLineDTO{" +
                "groceryLineId=" + groceryLineId +
                ", groceryListId=" + groceryListId +
                ", groceryId='" + groceryId + '\'' +
                ", groceryQuantity=" + groceryQuantity +
                '}';
    }
}
