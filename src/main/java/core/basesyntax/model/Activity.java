package core.basesyntax.model;

public class Activity {
    private final Operation operation;
    private final String item;
    private final int quantity;

    public Activity(Builder builder) {
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

    public static class Builder {
        private Operation operation;
        private String item;
        private int quantity;

        public Builder() {
        }

        public Builder setOperation(Operation operation) {
            this.operation = operation;
            return this;
        }

        public Builder setItem(String item) {
            if (item.isEmpty()) {
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

        public Activity build() {
            return new Activity(this);
        }
    }
}
