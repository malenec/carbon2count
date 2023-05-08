package dtos;

import entities.Person;
import entities.Quote;

import java.util.List;
import java.util.stream.Collectors;

public class QuoteDTO {
    private Long id;
    private String quote;
    private List<String> users;

    public QuoteDTO(Quote q) {
        this.quote = q.getQuote();
        this.id = q.getId();
        if(q.getUsers() != null)
            this.users = q.getUsers().stream().map(u -> u.getUserName()).collect(Collectors.toList());



    }

    public QuoteDTO(String quote) {
        this.quote = quote;
    }

    public static List<QuoteDTO> getDtos(List<Quote> quotes) {
        return quotes.stream().map(q -> new QuoteDTO(q)).collect(Collectors.toList());
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

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "QuoteDTO{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                ", users=" + users +
                '}';
    }
}
