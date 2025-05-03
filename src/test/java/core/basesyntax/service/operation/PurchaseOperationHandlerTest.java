package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static FruitDao fruitDao;
    private static OperationHandler handler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        handler = new PurchaseOperationHandler(fruitDao);
    }

    @Test
    public void handle_purchaseTransaction_Ok() {
        Storage.fruitsQuantity.put("banana", 15);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 15);
        handler.handle(fruitTransaction);
        Integer expectedBanana = 0;
        Integer actualBanana = Storage.fruitsQuantity.get("banana");
        assertEquals(expectedBanana, actualBanana);
    }

    @Test (expected = RuntimeException.class)
    public void handle_emptyStorage_NotOk() {
        Storage.fruitsQuantity.put("banana", 14);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 15);
        handler.handle(fruitTransaction);
    }

    @Test (expected = RuntimeException.class)
    public void handle_emptyTransaction_NotOk() {
        fruitTransaction = new FruitTransaction();
        handler.handle(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.fruitsQuantity.clear();
    }
}
