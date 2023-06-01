package core.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.strategy.operation.PurchaseHandlerImpl;
import org.junit.After;
import org.junit.Test;

public class PurchaseHandlerImplTest {

    @Test
    public void handle_enoughFruitsForPurchase_Ok() {
        Storage.storage.put("kiwi", 243);
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.handle(new FruitTransaction(
                FruitTransaction.Operation.getByCode("p"),
                "kiwi", 243));
        assertEquals(Integer.valueOf(0), Storage.storage.get("kiwi"));
    }

    @Test
    public void handle_moreFruitsForPurchase_Ok() {
        Storage.storage.put("banana", 98);
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.handle(new FruitTransaction(
                FruitTransaction.Operation.getByCode("p"),
                "banana", 60));
        assertEquals(Integer.valueOf(38), Storage.storage.get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void handle_notEnoughFruitsForPurchase_NotOk() {
        Storage.storage.put("ananas", 55);
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.handle(new FruitTransaction(
                FruitTransaction.Operation.getByCode("p"),
                "ananas", 99));
    }

    @Test(expected = RuntimeException.class)
    public void handle_emptyStorage_NotOk() {
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.handle(new FruitTransaction(
                FruitTransaction.Operation.getByCode("p"),
                "apple", 82));
    }

    @Test(expected = NullPointerException.class)
    public void handle_nullInput_Ok() {
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl();
        purchaseHandler.handle(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
