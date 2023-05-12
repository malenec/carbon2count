package dtos;

import entities.User;
import entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private String username;

    private List<GroceryListDTO> groceryLists;

    public UserDTO(User u) {
        this.username = u.getUserName();
        if(u.getGroceryLists() != null)
            this.groceryLists = u.getGroceryLists().stream().map(g -> new GroceryListDTO(g)).collect(Collectors.toList());
    }

    public static List<UserDTO> getDtos(List<User> Users) {
        return Users.stream().map(p -> new UserDTO(p)).collect(Collectors.toList());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<GroceryListDTO> getGroceryLists() {
        return groceryLists;
    }

    public void setGroceryLists(List<GroceryListDTO> groceryLists) {
        this.groceryLists = groceryLists;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", groceryLists=" + groceryLists +
                '}';
    }
}
