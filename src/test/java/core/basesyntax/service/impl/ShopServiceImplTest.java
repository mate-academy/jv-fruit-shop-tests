package core.basesyntax.service.impl;

import static core.basesyntax.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperation;
import core.basesyntax.strategy.operation.ReturnOperation;
import core.basesyntax.strategy.operation.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int QUANTITY_20 = 20;
    private static final int QUANTITY_100 = 100;
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlers =
            new HashMap<>();
    private static ShopServiceImpl shopService;

    @BeforeAll
    static void beforeAll() {
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_nullInput_notOk() {
        assertThrows(IllegalArgumentException.class, () -> shopService.process(null));
    }

    @Test
    void process_emptyListInput_ok() {
        assertDoesNotThrow(() -> shopService.process(new ArrayList<>()));
    }

    @Test
    void process_validInput_ok() {
        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(BALANCE);
        transaction1.setFruit(BANANA);
        transaction1.setQuantity(QUANTITY_20);
        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setOperation(BALANCE);
        transaction2.setFruit(APPLE);
        transaction2.setQuantity(QUANTITY_100);
        FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setOperation(SUPPLY);
        transaction3.setFruit(BANANA);
        transaction3.setQuantity(QUANTITY_100);
        List<FruitTransaction> actual = new ArrayList<>(List.of(
                transaction1,
                transaction2,
                transaction3
        ));
        assertDoesNotThrow(() -> shopService.process(actual));
    }
}
