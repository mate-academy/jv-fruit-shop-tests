package core.basesyntax.model;

import java.util.Arrays;
import java.util.Objects;

public class Activity {
    private final Operation operation;
    private final String item;
    private final int quantity;

    private Activity(Builder builder) {
        this.operation = builder.operation;
        this.item = builder.item;
        this.quantity = builder.quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getItem() {
        return item;
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
        Activity activity = (Activity) o;
        return getQuantity() == activity.getQuantity()
                && getOperation() == activity.getOperation()
                && getItem().equals(activity.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperation(), getItem(), getQuantity());
    }

    @Override
    public String toString() {
        return "Activity{"
                + "operation='" + operation.getOperationCode() + '\''
                + ", item='" + item + '\''
                + ", quantity=" + quantity
                + '}';
    }

    public enum Operation {
        BALANCE("b"), SUPPLY("s"), PURCHASE("p"), RETURN("r");
        private final String operationCode;

        Operation(String operationCode) {
            this.operationCode = operationCode;
        }

        public String getOperationCode() {
            return operationCode;
        }

        public static Operation getByCode(String code) {
            return Arrays.stream(Operation.values())
                    .filter(o -> o.operationCode.equals(code))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Unsupported operation code: " + code));
        }
    }

    public static class Builder {
        private Operation operation;
        private String item;
        private int quantity;

        public Builder() {
        }

        public Builder setItem(String item) {
            if (item.isEmpty() || item.isBlank()) {
                throw new RuntimeException("Incorrect name of item");
            }
            this.item = item;
            return this;
        }

        public Builder setQuantity(int quantity) {
            if (quantity < 0) {
                throw new RuntimeException("Incorrect value of quantity");
            }
            this.quantity = quantity;
            return this;
        }

        public Builder setOperation(String operationCode) {
            this.operation = Operation.getByCode(operationCode);
            return this;
        }

        public Activity build() {
            return new Activity(this);
        }
    }
}
