package core.basesyntax.model;

import java.util.Objects;

public class TransactionDto {
    private String operation;
    private String fruit;
    private int quantity;

    public TransactionDto(String operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public String getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.operation);
        hash = 71 * hash + Objects.hashCode(this.fruit);
        hash = 71 * hash + this.quantity;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransactionDto other = (TransactionDto) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.operation, other.operation)) {
            return false;
        }
        if (!Objects.equals(this.fruit, other.fruit)) {
            return false;
        }
        return true;
    }
}
