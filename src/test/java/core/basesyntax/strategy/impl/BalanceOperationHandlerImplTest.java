package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerImplTest {
    private static final String EXCEPTION_MESSAGE = "Exception should be thrown here!";
    private BalanceOperationHandlerImpl balanceIOperation;

    @BeforeEach
    void setUp() {
        balanceIOperation = new BalanceOperationHandlerImpl();
    }

    @Test
    void handle_validData_Ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 24);
        Map<String, Integer> expected = Map.of("banana", 24);
        balanceIOperation.handle(fruit);
        Map<String, Integer> actual = Storage.dataStorage;
        assertEquals(expected, actual);
    }

    @Test
    void handle_invalidQuantity_NotOk() {
        FruitTransaction fruit =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -12);
        assertThrows(RuntimeException.class, () ->
                balanceIOperation.handle(fruit), EXCEPTION_MESSAGE);
    }

    @Test
    void handle_balanceIsExist_NotOk() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 56);
        Storage.dataStorage.put("banana", 56);
        assertThrows(RuntimeException.class, () ->
                balanceIOperation.handle(fruit), EXCEPTION_MESSAGE);
    }

    @AfterEach
    void tearDown() {
        Storage.dataStorage.clear();
    }
}
