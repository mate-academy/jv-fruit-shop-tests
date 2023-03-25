package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Test;

public class SupplyHandlerImplTest {

    @Test
    public void handle_storageContainsFruit_Ok() {
        Storage.fruits.put("banana", 132);
        SupplyHandlerImpl supplyHandler = new SupplyHandlerImpl();
        supplyHandler.handle(new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("r"), 20));
        assertEquals(Integer.valueOf(152), Storage.fruits.get("banana"));
    }

    @Test
    public void handle_emptyStorage_Ok() {
        SupplyHandlerImpl supplyHandler = new SupplyHandlerImpl();
        supplyHandler.handle(new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("r"), 20));
        assertEquals(Integer.valueOf(20), Storage.fruits.get("banana"));
    }

    @Test(expected = NullPointerException.class)
    public void handle_nullInput_Ok() {
        SupplyHandlerImpl supplyHandler = new SupplyHandlerImpl();
        supplyHandler.handle(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
