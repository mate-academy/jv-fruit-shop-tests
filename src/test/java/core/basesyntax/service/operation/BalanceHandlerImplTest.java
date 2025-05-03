package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Test;

public class BalanceHandlerImplTest {

    @Test
    public void handle_storageContainsFruit_Ok() {
        Storage.fruits.put("banana", 132);
        BalanceHandlerImpl balanceHandler = new BalanceHandlerImpl();
        balanceHandler.handle(new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("b"), 20));
        assertEquals(Integer.valueOf(152), Storage.fruits.get("banana"));
    }

    @Test
    public void handle_emptyStorage_Ok() {
        BalanceHandlerImpl balanceHandler = new BalanceHandlerImpl();
        balanceHandler.handle(new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("b"), 20));
        assertEquals(Integer.valueOf(20), Storage.fruits.get("banana"));
    }

    @Test(expected = NullPointerException.class)
    public void handle_nullInput_Ok() {
        BalanceHandlerImpl balanceHandler = new BalanceHandlerImpl();
        balanceHandler.handle(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
