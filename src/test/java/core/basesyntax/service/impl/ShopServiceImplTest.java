package core.basesyntax.service.impl;

import static core.basesyntax.constants.Constants.APPLE;
import static core.basesyntax.constants.Constants.BALANCE_QUANTITY;
import static core.basesyntax.constants.Constants.BANANA;
import static core.basesyntax.constants.Constants.EXPECTED_APPLE_QUANTITY;
import static core.basesyntax.constants.Constants.EXPECTED_BANANA_QUANTITY;
import static core.basesyntax.constants.Constants.PURCHASE_QUANTITY;
import static core.basesyntax.constants.Constants.RETURN_QUANTITY;
import static core.basesyntax.constants.Constants.SUPPLY_QUANTITY;
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
