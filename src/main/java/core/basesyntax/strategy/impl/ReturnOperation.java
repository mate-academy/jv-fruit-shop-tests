package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Instruction;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.OperationException;

public class ReturnOperation implements Operation {
    private final StorageDao storageDao;

    public ReturnOperation(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void proceed(Instruction instruction) {
        if (instruction.getQuantity() < 0) {
            throw new OperationException("Return can't work with negative value: "
                    + instruction.getQuantity());
        }
        if (!storageDao.contains(instruction.getFruitName())) {
            throw new OperationException("Fruit " + instruction.getFruitName()
                    + " doesn't exist");
        }
        storageDao.add(instruction.getFruitName(),
                storageDao.get(instruction.getFruitName()) + instruction.getQuantity());
    }
}
