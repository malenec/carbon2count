package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.deleteAllRows", query = "DELETE from User")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "user_name", length = 25)
  private String userName;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "user_pass")
  private String userPass;

  @Column(name = "age")
  private int age;

  @JoinTable(name = "user_roles", joinColumns = {
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
    @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
  @ManyToMany
  private List<Role> roleList = new ArrayList<>();


  @ManyToMany
  @JoinTable(
          name="User_Quote",
          joinColumns=@JoinColumn(name="user_name", referencedColumnName="user_name"),
          inverseJoinColumns=@JoinColumn(name="Quote_id", referencedColumnName="id"))
  private List<Quote> quotes;

  public List<String> getRolesAsStrings() {
    if (roleList.isEmpty()) {
      return null;
    }
    List<String> rolesAsStrings = new ArrayList<>();
    roleList.forEach((role) -> {
        rolesAsStrings.add(role.getRoleName());
      });
    return rolesAsStrings;
  }

  public User() {}

  //TODO Change when password is hashed
   public boolean verifyPassword(String pw){
    return BCrypt.checkpw(pw, userPass);
    }

  public User(String userName, String userPass) {
    this.userName = userName;
    this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
  }

  public void setQuotes(List<Quote> quotes) {
    this.quotes = quotes;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPass() {
    return this.userPass;
  }

  public void setUserPass(String userPass) {
    this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());;
  }

  public List<Role> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<Role> roleList) {
    this.roleList = roleList;
  }

  public void addRole(Role userRole) {
    roleList.add(userRole);
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public List<Quote> getQuotes() {
    return quotes;
  }

  public void addQuote(Quote quote) {
    this.quotes.add(quote);
    quote.addUser(this);
  }

  public void removeQuote(Quote quote) {
    this.quotes.remove(quote);
    quote.removeUser(this);
  }

  @Override
  public String toString() {
    return "User{" +
            "userName='" + userName + '\'' +
            ", userPass='" + userPass + '\'' +
            ", age=" + age +
            ", roleList=" + roleList +
            ", quotes=" + quotes +
            '}';
  }
}
