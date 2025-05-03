package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private final String fruitName;
    private final int transactionQuantity;

    public FruitTransaction(Operation operation, String fruitName, int transactionQuantity) {
        this.operation = operation;
        this.fruitName = fruitName;
        this.transactionQuantity = transactionQuantity;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String code;

        Operation(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static Operation convertFromCode(String code) {
            Operation[] operations = Operation.values();
            for (Operation operation : operations) {
                if (operation.getCode().equals(code)) {
                    return operation;
                }
            }
            throw new IllegalArgumentException("Invalid operation code: " + code);
        }
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getTransactionQuantity() {
        return transactionQuantity;
    }

    public String getFruitName() {
        return fruitName;
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
        return transactionQuantity == that.transactionQuantity
                && operation == that.operation
                && Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruitName, transactionQuantity);
    }
}
