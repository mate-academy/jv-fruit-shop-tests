package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Instruction;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.OperationException;

public class PurchaseOperation implements Operation {
    private final StorageDao storageDao;

    public PurchaseOperation(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void proceed(Instruction instruction) {
        if (!storageDao.contains(instruction.getFruitName())) {
            throw new OperationException("Fruit " + instruction.getFruitName()
                    + " doesn't exist");
        }
        if (instruction.getQuantity() < 0) {
            throw new OperationException("Purchase can't work with negative value: "
                    + instruction.getQuantity());
        }
        if (storageDao.get(instruction.getFruitName()) - instruction.getQuantity() < 0) {
            throw new OperationException("Negative quantity of " + instruction.getFruitName());
        }
        storageDao.add(instruction.getFruitName(),
                storageDao.get(instruction.getFruitName()) - instruction.getQuantity());
    }
}
