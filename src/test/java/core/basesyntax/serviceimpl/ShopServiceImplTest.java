package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final int expected = 25;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private OperationStrategy operationStrategy;
    private ShopServiceImpl shopService;
    private List<FruitTransaction> transactions;

    @BeforeEach
    void setUp() {
        operationHandlers = Map
                .of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                        FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                        FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        transactions = List
                .of(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                "banana", 50),
                        new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                "apple", 50),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                                "banana", 30),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                "apple", 25));
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void process_correctlyUpdatesStorage_ok() {
        shopService.process(transactions);
        Integer actual = Storage.fruitStorage.get("apple");
        assertEquals(expected, actual);
    }
}
