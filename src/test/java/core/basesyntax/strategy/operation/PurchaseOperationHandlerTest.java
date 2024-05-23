package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.Constants;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final String BANANA = Constants.BANANA;
    private final FruitTransaction validTransaction =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 10);
    private final FruitTransaction invalidTransaction1 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 11);
    private final FruitTransaction invalidTransaction2 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 20);
    private final Map<String, Integer> expectedBalanceStatistic = Map.of(BANANA, 1);
    private OperationHandler operation;

    @BeforeEach
    void setUp() {
        operation = new PurchaseOperationHandler();
        Storage.balanceStatistic.put(BANANA, 11);
    }

    @Test
    void execute_Ok() {
        operation.execute(validTransaction);
        Map<String, Integer> actualBalanceStatistic = Storage.balanceStatistic;
        assertEquals(expectedBalanceStatistic, actualBalanceStatistic);
    }

    @Test
    void execute_negativeBalance_throwFruitShopException() {
        assertThrows(FruitShopException.class,
                () -> operation.execute(invalidTransaction1));
        assertThrows(FruitShopException.class,
                () -> operation.execute(invalidTransaction2));
    }

    @AfterEach
    void tearDown() {
        Storage.balanceStatistic.clear();
    }
}
