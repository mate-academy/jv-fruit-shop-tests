package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    @BeforeEach
    void clearStorage() {
        Storage.clear();
    }

    @Test
    void process_balanceOperation_updatesStorage() {
        OperationHandler balanceHandler = new BalanceOperation();
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, balanceHandler)
        );
        ShopService shopService = new ShopServiceImpl(strategy);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
        shopService.process(List.of(transaction));
        assertEquals(10, Storage.getQuantity("banana"));
    }
}
