package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final FruitTransaction VALID_INPUT =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);
    private static OperationHandler balanceOperationHandler;

    @BeforeAll
    static void beforeAll() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @BeforeEach
    void setUp() {
        FruitStorage.STORAGE_MAP.clear();
    }

    @Test
    void operate_validCase_isOk() {
        balanceOperationHandler.operate(VALID_INPUT);
        assertEquals(20, FruitStorage.STORAGE_MAP.get(VALID_INPUT.getFruit()));
    }

    @AfterAll
    static void afterAll() {
        FruitStorage.STORAGE_MAP.clear();
    }
}
