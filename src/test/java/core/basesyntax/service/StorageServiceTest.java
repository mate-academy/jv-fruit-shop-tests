package core.basesyntax.service;

import core.basesyntax.dto.Fruit;
import core.basesyntax.dto.Operation;
import core.basesyntax.exceptions.AlreadyHaveItException;
import org.junit.Test;

import static org.junit.Assert.*;

public class StorageServiceTest {
    private static final Fruit banana = new Fruit("banana");

    @Test(expected = AlreadyHaveItException.class)
    public void handleTwoBalanceOperationsOnOneFruit_NotOk() {
        StorageService storage = new StorageService();
        storage.create(banana, new Operation(Operation.OperationType.BALANCE, banana, 10));
        storage.create(banana, new Operation(Operation.OperationType.BALANCE, banana, 10));
    }
}