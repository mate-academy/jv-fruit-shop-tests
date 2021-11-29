package core.basesyntax.model;

import java.util.Objects;

public class TransactionDto {
    private final String operation;
    private final String fruitName;
    private final int quantity;

    public TransactionDto(String operation, String fruitName, int quantity) {
        this.operation = operation;
        this.fruitName = fruitName;
        this.quantity = quantity;
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransactionDto that = (TransactionDto) o;
        if (quantity != that.quantity) {
            return false;
        }
        if (!Objects.equals(operation, that.operation)) {
            return false;
        }
        return Objects.equals(fruitName, that.fruitName);
    }
}
