package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final OperationHandler returnOperationHandler = new ReturnOperationHandler(fruitDao);
    private final FruitTransaction fruit = new FruitTransaction();

    @BeforeEach
    public void init() {
        Storage.FRUIT_MAP.put("lemon", 50);
    }

    @Test
    void handle_validData_ok() {
        fruit.setOperation(FruitTransaction.Operation.RETURN);
        fruit.setFruitName("lemon");
        fruit.setFruitQuantity(5);
        returnOperationHandler.handle(fruit);
        String expected = "[lemon=55]";
        String actual = fruitDao.getAll().entrySet().toString();
        assertEquals(expected, actual);
    }

    @Test
    void handle_notValidData_notOk() {
        fruit.setOperation(FruitTransaction.Operation.RETURN);
        fruit.setFruitName("lemon");
        fruit.setFruitQuantity(-68);
        assertThrows(RuntimeException.class, () -> returnOperationHandler.handle(fruit));
    }

    @AfterEach
    public void clearStorageAfterEachTest() {
        Storage.FRUIT_MAP.clear();
    }
}
