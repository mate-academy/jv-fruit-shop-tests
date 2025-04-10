package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.ShopDataBase;
import core.basesyntax.service.operationhandler.BalanceOperation;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.PurchaseOperation;
import core.basesyntax.service.operationhandler.ReturnOperation;
import core.basesyntax.service.operationhandler.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> allHandlers;
    private OperationStrategy strategy;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        allHandlers = new HashMap<>();
        allHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        allHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        allHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        allHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        strategy = new OperationStrategyImpl(allHandlers);
        shopService = new ShopServiceImpl(strategy);
    }

    @Test
    void testProcessTransactions_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction("b", "apple", 100),
                new FruitTransaction("s", "apple", 50),
                new FruitTransaction("p", "apple", 30),
                new FruitTransaction("r", "apple", 10)
        );
        shopService.process(transactions);
        assertEquals(130, ShopDataBase.shopData.get("apple"));
    }

    @Test
    void testProcessEmptyTransactions_Ok() {
        shopService.process(List.of());
        assertTrue(ShopDataBase.shopData.isEmpty());
    }

    @AfterEach
    void tearDown() {
        ShopDataBase.shopData.clear();
    }
}
