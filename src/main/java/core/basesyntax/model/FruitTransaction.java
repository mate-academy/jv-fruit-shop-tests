package core.basesyntax.model;

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

    public String getFruitName() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FruitTransaction)) {
            return false;
        }
        FruitTransaction compare = (FruitTransaction) o;
        return this.fruit.equals(compare.fruit)
                && this.operation.equals(compare.operation)
                && this.quantity == compare.quantity;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        public static Operation getOperationByCode(String operationCode) {
            for (Operation elem : Operation.values()) {
                if (elem.operation.equals(operationCode)) {
                    return elem;
                }
            }
            throw new RuntimeException("Wrong operation code " + operationCode);
        }
    }
}
