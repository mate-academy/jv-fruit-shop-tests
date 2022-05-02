package model;

public class Transaction {
    private String operation;
    private Fruit fruit;
    private int quantity;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

        if (quantity != that.quantity) {
            return false;
        }
        if (operation != null ? !operation.equals(that.operation) : that.operation != null) {
            return false;
        }
        return fruit != null ? fruit.equals(that.fruit) : that.fruit == null;
    }

    @Override
    public int hashCode() {
        int result = operation != null ? operation.hashCode() : 0;
        result = 31 * result + (fruit != null ? fruit.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{"
                +
                "operation='" + operation + '\''
                +
                ", fruit=" + fruit
                +
                ", quantity=" + quantity
                +
                '}';
    }
}
