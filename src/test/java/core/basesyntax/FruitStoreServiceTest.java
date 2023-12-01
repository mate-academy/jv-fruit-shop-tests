package core.basesyntax;

import db.FruitStorage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.Test;
import service.FruitStoreServiceImpl;
import strategy.BalanceStrategy;
import strategy.PurchaseStrategy;
import strategy.ReturnStrategy;
import strategy.SupplyStrategy;
import strategy.TransactionStrategy;

public class FruitStoreServiceTest {
    private static final Map<FruitTransaction.Operation, TransactionStrategy> strategyMap = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceStrategy(),
            FruitTransaction.Operation.SUPPLY, new SupplyStrategy(),
            FruitTransaction.Operation.PURCHASE, new PurchaseStrategy(),
            FruitTransaction.Operation.RETURN, new ReturnStrategy());

    private static final FruitStoreServiceImpl fruitStoreService
            = new FruitStoreServiceImpl(strategyMap);

    private static final FruitTransaction transaction1 =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100);
    private static final FruitTransaction transaction2 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5);
    private static final FruitTransaction transaction3 =
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10);

    private static final List<FruitTransaction> correctTransactions =
            new ArrayList<>(Arrays.asList(transaction1, transaction2, transaction3));

    @Test
    public void testCorrectTransactions() {
        FruitStorage fruitStorage = new FruitStorage();
        SupplyStrategy supplyStrategy = new SupplyStrategy();
        PurchaseStrategy purchaseStrategy = new PurchaseStrategy();
        ReturnStrategy returnStrategy = new ReturnStrategy();

        supplyStrategy.apply(fruitStorage, transaction1);
        purchaseStrategy.apply(fruitStorage, transaction2);
        returnStrategy.apply(fruitStorage, transaction3);

        Assert.assertEquals(fruitStorage,
                fruitStoreService.processTransactions(correctTransactions));
    }
}
