package product.dtos;

public class CreateProductDTO {
    private String name;
    private float price;
    private int productYear;
    private String image;
    private int categoryId;

    public CreateProductDTO(String name, String price, String productYear, String image, String categoryId)
            throws NumberFormatException {
        this.name = name;
        this.price = Float.parseFloat(price);
        this.productYear = Integer.parseInt(productYear);
        this.image = image;
        this.categoryId = Integer.parseInt(categoryId);
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