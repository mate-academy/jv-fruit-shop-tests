package core.basesyntax.model;

import core.basesyntax.exception.ValidationException;
import java.util.Objects;
//
public class FruitTransaction {
    private OperationType operationType;
    private String name;
    private int quantity;

    public FruitTransaction(OperationType operationType, String name, int quantity) {
        if (operationType == null || name == null) {
            throw new ValidationException("Can't create an instance with null data");
        } else if (operationType.getCode().isEmpty()
                || operationType == OperationType.NONE) {
            throw new ValidationException("Can't create an instance "
                    + "with not valid Operation Type");
        } else if (name.isEmpty()) {
            throw new ValidationException("Can't create an instance without fruit name");
        } else if (quantity < 0) {
            throw new ValidationException("Can't create an instance with negative quantity");
        } else {
            this.operationType = operationType;
            this.name = name;
            this.quantity = quantity;
        }
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
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
        return quantity == that.quantity && operationType
                == that.operationType && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, name, quantity);
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "operationType='" + operationType + '\''
                + ", name='" + name + '\''
                + ", quantity=" + quantity
                + '}';
    }
}
