package core.basesyntax.model;

import java.util.Objects;

public class TransactionDto {
    private OperationTypes operationType;
    private final String fruit;
    private final int amount;

    public TransactionDto(String operationType, String fruit, int amount) {
        this.operationType = OperationTypes.valueOfShortName(operationType);
        this.fruit = fruit;
        this.amount = amount;
    }

    public String getOperationType() {
        return operationType.getShortName();
    }

    public String getFruit() {
        return fruit;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TransactionDto dto = (TransactionDto) obj;
        return amount == dto.amount
                && operationType == dto.operationType
                && Objects.equals(fruit, dto.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, fruit, amount);
    }
}
