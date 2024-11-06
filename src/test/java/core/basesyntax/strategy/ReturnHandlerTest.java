package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private ShopService shopService;
    private SupplyHandler supplyHandler;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
        supplyHandler = new SupplyHandler(shopService);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 100);
        shopService.addFruits("apple", 150);
    }

    @Test
    void apply_supplyTransaction_success() {
        supplyHandler.handle(fruitTransaction);
        int expectedQuantity = 250;
        assertEquals(expectedQuantity, shopService.getQuantity("apple"));
    }

    @Test
    void apply_nonSupplyOperation_throwsRuntimeException() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            supplyHandler.handle(fruitTransaction);
        });
        assertEquals("Unsupported operation for SupplyHandler", exception.getMessage());
    }

}
