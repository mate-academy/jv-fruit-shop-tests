package core.basesyntax.model;

public class FruitTransaction {
    private Operation operation;
    private Fruit fruit;
    private int quantity;

    public FruitTransaction(Operation operation, Fruit fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public Fruit getFruit() {
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

        private final String alias;

        Operation(String alias) {
            this.alias = alias;
        }

        public static Operation getOperationByAlias(String alias) {
            for (Operation operation : Operation.values()) {
                if (operation.alias.equalsIgnoreCase(alias)) {
                    return operation;
                }
            }
            throw new RuntimeException("No operations for alias " + alias);
        }
    }
}
