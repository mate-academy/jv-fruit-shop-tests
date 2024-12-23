package core.basesyntax.storage;

import java.util.Objects;

public class FruitTransaction {
    private String fruit;
    private int quantity;
    private Operation operation;

    public FruitTransaction(String fruit, int quantity, Operation operation) {
        this.fruit = fruit;
        this.quantity = quantity;
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

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FruitTransaction that)) {
            return false;
        }
        return getQuantity() == that.getQuantity()
                && Objects.equals(getFruit(), that.getFruit())
                && getOperation() == that.getOperation();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFruit(), getQuantity(), getOperation());
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
            for (Operation operation : values()) {
                if (operation.getCode().equals(code)) {
                    return operation;
                }
            }
            throw new IllegalArgumentException("Unknown operation :" + code);
        }
    }

}
