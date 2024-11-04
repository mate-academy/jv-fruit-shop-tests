package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.FruitStorageImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final String APPLE = "apple";
    private static final int ONE_HUNDRED_QUANTITY = 100;
    private FruitStorage fruitStorage;
    private ShopService shopService;
    private BalanceHandler balanceHandler;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorageImpl();
        shopService = new ShopServiceImpl(operationStrategy);
        balanceHandler = new BalanceHandler(fruitStorage);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                APPLE, ONE_HUNDRED_QUANTITY);
    }

    @Test
    void apply_addFruits_success() {
        balanceHandler.apply(fruitTransaction);
        assertEquals(ONE_HUNDRED_QUANTITY, fruitStorage.getQuantity(APPLE));
    }
}
