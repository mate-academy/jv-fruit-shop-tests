package core.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.strategy.operation.ReturnHandlerImpl;
import org.junit.After;
import org.junit.Test;

public class ReturnHandlerImplTest {
    private static final ReturnHandlerImpl returnHandler = new ReturnHandlerImpl();

    @Test
    public void handle_storageContainsFruit_Ok() {
        Storage.storage.put("banana", 276);
        returnHandler.handle(new FruitTransaction(
                FruitTransaction.Operation.getByCode("r"),
                "banana", 100));
        assertEquals(Integer.valueOf(376), Storage.storage.get("banana"));
    }

    @Test
    public void handle_emptyStorage_Ok() {
        returnHandler.handle(new FruitTransaction(
                FruitTransaction.Operation.getByCode("r"),
                "apple", 165));
        assertEquals(Integer.valueOf(165), Storage.storage.get("apple"));
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
