package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddingOperationHandlerTest {
    private FruitDao fruitDao;
    private OperationHandler handler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        handler = new AddingOperationHandler(fruitDao);
        }

    @Test
    public void handle_supplyTransaction_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 15);
        handler.handle(fruitTransaction);
        Integer expectedApple = fruitTransaction.getQuantity();
        Integer actualApple = Storage.fruitsQuantity.get("apple");
        assertEquals(expectedApple, actualApple);
    }

    @Test (expected = RuntimeException.class)
    public void handle_emptyTransaction_NotOk() {
        fruitTransaction = new FruitTransaction();
        handler.handle(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.fruitsQuantity.clear();
    }
}