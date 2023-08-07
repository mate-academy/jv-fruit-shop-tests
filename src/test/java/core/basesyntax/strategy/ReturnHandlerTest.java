package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandlerService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static final String FRUIT_BANANA = "banana";
    private static final int CURRENT_QUANTITY = 6;
    private static final int EXPECTED_QUANTITY = 10;
    private static final int PURCHASE_QUANTITY = 4;

    private OperationHandlerService operationHandlerService;

    @BeforeEach
    void setUp() {
        operationHandlerService = new ReturnHandler();
        Storage.fruitsStorage.clear();
    }

    @Test
    void returnOperationFruitExist_Ok() {
        Map<String, Integer> expected = Map.of(FRUIT_BANANA, EXPECTED_QUANTITY);
        Storage.fruitsStorage.put(FRUIT_BANANA, CURRENT_QUANTITY);
        operationHandlerService.handle(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_BANANA, PURCHASE_QUANTITY));
        Map<String, Integer> actual = Storage.fruitsStorage;
        assertEquals(expected, actual);
    }

    @Test
    void returnedOperationEmptyStorage_Ok() {
        Map<String, Integer> expected = Map.of(FRUIT_BANANA, EXPECTED_QUANTITY);
        operationHandlerService.handle(
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        FRUIT_BANANA, EXPECTED_QUANTITY));
        Map<String, Integer> actual = Storage.fruitsStorage;
        assertEquals(expected, actual);
    }
}

