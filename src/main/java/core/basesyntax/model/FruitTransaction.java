package core.basesyntax.model;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

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

        private final String code;

        Operation(String code) {
            this.code = code;
        }

        public static Operation getOperationByCode(String code) {
            for (Operation operationType : Operation.values()) {
                if (operationType.code.equals(code)) {
                    return operationType;
                }
            }
            throw new RuntimeException("Incorrect code: " + code);
        }

        public static Operation getOperation(String nameOperation) {
            return Stream.of(Operation.values())
                    .filter(name -> name.getCode().equals(nameOperation))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException(nameOperation));
        }

        public String getCode() {
            return code;
        }
    }
}
