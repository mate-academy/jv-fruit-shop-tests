package core.basesyntax.fruitshop.model.dto;

import core.basesyntax.fruitshop.service.shopoperation.OperationType;
import java.math.BigDecimal;
import java.util.Objects;

public class FruitOperationDto {
    private final OperationType operationType;
    private final String fruitName;
    private final BigDecimal quantity;

    public FruitOperationDto(OperationType operationType, String fruitName, BigDecimal quantity) {
        this.operationType = operationType;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getFruitName() {
        return fruitName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        FruitOperationDto that = (FruitOperationDto) object;
        return getOperationType() == that.getOperationType()
                && Objects.equals(getFruitName(), that.getFruitName())
                && Objects.equals(getQuantity(), that.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperationType(), getFruitName(), getQuantity());
    }
}
