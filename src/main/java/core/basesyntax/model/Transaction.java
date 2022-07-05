package core.basesyntax.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Transaction implements Cloneable {
    private static final Map<String, Operation> operationsMap = new HashMap<>();
    private Operation operation;
    private Fruit fruit;
    private Integer quantity;

    public Transaction(Operation operation, Fruit fruit, Integer quantity) {
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

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }

        public static Operation getOperation(String abbreviature) {
            return operationsMap.get(abbreviature);
        }

        static {
            for (Operation enumOperation : Operation.values()) {
                operationsMap.put(enumOperation.getOperation(), enumOperation);
            }
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
        Transaction that = (Transaction) o;
        return operation == that.operation
                && Objects.equals(fruit, that.fruit)
                && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }

    @Override
    public Transaction clone() {
        try {
            Object clone = super.clone();
            return new Transaction(Operation.getOperation(operation.name()),
                    new Fruit(fruit.getName()),
                    quantity);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

    }
}
