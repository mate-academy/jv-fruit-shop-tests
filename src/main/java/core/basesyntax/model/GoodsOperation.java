package core.basesyntax.model;

import java.util.Objects;

public class GoodsOperation {
    private final TransactionType transactionType;
    private final String item;
    private final int quantity;

    public GoodsOperation(TransactionType transactionType, String item, int quantity) {
        this.transactionType = transactionType;
        this.item = item;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public int getQuantity() {
        return quantity;
    }

    public enum TransactionType {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");
        private final String typesCode;

        TransactionType(String code) {
            this.typesCode = code;
        }

        public static TransactionType getByCode(String code) {
            for (TransactionType type : TransactionType.values()) {
                if (type.typesCode.equals(code)) {
                    return type;
                }
            }
            throw new RuntimeException("Can't find operation type: " + code);
        }
    }

    @Override
    public boolean equals(Object operation) {
        if (operation == null) {
            return false;
        }
        if (operation == this) {
            return true;
        }
        if (operation.getClass().equals(GoodsOperation.class)) {
            GoodsOperation current = (GoodsOperation) operation;
            return (transactionType == current.transactionType)
                    && (Objects.equals(item, current.item))
                    && (quantity == current.quantity);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = transactionType != null ? transactionType.hashCode() : 0;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }
}
