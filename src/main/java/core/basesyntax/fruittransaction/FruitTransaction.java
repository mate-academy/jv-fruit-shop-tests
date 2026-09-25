package core.basesyntax.fruittransaction;

import java.util.Objects;

public record FruitTransaction(Operation operation, String fruitName, int quantity) {

    @Override
    public String toString() {
        return System.lineSeparator() + "FruitTransaction{"
                + "operation=" + operation + ", fruitName='"
                + fruitName + '\'' + ", quantity=" + quantity + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FruitTransaction that = (FruitTransaction) o;
        return quantity() == that.quantity()
                && operation() == that.operation()
                && Objects.equals(fruitName(), that.fruitName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation(), fruitName(), quantity());
    }
}
