package ecomet;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private String tags;      // for SEO search
    private String category;  // for recommendations
    private String image;     // for product images


    public Product() {}
    public Product(int id, String name, double price, int stock, String image, String tags, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.tags = tags;
        this.category = category;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return name + " (" + price + ") [" + stock + "]";
    }
}
