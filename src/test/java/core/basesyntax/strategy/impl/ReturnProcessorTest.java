package core.basesyntax.strategy.impl;

import static core.basesyntax.model.Product.APPLE;
import static core.basesyntax.model.Product.BANANA;
import static core.basesyntax.strategy.FruitTransaction.Operation.RETURN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.InvalidTransaction;
import core.basesyntax.strategy.FruitTransaction;
import core.basesyntax.strategy.OperationProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ReturnProcessor Test")
class ReturnProcessorTest {
    private final OperationProcessor returnProcessor = new ReturnProcessor(new ProductDaoImpl());

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @DisplayName("Check return operation with valid value (Apple) and empty Storage")
    @Test
    void operate_checkReturnAppleEmptyStorage_ok() {
        returnProcessor.operate(new FruitTransaction(RETURN, APPLE, 20));
        assertEquals(Storage.storage.get(APPLE), 20);
    }

    @DisplayName("Check return operation with valid value (Apple) and non empty Storage")
    @Test
    void operate_checkReturnApple_ok() {
        Storage.storage.put(APPLE, 10);
        returnProcessor.operate(new FruitTransaction(RETURN, APPLE, 20));
        assertEquals(Storage.storage.get(APPLE), 30);
    }

    @DisplayName("Check return operation with valid value (Apple) and empty Storage")
    @Test
    void operate_checkReturnBananaEmptyStorage_ok() {
        returnProcessor.operate(new FruitTransaction(RETURN, BANANA, 20));
        assertEquals(Storage.storage.get(BANANA), 20);
    }

    @DisplayName("Check return operation with valid value (Apple) and non empty Storage")
    @Test
    void operate_checkReturnBanana_ok() {
        Storage.storage.put(BANANA, 10);
        returnProcessor.operate(new FruitTransaction(RETURN, BANANA, 20));
        assertEquals(Storage.storage.get(BANANA), 30);
    }

    @DisplayName("Check return operation with negative value")
    @Test
    void operate_checkReturnNegative_notOk() {
        assertThrows(InvalidTransaction.class, () -> returnProcessor.operate(
                new FruitTransaction(RETURN, BANANA, -20)));
    }
}
