package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.BalanceOperation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void balanceOperation_ok() {
        BalanceOperation bo = new BalanceOperation();
        FruitTransaction ft = new FruitTransaction("banana", Operation.BALANCE, 50);
        bo.apply(ft);
        int actual = Storage.getStorage().get("banana");
        assertEquals(50, actual);
    }
}
