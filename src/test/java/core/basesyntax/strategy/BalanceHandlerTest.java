package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandlerService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final String FRUIT_BANANA = "banana";
    private static final int QUANTITY = 12;
    private OperationHandlerService operationHandlerService;

    @BeforeEach
    void setUp() {
        operationHandlerService = new BalanceHandler();
        Storage.fruitsStorage.clear();
    }

    @Test
    void balanceOperationValidData_ok() {
        Map<String, Integer> expected = Map.of(FRUIT_BANANA, QUANTITY);
        operationHandlerService.handle(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT_BANANA, QUANTITY));
        Map<String, Integer> actual = Storage.fruitsStorage;
        assertEquals(expected, actual);
    }
}
