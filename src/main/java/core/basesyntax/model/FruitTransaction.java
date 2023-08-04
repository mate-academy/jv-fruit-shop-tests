package core.basesyntax.model;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class FruitTransaction {
    private final OperationType operation;
    private final String name;
    private final int amount;

    public FruitTransaction(String operation, String fruit, int amount) {
        this.operation = OperationType.findOperation(operation);
        this.name = fruit;
        this.amount = amount;
    }

    public String getFruitName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public OperationType getOperation() {
        return operation;
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
        return amount == that.amount && operation == that.operation
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, name, amount);
    }

    public enum OperationType {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String code;

        private OperationType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static OperationType findOperation(String code) {
            return Arrays.stream(OperationType.values())
                    .filter(operationType -> operationType.getCode().equals(code))
                    .findFirst().orElseThrow(() ->
                            new NoSuchElementException("Invalid operation type. Operation code: "
                                            + code));
        }
    }
}
