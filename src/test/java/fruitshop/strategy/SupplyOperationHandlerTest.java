package fruitshop.strategy;

import static org.junit.Assert.assertEquals;

import fruitshop.db.Storage;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SupplyOperationHandlerTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private OperationHandler operationHandler = new SupplyOperationHandler();

    @Test
    public void updateAmountOfFruit_existingFruit_ok() {
        Storage.fruitList.put("banana", 10);
        operationHandler.updateAmountOfFruit("banana", 20);
        assertEquals(1, Storage.fruitList.size());
        assertEquals(Integer.valueOf(30), Storage.fruitList.get("banana"));
    }

    @Test
    public void updateAmountOfFruit_notExistingFruit_notOk() {
        exceptionRule.expect(RuntimeException.class);
        operationHandler.updateAmountOfFruit("banana", 20);
    }

    @After
    public void tearDown() {
        Storage.fruitList.clear();
    }
}
