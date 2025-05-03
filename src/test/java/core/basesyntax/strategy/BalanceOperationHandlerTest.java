package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;
    private static FruitTransaction bananaBalanceOperation;
    private static FruitTransaction bananaSecondBalanceOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperationHandler = new BalanceOperationHandler();
        bananaBalanceOperation = new FruitTransaction();
        bananaSecondBalanceOperation = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        bananaBalanceOperation.setOperation(FruitTransaction.Operation.BALANCE);
        bananaBalanceOperation.setFruit("banana");
        bananaBalanceOperation.setAmount(100);

        bananaSecondBalanceOperation.setOperation(FruitTransaction.Operation.getCode("b"));
        bananaSecondBalanceOperation.setFruit("banana");
        bananaSecondBalanceOperation.setAmount(150);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitData.clear();
    }

    @Test
    void execute_firstBalanceOperation_Ok() {
        balanceOperationHandler.process(bananaBalanceOperation);
        assertTrue(Storage.fruitData
                .containsKey(bananaBalanceOperation.getFruit()));
        assertEquals(100, Storage
                .fruitData.getOrDefault(bananaBalanceOperation.getFruit(),
                        bananaBalanceOperation.getAmount()));
    }

    @Test
    void execute_sameBalanceOperation_Ok() {
        balanceOperationHandler.process(bananaBalanceOperation);
        balanceOperationHandler.process(bananaSecondBalanceOperation);
        assertTrue(Storage.fruitData
                .containsKey(bananaBalanceOperation.getFruit()));
        assertTrue(Storage.fruitData
                .containsKey(bananaSecondBalanceOperation.getFruit()));
        assertEquals(250,Storage
                .fruitData.getOrDefault(bananaBalanceOperation.getFruit(),
                        bananaBalanceOperation.getAmount()));
    }
}
