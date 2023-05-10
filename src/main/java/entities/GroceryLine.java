package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grocery_line")
public class GroceryLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grocery_list_id")
    private GroceryList groceryList;

    @ManyToOne
    @JoinColumn(name = "grocery_id")
    private Grocery grocery;

    @Column(name = "grocery_quantity")
    private Long groceryQuantity;


    public GroceryLine() {
    }

    public GroceryList getGroceryList() {
        return groceryList;
    }

    public void setGroceryList(GroceryList groceryList) {
        this.groceryList = groceryList;
    }

    public Grocery getGrocery() {
        return grocery;
    }

    public void setGrocery(Grocery grocery) {
        this.grocery = grocery;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroceryQuantity() {
        return groceryQuantity;
    }

    public void setGroceryQuantity(Long groceryQuantity) {
        this.groceryQuantity = groceryQuantity;
    }
}