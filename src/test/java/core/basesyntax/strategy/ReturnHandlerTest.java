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

class ReturnHandlerTest {
    private static final String FRUIT = "pear";
    private static final int QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -10;
    private static OperationHandler returnHandler;
    private static StorageDao storageDao;
    private static OperationHandler balanceHandler;
    private FruitTransfer fruitTransfer;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceHandler();
        returnHandler = new ReturnHandler();
        storageDao = new StorageDaoImpl();
        balanceHandler.performOperation(new FruitTransfer(Operation.BALANCE, FRUIT, QUANTITY));
    }

    @Test
    void performOperation_returnFruit_Ok() {
        fruitTransfer = new FruitTransfer(Operation.RETURN, FRUIT, QUANTITY);
        returnHandler.performOperation(fruitTransfer);
        int expected = 20;
        int actual = storageDao.getStoredQuantity(fruitTransfer.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    void performOperation_putNegativeReturn_NotOk() {
        fruitTransfer = new FruitTransfer(Operation.RETURN, FRUIT, NEGATIVE_QUANTITY);
        assertThrows(InvalidDataException.class, () ->
                returnHandler.performOperation(fruitTransfer));
    }
}
