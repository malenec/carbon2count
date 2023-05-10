package entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name = "grocery_list")

@NamedQueries({
        @NamedQuery(name = "GroceryList.deleteAllRows", query = "DELETE from GroceryList")
})
public class GroceryList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "groceryList", orphanRemoval = true)
    private List<Grocery> groceries = new ArrayList<>();

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

    @PrePersist
    public void onPersist() {
        created = LocalDateTime.now(ZoneId.of("GMT+02:00"));
    }

    public GroceryList() {
    }

    public GroceryList(User user, List<Grocery> groceries, LocalDateTime created) {
        this.user = user;
        this.groceries = groceries;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Grocery> getGroceries() {
        return groceries;
    }

    public void addGrocery(Grocery grocery) {
        this.groceries.add(grocery);
        grocery.setGroceryList(this);
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}