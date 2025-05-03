package core.basesyntax.model;

import core.basesyntax.exception.FruitException;
import java.util.Arrays;
import java.util.Objects;

public class FruitTransaction {
    private final Operation operation;
    private final String fruit;
    private final int amount;

    public FruitTransaction(Operation operation, String fruit, int amount) {
        this.operation = operation;
        this.fruit = fruit;
        this.amount = amount;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) obj;
        return amount == that.amount && operation == that.operation
                && Objects.equals(fruit, that.fruit);
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
                    .filter(c -> c.getCode().equals(code))
                    .findFirst()
                    .orElseThrow(() -> new FruitException("Unknown code operation \""
                            + code + "\""));
        }
    }
}
