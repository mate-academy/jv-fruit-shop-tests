package core.basesyntax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseHandler;
import core.basesyntax.operation.ReturnHandler;
import core.basesyntax.operation.SupplyHandler;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.transaction.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int FIFTY_QUANTITY = 50;
    private static final int THIRTY_QUANTITY = 30;
    private static final int EIGHTY_QUANTITY = 80;
    private ShopService shopService;
    private FruitStorage fruitStorage;

    @BeforeEach
    public void setUp() {
        fruitStorage = new FruitStorageImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);

        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(fruitStorage));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(fruitStorage));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(fruitStorage));
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler(fruitStorage));
    }

    @Test
    public void getQuantity_correctQuantity_success() {
        fruitStorage.addFruits(APPLE, FIFTY_QUANTITY);
        assertEquals(FIFTY_QUANTITY, fruitStorage.getQuantity(APPLE),
                "The quantity of apples should be 50.");
    }

    @Test
    public void supplyFruits_increaseQuantity_success() {
        fruitStorage.addFruits(APPLE, FIFTY_QUANTITY);
        fruitStorage.supplyFruits(APPLE, THIRTY_QUANTITY);
        assertEquals(EIGHTY_QUANTITY, fruitStorage.getQuantity(APPLE),
                "The quantity of apples should be 70 after supply.");
    }

    @Test
    public void getFruits_returnCorrectInventory_success() {
        fruitStorage.addFruits(APPLE, FIFTY_QUANTITY);
        fruitStorage.addFruits(BANANA, THIRTY_QUANTITY);

        Map<String, Integer> expectedInventory = new HashMap<>();
        expectedInventory.put(APPLE, FIFTY_QUANTITY);
        expectedInventory.put(BANANA, THIRTY_QUANTITY);

        assertEquals(expectedInventory, fruitStorage.getFruits(),
                "The inventory should match the expected values.");
    }
}
