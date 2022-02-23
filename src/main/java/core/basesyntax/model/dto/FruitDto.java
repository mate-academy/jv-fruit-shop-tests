package core.basesyntax.model.dto;

public class FruitDto {
    private String name;
    private int quantity;
    private String type;

    public FruitDto() {
    }

    public FruitDto(String name, int quantity, String type) {
        this.name = name;
        this.quantity = quantity;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FruitDto fruitDto = (FruitDto) o;

        if (quantity != fruitDto.quantity) {
            return false;
        }
        if (name != null ? !name.equals(fruitDto.name) : fruitDto.name != null) {
            return false;
        }
        return type != null ? type.equals(fruitDto.type) : fruitDto.type == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + quantity;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
