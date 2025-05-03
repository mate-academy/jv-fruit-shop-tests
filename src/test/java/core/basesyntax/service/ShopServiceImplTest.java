package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperation;
import core.basesyntax.operation.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static final String APPLE = "apple";
    private static final int SUPPLY_QUANTITY = 50;
    private static final int PURCHASE_QUANTITY = 100;

    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_buyMoreThanAvailable_throwsException() {
        supplyFruit(APPLE, SUPPLY_QUANTITY);

        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(Collections.singletonList(
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                APPLE, PURCHASE_QUANTITY))),
                "Failed in process_buyMoreThanAvailable_throwsException "
                        + "Expected IllegalArgumentException "
                        + "when purchasing more than available stock.");
    }

    private void supplyFruit(String fruit, int quantity) {
        shopService.process(Collections.singletonList(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, fruit, quantity)));
    }
}
