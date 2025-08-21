package com.bookmyshow.main.dto;



public class CityDto {
    private Long id;
    private String name;
    private String imageUrl;

    public CityDto(Long id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }
    public CityDto()
    {
    	
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
	@Override
	public String toString() {
		return "CityDto [id=" + id + ", name=" + name + ", imageUrl=" + imageUrl + "]";
	}
    
    
    
}
