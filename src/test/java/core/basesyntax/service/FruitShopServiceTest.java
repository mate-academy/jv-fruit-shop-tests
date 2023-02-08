package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.operation.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.operation.SupplyOperationHandlerImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceTest {
    private static final Map<FruitTransaction.Operation,
            OperationHandler> STRATEGIES = new HashMap<>();
    private static OperationStrategy operationStrategy;
    private static FruitShopServiceImpl fruitShopService;

    @BeforeClass
    public static void beforeClass() {
        STRATEGIES.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandlerImpl());
        STRATEGIES.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandlerImpl());
        STRATEGIES.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandlerImpl());
        STRATEGIES.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandlerImpl());
        Storage.fruitStorage.clear();
        operationStrategy = new OperationStrategyImpl(STRATEGIES);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void operateTransactions_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 100);
        expected.put("apple", 100);
        FruitTransaction bananaTransaction = new FruitTransaction();
        bananaTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        bananaTransaction.setFruit("banana");
        bananaTransaction.setQuantity(100);
        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        appleTransaction.setFruit("apple");
        appleTransaction.setQuantity(100);
        List<FruitTransaction> transactions = List.of(bananaTransaction,appleTransaction);
        fruitShopService.processTransactions(transactions);
        Assert.assertEquals(Storage.fruitStorage, expected);
    }
}
