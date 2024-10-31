package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static final String APPLE = "apple";
    private static final int INITIAL_QUANTITY = 100;
    private ShopService shopService;
    private ReturnHandler returnHandler;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
        returnHandler = new ReturnHandler(shopService);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                APPLE, INITIAL_QUANTITY);
    }

    @Test
    void apply_returnTransaction_success() {
        returnHandler.apply(fruitTransaction);
        assertEquals(INITIAL_QUANTITY, shopService.getQuantity(APPLE));
    }

    @Test
    void apply_nonReturnOperation_throwsRuntimeException() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                APPLE, INITIAL_QUANTITY);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            returnHandler.apply(fruitTransaction);
        });
        assertEquals("Unsupported operation for ReturnHandler", exception.getMessage());
    }
}
