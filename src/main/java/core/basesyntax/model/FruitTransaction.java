package core.basesyntax.model;

import core.basesyntax.OperationType;
import java.util.Objects;

public class FruitTransaction {
    private final OperationType type;
    private final String productName;
    private final int amount;

    public FruitTransaction(OperationType type, String productName, int amount) {
        this.type = type;
        this.productName = productName;
        this.amount = amount;
    }

    public OperationType getType() {
        return type;
    }

    public String getProductName() {
        return productName;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) o;
        return amount == that.amount && type == that.type && productName.equals(that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, productName, amount);
    }
}
