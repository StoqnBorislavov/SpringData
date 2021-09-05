package springdata.gamestore.domain.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DetailsGameDto {

    private String title;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public DetailsGameDto() {
    }

    public DetailsGameDto(String title, BigDecimal price, String description, LocalDate releaseDate) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        //    /*Title: Overwatch
//    Price: 80.00
//    Description: Overwatch is a team-based multiplayer online first-person shooter video game developed and published by Blizzard Entertainment.
//    Release date: 24-05-2016*/
        return String.format("Title: %s%nPrice: %.2f%nDescription: %s%nRelease date: %s",
                this.getTitle(), this.getPrice(), this.getDescription(), this.releaseDate);
    }
}
