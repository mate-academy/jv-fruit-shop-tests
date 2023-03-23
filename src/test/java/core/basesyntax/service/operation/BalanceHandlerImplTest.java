package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerImplTest {
    private static FruitTransaction fruitTransaction;
    private static BalanceHandlerImpl balanceHandler;
    private static Map<String, Integer> expectedFruits;

    @Before
    public void setUp() throws Exception {
        fruitTransaction = new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("b"), 20);
        balanceHandler = new BalanceHandlerImpl();
        expectedFruits = new HashMap<>();
    }

    @Test
    public void handle_storageContainsFruit_Ok() {
        Storage.fruits.put("banana", 132);
        expectedFruits.put("banana", 152);
        balanceHandler.handle(fruitTransaction);
        assertEquals(expectedFruits.size(), Storage.fruits.size());
        assertEquals(expectedFruits, Storage.fruits);
    }

    @Test
    public void handle_emptyStorage_Ok() {
        expectedFruits.put("banana", 20);
        balanceHandler.handle(fruitTransaction);
        assertEquals(expectedFruits.size(), Storage.fruits.size());
        assertEquals(expectedFruits, Storage.fruits);
    }

    @Test
    public void handle_nullInput_Ok() {
        assertThrows(RuntimeException.class, () -> balanceHandler.handle(null));
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
        expectedFruits.clear();
    }
}
