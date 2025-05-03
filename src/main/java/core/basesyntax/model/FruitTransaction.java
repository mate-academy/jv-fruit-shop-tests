package core.basesyntax.model;

import java.util.Arrays;
import java.util.Objects;

public class FruitTransaction {
    private final Operation operation;
    private final String fruit;
    private final int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
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

        public static Operation getOperationByCode(String code) {
            return Arrays.stream(Operation.values())
                    .filter(x -> x.getCode().equals(code))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Unknown code operation by "
                            + code));
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
        return getQuantity() == that.getQuantity() && getOperation()
                == that.getOperation() && Objects.equals(getFruit(), that.getFruit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperation(), getFruit(), getQuantity());
    }
}
