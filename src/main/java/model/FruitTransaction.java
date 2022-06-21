package model;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(String symbol, String fruit, int quantity) {
        this.operation = assignOperationBySymbol(symbol);
        this.fruit = fruit;
        this.quantity = quantity;
    }

    private Operation assignOperationBySymbol(String symbol) {
        switch (symbol) {
            case "b":
                return Operation.BALANCE;
            case "s":
                return Operation.SUPPLY;
            case "r":
                return Operation.RETURN;
            case "p":
                return Operation.PURCHASE;
            default:
                throw new RuntimeException("There isn't such supported"
                        + " operation for input symbol " + symbol);
        }
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

        private final String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        FruitTransaction transaction = (FruitTransaction) obj;
        if (!this.operation.equals(transaction.getOperation())) {
            return false;
        } else if (!this.fruit.equals(transaction.getFruit())) {
            return false;
        } else {
            return this.quantity == transaction.getQuantity();
        }
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash += 31 + (operation.getOperation() == null ? 0 : operation.getOperation().hashCode());
        hash += 31 + (fruit == null ? 0 : fruit.hashCode());
        hash += 31 + quantity;
        return hash;
    }
}
