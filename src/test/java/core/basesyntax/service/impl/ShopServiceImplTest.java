package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationImpl;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationImpl;
import core.basesyntax.strategy.impl.ReturnOperationImpl;
import core.basesyntax.strategy.impl.SupplyOperationImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int BALANCE_QUANTITY = 1;
    private static final int SUPPLY_QUANTITY = 10;
    private static final int PURCHASE_QUANTITY = 5;
    private static final int RETURN_QUANTITY = 2;
    private static final int EXPECTED_BANANA_QUANTITY = 8;
    private static final int EXPECTED_APPLE_QUANTITY = 1;

    @Test
    void process_validData_ok() {
        Map<Operation, OperationHandler> operationHandlers = Map.of(
                Operation.BALANCE, new BalanceOperationImpl(),
                Operation.PURCHASE, new PurchaseOperationImpl(),
                Operation.RETURN, new ReturnOperationImpl(),
                Operation.SUPPLY, new SupplyOperationImpl()
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        ShopService shopService = new ShopServiceImpl(operationStrategy);

        List<FruitTransaction> inputData = List.of(
                new FruitTransaction(
                        Operation.BALANCE,
                        BANANA,
                        BALANCE_QUANTITY),
                new FruitTransaction(
                        Operation.BALANCE,
                        APPLE,
                        BALANCE_QUANTITY),
                new FruitTransaction(
                        Operation.SUPPLY,
                        BANANA,
                        SUPPLY_QUANTITY),
                new FruitTransaction(
                        Operation.PURCHASE,
                        BANANA,
                        PURCHASE_QUANTITY),
                new FruitTransaction(
                        Operation.RETURN,
                        BANANA,
                        RETURN_QUANTITY));
        shopService.process(inputData);

        assertEquals(Storage.getQuantity(BANANA), EXPECTED_BANANA_QUANTITY);
        assertEquals(Storage.getQuantity(APPLE), EXPECTED_APPLE_QUANTITY);
    }
}
