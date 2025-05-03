package core.basesyntax.service.impl.operation;

import core.basesyntax.dao.FruitStorageDao;
import java.util.Objects;

public class ReturnOperation implements OperationHandler {
    private final FruitStorageDao storageDao;

    public ReturnOperation(FruitStorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void doOperation(String fruitName, Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(
                    "Quantity cannot be negative for return operation: " + quantity
            );
        }

        Integer previousQuantity;
        Integer currentQuantity = storageDao.getQuantity(fruitName);

        previousQuantity = Objects.requireNonNullElse(currentQuantity, 0);

        Integer newQuantity = previousQuantity + quantity;
        storageDao.setQuantity(fruitName, newQuantity);
    }
}
