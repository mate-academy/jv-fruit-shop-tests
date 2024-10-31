package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static final String APPLE = "apple";
    private static final int ONE_HUNDRED_QUANTITY = 100;
    private static final int ONE_HUNDRED_FIFTY_QUANTITY = 150;
    private static final int TWO_HUNDRED_FIFTY_QUANTITY = 250;
    private ShopService shopService;
    private SupplyHandler supplyHandler;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
        supplyHandler = new SupplyHandler(shopService);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                APPLE, ONE_HUNDRED_FIFTY_QUANTITY);
        shopService.addFruits(APPLE, ONE_HUNDRED_QUANTITY);
    }

    @Test
    void apply_supplyTransaction_success() {
        supplyHandler.apply(fruitTransaction);
        int expectedQuantity = TWO_HUNDRED_FIFTY_QUANTITY;
        assertEquals(expectedQuantity, shopService.getQuantity(APPLE));
    }

    @Test
    void apply_nonSupplyOperation_throwsRuntimeException() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                APPLE, ONE_HUNDRED_FIFTY_QUANTITY);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            supplyHandler.apply(fruitTransaction);
        });
        assertEquals("Unsupported operation for SupplyHandler", exception.getMessage());
    }
}
