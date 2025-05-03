package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private ShopService shopService;
    private BalanceHandler balance;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
        balance = new BalanceHandler(shopService);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100);
    }

    @Test
    void apply_shouldAddFruitsToShopService() {
        balance.handle(fruitTransaction);
        assertEquals(100, shopService.getQuantity("apple"));
    }

    @Test
    void apply_nonBalanceOperation_throwsRuntimeException() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 100);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            balance.handle(fruitTransaction);
        });
        assertEquals("Unsupported operation for BalanceHandler", exception.getMessage());
    }
}
