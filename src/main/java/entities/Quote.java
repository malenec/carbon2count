package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;


@Entity
@NamedQuery(name = "Quote.deleteAllRows", query = "DELETE from Quote")
public class Quote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String quote;

    @ManyToMany(mappedBy="quotes")
    private List<User> users;


    public Quote() {
    }

    // TODO, delete this class, or rename to an Entity class that makes sense for what you are about to do
    // Delete EVERYTHING below if you decide to use this class, it's dummy data used for the initial demo


    public Quote(String quote) {
        this.quote = quote;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getQuote() {
        return quote;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                ", users=" + users +
                '}';
    }
}
