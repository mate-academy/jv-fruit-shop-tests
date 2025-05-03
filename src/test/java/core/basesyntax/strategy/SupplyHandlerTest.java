package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private ShopService shopService;
    private ReturnHandler returnHandler;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
        returnHandler = new ReturnHandler(shopService);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 100);
    }

    @Test
    void apply_returnTransaction_success() {
        returnHandler.handle(fruitTransaction);
        assertEquals(100, shopService.getQuantity("apple"));
    }

    @Test
    void apply_nonReturnOperation_throwsRuntimeException() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 100);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            returnHandler.handle(fruitTransaction);
        });
        assertEquals("Unsupported operation for ReturnHandler", exception.getMessage());
    }

}
