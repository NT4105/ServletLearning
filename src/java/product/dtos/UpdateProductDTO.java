package product.dtos;

public class UpdateProductDTO {
    private int id;
    private String name;
    private float price;
    private int productYear;
    private String image;
    private int categoryId;

    public UpdateProductDTO(String id, String name, String price, String productYear, String image, String categoryId)
            throws NumberFormatException {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.price = Float.parseFloat(price);
        this.productYear = Integer.parseInt(productYear);
        this.image = image;
        this.categoryId = Integer.parseInt(categoryId);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getProductYear() {
        return productYear;
    }

    public String getImage() {
        return image;
    }

    public int getCategoryId() {
        return categoryId;
    }
}