package core.basesyntax;

import static core.basesyntax.dbreport.Report.report;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.FruitShopService;
import core.basesyntax.services.FruitShopServiceImpl;
import core.basesyntax.services.TransactionStrategy;
import core.basesyntax.services.TransactionStrategyImpl;
import core.basesyntax.services.transaction.BalanceOperationHandler;
import core.basesyntax.services.transaction.OperationHandler;
import core.basesyntax.services.transaction.PurchaseOperationHandler;
import core.basesyntax.services.transaction.ReturnOperationHandler;
import core.basesyntax.services.transaction.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class FruitShopServiceTest {
    @Test
    public void doTransaction_Ok() {
        Map<FruitTransaction.Operation, OperationHandler> transactionMap = new HashMap<>();
        transactionMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        transactionMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        transactionMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        transactionMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(transactionMap);
        FruitShopService fruitShopService = new FruitShopServiceImpl(transactionStrategy);

        FruitTransaction transactionBalance = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana", 100);
        FruitTransaction transactionPurchase = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana", 10);
        FruitTransaction transactionReturn = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "banana", 5);
        FruitTransaction transactionSupply = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana", 10);

        fruitShopService.doTransaction(transactionBalance);
        fruitShopService.doTransaction(transactionPurchase);
        fruitShopService.doTransaction(transactionReturn);
        fruitShopService.doTransaction(transactionSupply);
        assertEquals(report.get("banana").toString(), "105");
    }
}

