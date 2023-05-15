package core.basesyntax.model;

import java.util.HashMap;
import java.util.Map;

public class FruitTransaction {
    private String name;
    private Integer quantity;
    private Operation operation;

    public FruitTransaction(String name, Integer quantity, Operation operation) {
        this.name = name;
        this.quantity = quantity;
        this.operation = operation;
    }
    
    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private static final Map<String, Operation> BY_LABEL = new HashMap<>();

        static {
            for (Operation o : values()) {
                BY_LABEL.put(o.label, o);
            }
        }

        private String label;

        Operation(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }   

        public static Operation getByLabel(String label) {
            return BY_LABEL.get(label);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
        result = prime * result + ((operation == null) ? 0 : operation.hashCode());
        return result;
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
        FruitTransaction other = (FruitTransaction) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (quantity == null) {
            if (other.quantity != null) {
                return false;
            }
        } else if (!quantity.equals(other.quantity)) {
            return false;
        }
        if (operation != other.operation) {
            return false;
        }
        return true;
    }
}
