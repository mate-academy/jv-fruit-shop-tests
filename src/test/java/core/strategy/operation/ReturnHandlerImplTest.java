package core.strategy.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.strategy.operation.ReturnHandlerImpl;
import org.junit.After;
import org.junit.Test;

public class ReturnHandlerImplTest {

    @Test
    public void handle_storageContainsFruit_Ok() {
        Storage.storage.put("banana", 276);
        ReturnHandlerImpl returnHandler = new ReturnHandlerImpl();
        returnHandler.handle(new FruitTransaction(
                FruitTransaction.Operation.getByCode("r"),
                "banana", 100));
        assertEquals(Integer.valueOf(376), Storage.storage.get("banana"));
    }

    @Test
    public void handle_emptyStorage_Ok() {
        ReturnHandlerImpl returnHandler = new ReturnHandlerImpl();
        returnHandler.handle(new FruitTransaction(
                FruitTransaction.Operation.getByCode("r"),
                "apple", 165));
        assertEquals(Integer.valueOf(165), Storage.storage.get("apple"));
    }

    @Test(expected = NullPointerException.class)
    public void handle_nullInput_Ok() {
        ReturnHandlerImpl returnHandler = new ReturnHandlerImpl();
        returnHandler.handle(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
