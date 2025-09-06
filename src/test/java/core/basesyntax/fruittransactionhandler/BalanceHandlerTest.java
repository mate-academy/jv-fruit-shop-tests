package core.basesyntax.fruittransactionhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.TypeOfTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final Storage storage = new Storage();
    private static final FruitTransaction fruitTransaction = new FruitTransaction();
    private static final OperationHandler balancedHandler = new BalanceHandler(storage);
    private static final Map<String,Integer> expectedMap = new HashMap<>();

    @AfterEach
    public void afterEach() {
        storage.getDataStorageMapOrDefault("randomFruit").clear();
    }

    @Test
    void handle_nullFruitTransaction_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> balancedHandler.handle(null));
    }

    @Test
    void handle_addBalanceValueToResultMap_ok() {
        fruitTransaction.setTypeOfTransaction(TypeOfTransaction.B);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
        balancedHandler.handle(fruitTransaction);
        expectedMap.put("banana", 10);
        Assertions.assertEquals(expectedMap, storage.getDataStorageMap());
    }
}
