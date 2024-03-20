package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operationType;
    private String productName;
    private int amount;

    public FruitTransaction() {
    }

    public FruitTransaction(Operation operationType, String productType, int amount) {
        this.operationType = operationType;
        this.productName = productType;
        this.amount = amount;
    }

    public Operation getOperationType() {
        return operationType;
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
        return amount == that.amount && operationType == that.operationType && Objects.equals(
            productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, productName, amount);
    }
}
