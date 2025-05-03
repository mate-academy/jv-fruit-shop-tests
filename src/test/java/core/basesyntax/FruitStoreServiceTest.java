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
    private static final Map<FruitTransaction.Operation, TransactionStrategy> STRATEGY_MAP = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceStrategy(),
            FruitTransaction.Operation.SUPPLY, new SupplyStrategy(),
            FruitTransaction.Operation.PURCHASE, new PurchaseStrategy(),
            FruitTransaction.Operation.RETURN, new ReturnStrategy());

    private static final FruitTransaction FIRST_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100);
    private static final FruitTransaction SECOND_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5);
    private static final FruitTransaction THIRD_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10);

    private static final List<FruitTransaction> CORRECT_TRANSACTIONS =
            new ArrayList<>(
                    Arrays.asList(FIRST_TRANSACTION, SECOND_TRANSACTION, THIRD_TRANSACTION));
    private FruitStoreServiceImpl fruitStoreService
            = new FruitStoreServiceImpl(STRATEGY_MAP);

    @Test
    public void processTransactionsAndGetOk() {
        FruitStorage fruitStorage = new FruitStorage();
        SupplyStrategy supplyStrategy = new SupplyStrategy();
        PurchaseStrategy purchaseStrategy = new PurchaseStrategy();
        ReturnStrategy returnStrategy = new ReturnStrategy();

        supplyStrategy.apply(fruitStorage, FIRST_TRANSACTION);
        purchaseStrategy.apply(fruitStorage, SECOND_TRANSACTION);
        returnStrategy.apply(fruitStorage, THIRD_TRANSACTION);

        Assert.assertEquals(fruitStorage,
                fruitStoreService.processTransactions(CORRECT_TRANSACTIONS));
    }
}
