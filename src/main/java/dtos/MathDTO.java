package dtos;

import java.time.LocalDateTime;

public class MathDTO {


    private String created;
    private double groceryListTotalCo2;

    public MathDTO() {
    }

    public MathDTO(String result) {
        this.groceryListTotalCo2 = Double.parseDouble(result);
    }


    public String getCreated() {
        return created;
    }

    public double getGroceryListTotalCo2() {
        return groceryListTotalCo2;
    }

    public void setGroceryListTotalCo2(double groceryListTotalCo2) {
        this.groceryListTotalCo2 = groceryListTotalCo2;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "MathDTO{" +
                "created=" + created +
                ", groceryListTotalCo2=" + groceryListTotalCo2 +
                '}';
    }
}
