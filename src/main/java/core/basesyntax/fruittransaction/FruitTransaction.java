package core.basesyntax.fruittransaction;

import core.basesyntax.errors.InvalidCodeException;
import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

        public static Operation getOperationByCode(String code) {
            for (Operation operation : Operation.values()) {
                if (operation.getCode().equals(code)) {
                    return operation;
                }
            }
            throw new InvalidCodeException("Incorrect code for operations" + code);
        }

        public String getCode() {
            return code;
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
        return quantity == that.quantity && operation == that.operation
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "operation=" + operation
                + ", fruit='" + fruit + '\''
                + ", quantity=" + quantity
                + '}';
    }
}

