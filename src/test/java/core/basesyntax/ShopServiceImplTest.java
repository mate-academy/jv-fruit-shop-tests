package core.basesyntax.shopservice;

import core.basesyntax.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.operation.handler.BalanceOperation;
import core.basesyntax.operation.handler.PurchaseOperation;
import core.basesyntax.operation.handler.ReturnOperation;
import core.basesyntax.operation.handler.SupplyOperation;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private final String testFruit = "apple";
    private final int testQuantity = 13;
    private ShopService shopService;

    @BeforeEach
    public void setup() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
        Storage.fruits.clear();
    }

    @Test
    public void processTransactionsWithBalance_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        testFruit, testQuantity));
        shopService.process(transactions);
        Assert.assertEquals((Integer) testQuantity,Storage.fruits.get(testFruit));
    }

    @Test
    public void processTransactionWithSupply_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        testFruit, testQuantity));
        shopService.process(transactions);
        Assert.assertEquals((Integer) testQuantity,Storage.fruits.get(testFruit));
    }

    @Test
    public void processTransactionWithReturnOperation_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        testFruit, testQuantity));
        shopService.process(transactions);
        Assert.assertEquals((Integer) testQuantity,Storage.fruits.get(testFruit));
    }

    @Test
    public void processTransactionWithPurchase_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, testFruit, testQuantity),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, testFruit,5));
        shopService.process(transactions);
        Assert.assertEquals((Integer) 8,Storage.fruits.get(testFruit));
    }
}
