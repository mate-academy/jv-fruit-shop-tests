package core.basesyntax.model;

import java.util.Objects;

public class FruitOperationDto {
    private Type type;
    private String fruitName;
    private int quantity;

    public FruitOperationDto(Type type, String fruitName, int quantity) {
        this.type = type;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public enum Type {
        BALANCE("b"),
        PURCHASE("p"),
        RETURN("r"),
        SUPPLY("s");

        private final String label;

        Type(String label) {
            this.label = label;
        }

        public static Type valueOfLabel(String label) {
            for (Type type : values()) {
                if (type.label.equals(label)) {
                    return type;
                }
            }
            throw new RuntimeException("Can't find type of operation " + label);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass()
                != o.getClass()) {
            return false;
        }
        FruitOperationDto that = (FruitOperationDto) o;
        return quantity == that.quantity
                && type == that.type
                && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fruitName, quantity);
    }
}
