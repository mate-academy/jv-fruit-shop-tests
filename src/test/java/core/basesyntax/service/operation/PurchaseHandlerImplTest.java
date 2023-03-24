package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerImplTest {
    private static FruitTransaction fruitTransaction;
    private static PurchaseHandlerImpl purchaseHandler;
    private static Map<String, Integer> expectedFruits;

    @Before
    public void setUp() throws Exception {
        fruitTransaction = new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("p"), 20);
        purchaseHandler = new PurchaseHandlerImpl();
        expectedFruits = new HashMap<>();
    }

    @Test
    public void handle_enoughFruitsForPurchase_Ok() {
        Storage.fruits.put("banana", 20);
        expectedFruits.put("banana", 0);
        purchaseHandler.handle(fruitTransaction);
        assertEquals(expectedFruits.size(), Storage.fruits.size());
        assertEquals(expectedFruits, Storage.fruits);
    }

    @Test
    public void handle_moreFruitsForPurchase_Ok() {
        Storage.fruits.put("banana", 172);
        expectedFruits.put("banana", 152);
        purchaseHandler.handle(fruitTransaction);
        assertEquals(expectedFruits.size(), Storage.fruits.size());
        assertEquals(expectedFruits, Storage.fruits);
    }

    @Test(expected = RuntimeException.class)
    public void handle_notEnoughFruitsForPurchase_NotOk() {
        Storage.fruits.put("banana", 10);
        purchaseHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_emptyStorage_NotOk() {
        purchaseHandler.handle(fruitTransaction);
    }

    @Test(expected = NullPointerException.class)
    public void handle_nullInput_Ok() {
        purchaseHandler.handle(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
        expectedFruits.clear();
    }
}
