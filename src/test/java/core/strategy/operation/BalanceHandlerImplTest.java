package core.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceHandlerImpl;
import org.junit.After;
import org.junit.Test;

public class BalanceHandlerImplTest {
    private static final BalanceHandlerImpl balanceHandler = new BalanceHandlerImpl();

    @Test
    public void handle_storageContainsFruit_Ok() {
        Storage.storage.put("apple", 66);
        balanceHandler.handle(new FruitTransaction(
                FruitTransaction.Operation.getByCode("b"),
                "apple", 15));
        assertEquals(Integer.valueOf(81), Storage.storage.get("apple"));
    }

    @Test
    public void handle_emptyStorage_Ok() {
        balanceHandler.handle(new FruitTransaction(
                FruitTransaction.Operation.getByCode("b"),
                "mango", 25));
        assertEquals(Integer.valueOf(25), Storage.storage.get("mango"));
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
