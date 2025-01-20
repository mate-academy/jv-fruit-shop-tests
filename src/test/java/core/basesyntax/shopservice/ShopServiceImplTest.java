package core.basesyntax.shopservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.operation.PurchaseOperation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static final FruitTransaction TRANSACTION_1 =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
    private static final FruitTransaction TRANSACTION_2 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5);
    private OperationStrategy operationStrategy;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransactions_updatesStorage() {
        List<FruitTransaction> transactions = Arrays.asList(
                TRANSACTION_1,
                TRANSACTION_2
        );
        shopService.process(transactions);

        Map<String, Integer> storage = shopService.getStorage();
        int expectedQuantity = TRANSACTION_1.getQuantity() - TRANSACTION_2.getQuantity();
        assertEquals(expectedQuantity, storage.get("banana"));
    }
}
