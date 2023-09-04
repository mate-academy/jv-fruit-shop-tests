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

class SupplyOperationHandlerTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final OperationHandler supplyOperationHandler = new SupplyOperationHandler(fruitDao);
    private final FruitTransaction fruit = new FruitTransaction();

    @BeforeEach
    public void init() {
        Storage.FRUIT_MAP.put("lemon", 50);
    }

    @Test
    void handle_validData_ok() {
        fruit.setOperation(FruitTransaction.Operation.SUPPLY);
        fruit.setFruitName("lemon");
        fruit.setFruitQuantity(45);
        supplyOperationHandler.handle(fruit);
        String expected = "[lemon=95]";
        String actual = fruitDao.getAll().entrySet().toString();
        assertEquals(expected, actual);
    }

    @Test
    void handle_notValidData_notOk() {
        fruit.setOperation(FruitTransaction.Operation.SUPPLY);
        fruit.setFruitName("lemon");
        fruit.setFruitQuantity(-80);
        assertThrows(RuntimeException.class, () -> supplyOperationHandler.handle(fruit));
    }

    @AfterEach
    public void clearStorageAfterEachTest() {
        Storage.FRUIT_MAP.clear();
    }
}
