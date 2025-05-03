package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.handler.BalanceOperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.FruitServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceTest {
    private static FruitService fruitService;
    
    @BeforeAll
    static void beforeAll() {
        fruitService = new FruitServiceImpl(Map.of(
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.PURCHASE, new BalanceOperationHandler(),
                Operation.SUPPLY, new BalanceOperationHandler(),
                Operation.RETURN, new BalanceOperationHandler()
        ));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.STORAGE.clear();
    }

    @Test
    void manageTransactions_emptyList_ok() {
        assertDoesNotThrow(() -> fruitService.manageTransactions(Collections.emptyList()));
    }

    @Test
    void manageTransactions_nonEmptyList_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.SUPPLY, "banana", 100)
        );
        assertDoesNotThrow(() -> fruitService.manageTransactions(transactions));
    }
}
