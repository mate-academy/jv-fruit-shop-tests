package core.basesyntax.model;

import java.util.Objects;

public class ParsedLine {
    private final String operation;
    private final String fruitName;
    private final int quantity;

    public ParsedLine(String operation, String fruitName, int quantity) {
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
        ParsedLine line = (ParsedLine) o;
        return quantity == line.quantity
                && Objects.equals(operation, line.operation)
                && Objects.equals(fruitName, line.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruitName, quantity);
    }

    @Override
    public String toString() {
        return "ParsedLine{"
                + "operation='" + operation + '\''
                + ", fruitName='" + fruitName + '\''
                + ", quantity=" + quantity + '}';
    }
}
