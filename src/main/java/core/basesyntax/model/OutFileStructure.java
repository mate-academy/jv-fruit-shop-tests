package core.basesyntax.model;
//
public class OutFileStructure {
    private String fruit;
    private String quantity;

    public OutFileStructure(String fruit, String quantity) {
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public String getFruit() {
        return fruit;
    }

    public String getQuantity() {
        return quantity;
    }
}
