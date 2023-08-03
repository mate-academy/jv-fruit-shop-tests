package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationStrategy;
import core.basesyntax.service.operation.OperationStrategyImpl;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitShopServiceImplTest {
    private static final List<FruitTransaction> VALID_TRANSACTIONS = List.of(
            new FruitTransaction("b", "banana", 20),
            new FruitTransaction("s", "apple", 100)
    );
    private static final Map<FruitTransaction.OperationType,
            OperationHandler> OPERATION_HANDLER_MAP =
            Map.of(FruitTransaction.OperationType.BALANCE, new BalanceOperationHandler(),
                    FruitTransaction.OperationType.SUPPLY, new SupplyOperationHandler(),
                    FruitTransaction.OperationType.RETURN, new ReturnOperationHandler(),
                    FruitTransaction.OperationType.PURCHASE, new PurchaseOperationHandler());
    private FruitShopService fruitShopService;

    @BeforeEach
    void setUp() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(OPERATION_HANDLER_MAP);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void processData_validList_ok() {
        Map<String, Integer> expectedTransactions = Map.of("banana", 20, "apple", 100);
        Map<String, Integer> actualTransactions = fruitShopService.processData(VALID_TRANSACTIONS);
        Assertions.assertEquals(expectedTransactions, actualTransactions);
    }
}
