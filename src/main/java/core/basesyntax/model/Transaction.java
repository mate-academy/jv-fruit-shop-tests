package core.basesyntax.model;

import java.util.Objects;

public abstract class Transaction {
    private OperationType operationType;
    private String productType;
    private int transactionValue;

    public Transaction(OperationType operationType, String productType, int transactionValue) {
        this.operationType = operationType;
        this.productType = productType;
        this.transactionValue = transactionValue;
    }

    public OperationType getTransactionType() {
        return operationType;
    }

    public String getProductType() {
        return productType;
    }

    public int getTransactionValue() {
        return transactionValue;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setTransactionValue(int transactionValue) {
        this.transactionValue = transactionValue;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Transaction that = (Transaction) object;
        return transactionValue == that.transactionValue
                && operationType == that.operationType
                && Objects.equals(productType, that.productType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, productType, transactionValue);
    }
}
