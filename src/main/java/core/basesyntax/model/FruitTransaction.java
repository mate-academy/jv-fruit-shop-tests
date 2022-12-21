package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private Fruit fruit;
    private Integer amount;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
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
        return operation == that.operation && Objects.equals(fruit, that.fruit)
                && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, amount);
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String operationAbbreviation;

        Operation(String operationAbbreviation) {
            this.operationAbbreviation = operationAbbreviation;
        }

        public static Operation getOperation(String value) {
            for (Operation operation : Operation.values()) {
                if (operation.operationAbbreviation.contains(value)) {
                    return operation;
                }
            }
            throw new RuntimeException("Operation not found with value: " + value);
        }
    }
}
