package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String APPLE = "apple";
    private static final int ONE_HUNDRED_QUANTITY = 100;
    private static final int ONE_HUNDRED_FIFTY_QUANTITY = 150;
    private static final int FIFTY_QUANTITY = 50;
    private static final int TWO_HUNDRED_QUANTITY = 200;
    private ShopService shopService;
    private PurchaseHandler purchaseHandler;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
        purchaseHandler = new PurchaseHandler(shopService);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                APPLE, ONE_HUNDRED_QUANTITY);
        shopService.addFruits(APPLE, ONE_HUNDRED_FIFTY_QUANTITY);
    }

    @Test
    void apply_purchaseTransaction_success() {
        purchaseHandler.apply(fruitTransaction);
        int expectedQuantity = FIFTY_QUANTITY;
        int actualQuantity = shopService.getQuantity(APPLE);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void apply_purchaseTransaction_unsuccessful() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                APPLE, TWO_HUNDRED_QUANTITY);
        shopService.addFruits(APPLE, ONE_HUNDRED_QUANTITY);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchaseHandler.apply(fruitTransaction);
        });
        assertEquals("Not enough apple in stock for purchase", exception.getMessage());
    }

    @Test
    void apply_nonPurchaseOperation_throwsRuntimeException() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                APPLE, ONE_HUNDRED_FIFTY_QUANTITY);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchaseHandler.apply(fruitTransaction);
        });
        assertEquals("Unsupported operation for PurchaseHandler", exception.getMessage());
    }
}
