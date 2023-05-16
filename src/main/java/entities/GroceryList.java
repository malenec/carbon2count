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

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @OneToMany(mappedBy = "groceryList", orphanRemoval = true)
    private List<GroceryLine> groceryLines = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "user_name")
    private User user;

    @PrePersist
    public void onPersist() {
        created = LocalDateTime.now(ZoneId.of("GMT+02:00"));
    }

    public GroceryList() {
    }

    public List<GroceryLine> getGroceryLines() {
        return groceryLines;
    }

    public void addGroceryLine(GroceryLine groceryLine) {
        this.groceryLines.add(groceryLine);
        groceryLine.setGroceryList(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "GroceryList{" +
                "id=" + id +
                ", created=" + created +
                ", groceryLines=" + groceryLines +
                ", user=" + user +
                '}';
    }
}