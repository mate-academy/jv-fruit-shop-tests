package core.basesyntax.model;

import java.util.Objects;

public class Transaction {
    private Operation operation;
    private Fruit fruit;
    private Integer sum;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Transaction{"
                + "operation='" + operation + '\''
                + ", fruit=" + fruit + ", quanity =" + sum + '}';
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
                && Objects.equals(sum, that.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, sum);
    }
}
