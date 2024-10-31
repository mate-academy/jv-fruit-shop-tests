package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final String APPLE = "apple";
    private static final int ONE_HUNDRED_QUANTITY = 100;
    private ShopService shopService;
    private BalanceHandler balanceHandler;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
        balanceHandler = new BalanceHandler(shopService);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                APPLE, ONE_HUNDRED_QUANTITY);
    }

    @Test
    void apply_shouldAddFruitsToShopService() {
        balanceHandler.apply(fruitTransaction);
        assertEquals(ONE_HUNDRED_QUANTITY, shopService.getQuantity(APPLE));
    }

    @Test
    void apply_nonBalanceOperation_throwsRuntimeException() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                APPLE, ONE_HUNDRED_QUANTITY);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            balanceHandler.apply(fruitTransaction);
        });
        assertEquals("Unsupported operation for BalanceHandler", exception.getMessage());
    }
}
