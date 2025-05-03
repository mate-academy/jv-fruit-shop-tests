package core.basesyntax.model;

import java.util.Arrays;
import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruitName;
    private int fruitQuantity;

    public FruitTransaction(Operation operation, String fruitName, int quantity) {
        this.operation = operation;
        this.fruitName = fruitName;
        this.fruitQuantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruitName() {
        return fruitName;
    }

    public int getFruitQuantity() {
        return fruitQuantity;
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
        if (fruitQuantity != that.fruitQuantity) {
            return false;
        }
        if (operation != that.operation) {
            return false;
        }
        return Objects.equals(fruitName, that.fruitName);
    }

    @Override
    public int hashCode() {
        int result = operation != null ? operation.hashCode() : 0;
        result = 31 * result + (fruitName != null ? fruitName.hashCode() : 0);
        result = 31 * result + fruitQuantity;
        return result;
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
            return Arrays.stream(FruitTransaction.Operation.values())
                    .filter(op -> op.getCode().equals(code))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown opcode: "
                            + code));
        }
    }
}
