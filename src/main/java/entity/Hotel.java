package entity;

import lombok.Data;
import lombok.Getter;
import util.StringUtils;

@Data
public class Hotel {
    private String name;
    private int rating;
    private String dateOfConstruction;
    private Country country;
    private City city;
    private String shortDescription;
    private String description;
    private String notes;

    public Hotel withName(String name) {
        this.name = name;
        return this;
    }

    public Hotel withRating(int rating) {
        this.rating = rating;
        return this;
    }

    /**
     * date should be in mm/dd/YY
     */
    public Hotel withDateofConstruction(String date) {
        this.dateOfConstruction = date;
        return this;
    }

    public Hotel withCountry(Hotel.Country country) {
        this.country = country;
        return this;
    }

    public Hotel withCity(Hotel.City city) {
        this.city = city;
        return this;
    }

    public Hotel withShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public Hotel withDescription(String description) {
        this.description = description;
        return this;
    }

    public Hotel withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public static Hotel createDummyHotel() {
        return new Hotel()
                .withName("[AUTOTEST] Hotel " + StringUtils.getRandomString(10))
                .withRating(5)
                .withDateofConstruction("1/1/11")
                .withCity(City.KYIV)
                .withCountry(Country.UKRAINE)
                .withShortDescription("Short Description " + StringUtils.getRandomString(10))
                .withDescription("Description " + StringUtils.getRandomString(10))
                .withNotes("Notes " + StringUtils.getRandomString(10));
    }

    public enum City {
        KYIV("Kyiv");

        private @Getter
        String name;

        City(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Country {
        UKRAINE("Ukraine"),
        USA("USA"),
        UK("UK");

        private @Getter
        String name;

        Country(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
