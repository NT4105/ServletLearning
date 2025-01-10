package product.dtos;

public class SearchProductDTO {
    private String productName;
    private Integer categoryId;
    private Float price;
    private String filterBy;

    public SearchProductDTO(String productName, Integer categoryId, Float price, String filterBy) {

        this.productName = productName;
        this.categoryId = categoryId;
        this.price = price;
        this.filterBy = filterBy;
    }

    public String getProductName() {
        return this.productName;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public Float getPrice() {
        return this.price;
    }

    public String getFilterBy() {
        return this.filterBy;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public void setCategoryId(Integer id) {
        this.categoryId = id;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }
}