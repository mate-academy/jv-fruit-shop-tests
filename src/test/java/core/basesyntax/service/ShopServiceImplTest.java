package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseHandler;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.FruitStorageImpl;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.transaction.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String APPLE = "apple";
    private static final int FIFTY_QUANTITY = 50;
    private static final int TWENTY_QUANTITY = 20;
    private static final int THIRTY_QUANTITY = 30;
    private ShopService shopService;
    private FruitStorage fruitStorage;

    @BeforeEach
    public void setUp() {
        fruitStorage = new FruitStorageImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);

        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(fruitStorage));
    }

    @Test
    public void process_reduceQuantity_success() {
        fruitStorage.addFruits(APPLE, FIFTY_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                APPLE, THIRTY_QUANTITY);
        shopService.process(List.of(transaction));
        assertEquals(TWENTY_QUANTITY, fruitStorage.getQuantity(APPLE),
                "The quantity of apples should be 20 after purchase.");
    }

    @Test
    public void process_purchasingMoreThanAvailable_shouldThrowException() {
        fruitStorage.addFruits(APPLE, THIRTY_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                APPLE, FIFTY_QUANTITY);

        assertThrows(RuntimeException.class, () ->
                        shopService.process(List.of(transaction)),
                "Should throw exception when trying to purchase more than available.");
    }
}
