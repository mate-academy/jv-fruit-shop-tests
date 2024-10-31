package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseHandler;
import core.basesyntax.operation.ReturnHandler;
import core.basesyntax.operation.SupplyHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.transaction.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int FIFTY_QUANTITY = 50;
    private static final int TWENTY_QUANTITY = 20;
    private static final int THIRTY_QUANTITY = 30;
    private static final int EIGHTY_QUANTITY = 80;
    private ShopService shopService;

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);

        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(shopService));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(shopService));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(shopService));
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler(shopService));
    }

    @Test
    public void shouldAddFruitsWith_correctQuantity() {
        shopService.addFruits(APPLE, FIFTY_QUANTITY);
        assertEquals(FIFTY_QUANTITY, shopService.getQuantity(APPLE),
                "The quantity of apples should be 50.");
    }

    @Test
    public void shouldIncreaseQuantityWhen_fruitsAreSupplied() {
        shopService.addFruits(APPLE, FIFTY_QUANTITY);
        shopService.supplyFruits(APPLE, THIRTY_QUANTITY);
        assertEquals(EIGHTY_QUANTITY, shopService.getQuantity(APPLE),
                "The quantity of apples should be 70 after supply.");
    }

    @Test
    public void shouldReduceQuantityWhen_fruitsArePurchased() {
        shopService.addFruits(APPLE, FIFTY_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                APPLE, THIRTY_QUANTITY);
        shopService.process(List.of(transaction));
        assertEquals(TWENTY_QUANTITY, shopService.getQuantity(APPLE),
                "The quantity of apples should be 20 after purchase.");
    }

    @Test
    public void shouldThrowExceptionWhen_purchasingMoreThanAvailable() {
        shopService.addFruits(APPLE, THIRTY_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                APPLE, FIFTY_QUANTITY);

        assertThrows(RuntimeException.class, () ->
                        shopService.process(List.of(transaction)),
                "Should throw exception when trying to purchase more than available.");
    }

    @Test
    public void shouldReturn_correctInventory() {
        shopService.addFruits(APPLE, FIFTY_QUANTITY);
        shopService.addFruits(BANANA, THIRTY_QUANTITY);

        Map<String, Integer> expectedInventory = new HashMap<>();
        expectedInventory.put(APPLE, FIFTY_QUANTITY);
        expectedInventory.put(BANANA, THIRTY_QUANTITY);

        assertEquals(expectedInventory, shopService.getFruits(),
                "The inventory should match the expected values.");
    }
}
