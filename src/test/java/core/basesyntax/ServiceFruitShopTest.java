package core.basesyntax;

import core.basesyntax.db.FruitTransactionDb;
import core.basesyntax.db.FruitTransactionDbImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ServiceFruitShop;
import core.basesyntax.service.ServiceFruitShopImpl;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.impl.BalanceStrategy;
import core.basesyntax.strategy.impl.PurchaseStrategy;
import core.basesyntax.strategy.impl.ReturnStrategy;
import core.basesyntax.strategy.impl.SupplyStrategy;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class ServiceFruitShopTest {
    private static final Map<FruitTransaction.Operation, Strategy> OPERATION_STRATEGY_MAP = Map
            .of(FruitTransaction.Operation.BALANCE, new BalanceStrategy(),
            FruitTransaction.Operation.PURCHASE, new PurchaseStrategy(),
            FruitTransaction.Operation.RETURN, new ReturnStrategy(),
            FruitTransaction.Operation.SUPPLY, new SupplyStrategy());
    private static final FruitTransaction BALANCE_TXN = new FruitTransaction(
            FruitTransaction.Operation.BALANCE, "banana", 100);
    private static final FruitTransaction PURCHASE_TXN = new FruitTransaction(
            FruitTransaction.Operation.PURCHASE, "banana", 20);
    private static final FruitTransaction RETURN_TXN = new FruitTransaction(
            FruitTransaction.Operation.RETURN, "banana", 45);
    private static final FruitTransaction SUPPLY_TXN = new FruitTransaction(
            FruitTransaction.Operation.SUPPLY, "apple", 180);
    private static final List<FruitTransaction> FRUIT_TRANSACTION = List
            .of(BALANCE_TXN, PURCHASE_TXN, RETURN_TXN, SUPPLY_TXN);
    private ServiceFruitShop serviceFruitShop = new ServiceFruitShopImpl(OPERATION_STRATEGY_MAP);

    @Test
    public void processTransactionFruitShop_correctTransaction_Ok() {
        FruitTransactionDb fruitStore = new FruitTransactionDbImpl();

        BalanceStrategy balanceStrategy = new BalanceStrategy();
        balanceStrategy.calculation(BALANCE_TXN, fruitStore);

        PurchaseStrategy purchaseStrategy = new PurchaseStrategy();
        purchaseStrategy.calculation(PURCHASE_TXN, fruitStore);

        ReturnStrategy returnStrategy = new ReturnStrategy();
        returnStrategy.calculation(RETURN_TXN, fruitStore);

        SupplyStrategy supplyStrategy = new SupplyStrategy();
        supplyStrategy.calculation(SUPPLY_TXN, fruitStore);

        Assert.assertEquals(fruitStore, serviceFruitShop.processTransaction(FRUIT_TRANSACTION));
    }

}
