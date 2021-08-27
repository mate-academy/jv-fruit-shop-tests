package core.basesyntax.dto;

public class ShopOperation {
    private final String operation;
    private final String fruitName;
    private final int quantity;

    public ShopOperation(String operation, String fruitName, int quantity) {
        this.operation = operation;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ShopOperation{"
                + "operation='" + operation + '\''
                + ", fruitName='" + fruitName + '\''
                + ", quantity=" + quantity + '}';
    }

    public String getOperation() {
        return operation;
    }

    public String getFruitName() {
        return fruitName;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && getClass() == o.getClass()) {
            ShopOperation that = (ShopOperation) o;

            if (getQuantity() != that.getQuantity()) {
                return false;
            }
            return (getOperation() != null
                    ? getOperation().equals(that.getOperation())
                    : that.getOperation() == null) && (getFruitName()
                    != null ? getFruitName().equals(that.getFruitName())
                    : that.getFruitName() == null);

        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = getOperation() != null ? getOperation().hashCode() : 0;
        result = 31 * result + (getFruitName() != null ? getFruitName().hashCode() : 0);
        result = 31 * result + getQuantity();
        return result;
    }
}
