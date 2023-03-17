package core.basesyntax.model;

public class FruitTransaction {
    private String operation;
    private String fruit;
    private Integer value;

    public FruitTransaction(String operation, String fruit, Integer value) {
        this.operation = operation;
        this.fruit = fruit;
        this.value = value;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getValue() {
        return value;
    }
}
