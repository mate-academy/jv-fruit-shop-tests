package core.basesyntax.model;

import core.basesyntax.exception.OperationNotFoundException;
import java.util.Arrays;
import java.util.Objects;

public class ProductTransaction {
    private final Operation operation;
    private final String product;
    private final int quantity;

    public ProductTransaction(Operation operation, String product, int quantity) {
        this.operation = operation;
        this.product = product;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public static ProductTransaction of(String operation, String product, String quantity) {
        if (product == null || product.isEmpty()) {
            throw new RuntimeException("Product can't be empty");
        }
        int quantityInteger = Integer.parseInt(quantity);
        if (quantityInteger < 0) {
            throw new RuntimeException("There's negative value of quantity " + quantityInteger
                    + " for product " + product);
        }
        Operation operationOperation = Operation.convertStringToOperation(operation);
        return new ProductTransaction(operationOperation, product, quantityInteger);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductTransaction that = (ProductTransaction) o;
        return quantity == that.quantity && operation == that.operation
                && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, product, quantity);
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }

        private static Operation convertStringToOperation(String operation) {
            return Arrays.stream(Operation.values())
                    .filter(o -> o.getOperation().equalsIgnoreCase(operation))
                    .findFirst()
                    .orElseThrow(() ->
                            new OperationNotFoundException(
                                    String.format("Error convert input string '%s' into operation",
                                            operation)));
        }
    }
}
