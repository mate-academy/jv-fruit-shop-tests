package core.basesyntax.transaction;

import java.util.Objects;

public class Transaction {
    private Operation operation;
    private String product;
    private int quantity;

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String fruit) {
        this.product = fruit;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (operation == null ? 0 : operation.hashCode());
        result = 31 * result + (product == null ? 0 : product.hashCode());
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public boolean equals(Object transaction) {
        if (transaction == this) {
            return true;
        }
        if (transaction == null) {
            return false;
        }
        if (transaction instanceof Transaction) {
            Transaction current = (Transaction) transaction;
            return Objects.equals(this.operation, current.operation)
                    && Objects.equals(this.product, current.product)
                    && this.quantity == current.getQuantity();
        }
        return false;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String code;

        Operation(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static Operation getByCode(String code) {
            for (Operation operationName : Operation.values()) {
                if (operationName.getCode().equals(code)) {
                    return operationName;
                }
            }
            throw new IllegalArgumentException("Invalid operation code: " + code);
        }
    }
}
