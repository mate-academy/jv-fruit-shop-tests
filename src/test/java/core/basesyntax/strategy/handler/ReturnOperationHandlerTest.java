package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static FruitTransaction validFruitTransaction;
    private static ReturnOperationHandler returnOperationHandler;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        validFruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 7);
        fruitDao = new FruitDaoImpl();
        returnOperationHandler = new ReturnOperationHandler(fruitDao);
    }

    @Test
    void process_ValidData_ok() {
        Map<String, Integer> expected = Map.of("apple", 22);
        Storage.fruitStorage.put("apple", 15);
        returnOperationHandler.process(validFruitTransaction);
        Map<String, Integer> actual = Storage.fruitStorage;
        assertEquals(expected, actual);
    }

    @Test
    void process_NullData_throwsException() {
        assertThrows(NullPointerException.class, () ->
                returnOperationHandler.process(null));
    }

}
