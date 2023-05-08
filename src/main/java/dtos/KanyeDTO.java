package dtos;

public class KanyeDTO {
    private String quote;


    public KanyeDTO(String quote) {
        this.quote = quote;
    }


    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "KanyeDTO{" +
                "quote='" + quote + '\'' +
                '}';
    }
}
