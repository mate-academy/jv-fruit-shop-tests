package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.impl.BalanceOperation;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import core.basesyntax.service.strategy.impl.PurchaseOperation;
import core.basesyntax.service.strategy.impl.ReturnOperation;
import core.basesyntax.service.strategy.impl.SupplyOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private final Map<FruitTransaction.Operation,
            OperationHandler> operationHandlers = new HashMap<>();
    private final OperationStrategy operationStrategy =
            new OperationStrategyImpl(operationHandlers);
    private final ShopService shopService =
            new ShopServiceImpl(operationStrategy);
    private List<FruitTransaction> transactionList;
    private final Storage storage = new StorageImpl();

    @BeforeEach
    void setUp() {
        storage.clearStorage();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        transactionList = new ArrayList<>(Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",12),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"apple",20),
                new FruitTransaction(FruitTransaction.Operation.RETURN,"apple",5)
        ));
    }

    @Test
    void process() {
        shopService.process(transactionList);
        assertEquals(37, storage.getFruitBalance("apple"));
    }
}
