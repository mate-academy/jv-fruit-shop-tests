package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final OperationHandler balanceOperationHandler = new BalanceOperationHandler(fruitDao);
    private final FruitTransaction fruit = new FruitTransaction();

    @Test
    void handle_validData_ok() {
        fruit.setOperation(FruitTransaction.Operation.BALANCE);
        fruit.setFruitName("lemon");
        fruit.setFruitQuantity(32);
        balanceOperationHandler.handle(fruit);
        String expected = "[lemon=32]";
        String actual = fruitDao.getAll().entrySet().toString();
        assertEquals(expected, actual);
    }

    @Test
    void handle_notValidData_notOk() {
        fruit.setOperation(FruitTransaction.Operation.BALANCE);
        fruit.setFruitName("lemon");
        fruit.setFruitQuantity(-45);
        assertThrows(RuntimeException.class, () -> balanceOperationHandler.handle(fruit));
    }

    @AfterEach
    public void clearStorageAfterEachTest() {
        Storage.FRUIT_MAP.clear();
    }
}
