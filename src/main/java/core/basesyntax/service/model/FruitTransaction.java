package core.basesyntax.service.model;

import java.util.Objects;

public class FruitTransaction {
    private final TypeOperation typeOperation;
    private final String fruit;
    private final Integer quantity;

    public FruitTransaction(TypeOperation typeOperation, String fruit, Integer quantity) {
        this.typeOperation = typeOperation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public TypeOperation getTypeOperation() {
        return typeOperation;
    }

    public String getFruit() {
        return fruit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FruitTransaction)) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) o;
        return typeOperation == that.typeOperation
                && Objects.equals(fruit, that.fruit)
                && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeOperation, fruit, quantity);
    }

    public enum TypeOperation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String type;

        TypeOperation(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
