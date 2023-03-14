package core.basesyntax.model;

import java.util.Arrays;
import java.util.Objects;

public class FruitTransaction {
    private final Operation operation;
    private final String productName;
    private final int quantity;

    public FruitTransaction(Operation operation, String productName, int quantity) {
        this.operation = operation;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, operation, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitTransaction transaction = (FruitTransaction) o;
        return Objects.equals(transaction.productName, productName)
                && Objects.equals(transaction.operation, operation)
                && transaction.quantity == quantity;
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "operation=" + operation
                + ", productName='" + productName + '\''
                + ", quantity=" + quantity + '}';
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

        public static Operation getByCode(String operationCode) {
            return Arrays.stream(values())
                    .filter(operation -> operation.getCode().equals(operationCode))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Invalid operation code '"
                            + operationCode + '\''));
        }
    }
}
