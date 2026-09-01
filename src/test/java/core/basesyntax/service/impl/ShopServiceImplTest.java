package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();

        Map<FruitTransaction.Operation, OperationHandler> handlers =
                new HashMap<>();

        handlers.put(
                FruitTransaction.Operation.BALANCE,
                new BalanceOperation()
        );
        handlers.put(
                FruitTransaction.Operation.SUPPLY,
                new SupplyOperation()
        );
        handlers.put(
                FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation()
        );
        handlers.put(
                FruitTransaction.Operation.RETURN,
                new ReturnOperation()
        );

        OperationStrategy strategy =
                new OperationStrategyImpl(handlers);

        shopService = new ShopServiceImpl(strategy);
    }

    @Test
    void process_validTransactions_updatesStorage() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE,
                        "banana",
                        20
                ),
                new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY,
                        "banana",
                        100
                ),
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE,
                        "banana",
                        13
                ),
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN,
                        "banana",
                        5
                )
        );

        shopService.process(transactions);

        assertEquals(112, Storage.getStorage().get("banana"));
    }
}
