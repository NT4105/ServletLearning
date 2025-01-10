package product.dtos;

public class DeleteProductDTO {
    private int id;

    public DeleteProductDTO(String id) throws NumberFormatException {
        this.id = Integer.parseInt(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}