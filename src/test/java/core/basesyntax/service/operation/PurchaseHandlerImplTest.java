package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Test;

public class PurchaseHandlerImplTest {

    @Test
    public void handle_enoughFruitsForPurchase_Ok() {
        Storage.fruits.put("banana", 20);
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.handle(new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("p"), 20));
        assertEquals(Integer.valueOf(0), Storage.fruits.get("banana"));
    }

    @Test
    public void handle_moreFruitsForPurchase_Ok() {
        Storage.fruits.put("banana", 172);
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.handle(new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("p"), 20));
        assertEquals(Integer.valueOf(152), Storage.fruits.get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void handle_notEnoughFruitsForPurchase_NotOk() {
        Storage.fruits.put("banana", 10);
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.handle(new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("p"), 20));
    }

    @Test(expected = RuntimeException.class)
    public void handle_emptyStorage_NotOk() {
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.handle(new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("p"), 20));
    }

    @Test(expected = NullPointerException.class)
    public void handle_nullInput_Ok() {
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.handle(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
