package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.models.FruitTransfer;
import core.basesyntax.models.InvalidDataException;
import core.basesyntax.models.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final String FRUIT = "pear";
    private static final int QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -10;
    private static OperationHandler balanceHandler;
    private static StorageDao storageDao;
    private FruitTransfer fruitTransfer;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceHandler();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void performOperation_putFruit_Ok() {
        fruitTransfer = new FruitTransfer(Operation.BALANCE, FRUIT,QUANTITY);
        balanceHandler.performOperation(fruitTransfer);
        int expected = QUANTITY;
        int actual = storageDao.getStoredQuantity(fruitTransfer.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    void performOperation_putNegativeBalance_NotOk() {
        fruitTransfer = new FruitTransfer(Operation.BALANCE, FRUIT,NEGATIVE_QUANTITY);
        assertThrows(InvalidDataException.class, () ->
                balanceHandler.performOperation(fruitTransfer));
    }
}
