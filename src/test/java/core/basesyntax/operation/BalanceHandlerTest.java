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
    private ShopService shopService;
    private BalanceHandler balanceHandler;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
        balanceHandler = new BalanceHandler(shopService);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100);
    }

    @Test
    void apply_shouldAddFruitsToShopService() {
        balanceHandler.apply(fruitTransaction);
        assertEquals(100, shopService.getQuantity("apple"));
    }

    @Test
    void apply_nonBalanceOperation_throwsRuntimeException() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 100);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            balanceHandler.apply(fruitTransaction);
        });
        assertEquals("Unsupported operation for BalanceHandler", exception.getMessage());
    }
}
