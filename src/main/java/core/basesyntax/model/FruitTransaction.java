package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private OperationType operationType;
    private String fruitName;
    private int quantity;

    public FruitTransaction(OperationType operationType, String fruitType, int quantity) {
        this.operationType = operationType;
        this.fruitName = fruitType;
        this.quantity = quantity;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getFruitName() {
        return fruitName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public enum OperationType {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String code;

        OperationType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static OperationType getOperationTypeByCode(String code) {
            for (OperationType operationType : OperationType.values()) {
                if (operationType.getCode().equals(code)) {
                    return operationType;
                }
            }
            throw new IllegalArgumentException("Invalid Operation code: " + code);
        }
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
        return quantity == that.quantity
                    && operationType == that.operationType
                    && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruitName, quantity);
    }
}
