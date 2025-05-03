package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionStrategy;
import core.basesyntax.service.calculation.BalanceCalculator;
import core.basesyntax.service.calculation.PurchaseCalculator;
import core.basesyntax.service.calculation.ReturnCalculator;
import core.basesyntax.service.calculation.SupplyCalculator;
import core.basesyntax.service.calculation.TransactionCalculation;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionStrategyImplTest {
    private static TransactionStrategy transactionStrategy;

    @BeforeClass
    public static void beforeClass() {
        Map<String, TransactionCalculation> strategyMap = new HashMap<>();
        strategyMap.put((FruitTransaction.Operation.BALANCE.getOperations()),
                new BalanceCalculator());
        strategyMap.put(FruitTransaction.Operation.SUPPLY.getOperations(),
                new SupplyCalculator());
        strategyMap.put(FruitTransaction.Operation.PURCHASE.getOperations(),
                new PurchaseCalculator());
        strategyMap.put(FruitTransaction.Operation.RETURN.getOperations(),
                new ReturnCalculator());
        transactionStrategy = new TransactionStrategyImpl(strategyMap);
    }

    @Test
    public void transactionStrategyBalance_Ok() {
        Class<? extends TransactionCalculation> actual = transactionStrategy
                .get(FruitTransaction.Operation.BALANCE.getOperations()).getClass();
        Class<BalanceCalculator> expected = BalanceCalculator.class;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transactionStrategySupply_Ok() {
        Class<? extends TransactionCalculation> actual = transactionStrategy
                .get(FruitTransaction.Operation.SUPPLY.getOperations()).getClass();
        Class<SupplyCalculator> expected = SupplyCalculator.class;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transactionStrategyPurchase_Ok() {
        Class<? extends TransactionCalculation> actual = transactionStrategy
                .get(FruitTransaction.Operation.PURCHASE.getOperations()).getClass();
        Class<PurchaseCalculator> expected = PurchaseCalculator.class;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transactionStrategyReturn_Ok() {
        Class<? extends TransactionCalculation> actual = transactionStrategy
                .get(FruitTransaction.Operation.RETURN.getOperations()).getClass();
        Class<ReturnCalculator> expected = ReturnCalculator.class;
        Assert.assertEquals(expected, actual);
    }
}
