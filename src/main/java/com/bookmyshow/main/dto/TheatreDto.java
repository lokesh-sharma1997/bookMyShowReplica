package com.bookmyshow.main.dto;




public class TheatreDto {

    private String name;

    private String location;

    private String city;

    public TheatreDto() {
    }

    public TheatreDto(String name, String location, String city) {
        this.name = name;
        this.location = location;
        this.city = city;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "TheatreDTO [name=" + name + ", location=" + location + ", city=" + city + "]";
    }
}
