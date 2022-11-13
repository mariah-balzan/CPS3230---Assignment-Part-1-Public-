package org.example.items;

public class ItemDetails {
    //Attributes
    private String heading;
    private String details;
    private String image;
    private String price;

//Constructor with parameters
    public ItemDetails(String heading, String details, String image, String price){
        this.heading = heading;
        this.details = details;
        this.image = image;
        this.price = price;
    }

    //Constructor without parameters
    public ItemDetails(){
    }

    //Getters and Setters
    public String getHeading(){
        return heading;
    }
    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getItemDetails(){
        return details;
    }
    public void setItemDetails(String details) {
        this.details = details;
    }

    public String getImage(){
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice(){
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }


}
