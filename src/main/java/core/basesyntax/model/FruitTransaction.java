package core.basesyntax.model;

import java.util.Map;
import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruitName() {
        return fruit;
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
        FruitTransaction that = (FruitTransaction) o;
        return quantity == that.quantity && operation == that.operation && fruit.equals(that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");
        private static final Map<String, Operation> operationMap = Map.of(
                "b", Operation.BALANCE,
                "s", Operation.SUPPLY,
                "p", Operation.PURCHASE,
                "r", Operation.RETURN);
        private String code;

        Operation(String code) {
            this.code = code;
        }

        public static Operation getOperation(String operationCode) {
            return operationMap.get(operationCode);
        }
    }
}
