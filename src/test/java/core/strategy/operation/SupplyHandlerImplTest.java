package core.strategy.operation;

import static org.junit.Assert.assertEquals;
import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.strategy.operation.SupplyHandlerImpl;
import org.junit.After;
import org.junit.Test;

public class SupplyHandlerImplTest {

    @Test
    public void handle_storageContainsFruit_Ok() {
        Storage.storage.put("apple", 34);
        SupplyHandlerImpl supplyHandler = new SupplyHandlerImpl();
        supplyHandler.handle(new FruitTransaction(
                FruitTransaction.Operation.getByCode("r"),
                "apple", 143));
        assertEquals(Integer.valueOf(177), Storage.storage.get("apple"));
    }

    @Test
    public void handle_emptyStorage_Ok() {
        SupplyHandlerImpl supplyHandler = new SupplyHandlerImpl();
        supplyHandler.handle(new FruitTransaction(
                FruitTransaction.Operation.getByCode("r"),
                "apple", 213));
        assertEquals(Integer.valueOf(213), Storage.storage.get("apple"));
    }

    @Test(expected = NullPointerException.class)
    public void handle_nullInput_Ok() {
        SupplyHandlerImpl supplyHandler = new SupplyHandlerImpl();
        supplyHandler.handle(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
