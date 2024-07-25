package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int INITIAL_APPLE_BALANCE = 100;
    private static final int APPLE_PURCHASE_QUANTITY = 20;
    private static final int EXPECTED_APPLE_QUANTITY
            = INITIAL_APPLE_BALANCE - APPLE_PURCHASE_QUANTITY;
    private static final int INITIAL_BANANA_SUPPLY = 50;
    private static final int BANANA_RETURN_QUANTITY = 10;
    private static final int EXPECTED_BANANA_QUANTITY
            = INITIAL_BANANA_SUPPLY + BANANA_RETURN_QUANTITY;

    private Storage storage;
    private ShopServiceImpl shopService;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation()
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy, storage);
    }

    @Test
    public void testProcessTransactions() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE,
                        INITIAL_APPLE_BALANCE),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA,
                        INITIAL_BANANA_SUPPLY),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE,
                        APPLE_PURCHASE_QUANTITY),
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA,
                        BANANA_RETURN_QUANTITY)
        );

        shopService.process(transactions);

        assertEquals(EXPECTED_APPLE_QUANTITY, storage.getFruitQuantities().get(APPLE));
        assertEquals(EXPECTED_BANANA_QUANTITY, storage.getFruitQuantities().get(BANANA));
    }
}
