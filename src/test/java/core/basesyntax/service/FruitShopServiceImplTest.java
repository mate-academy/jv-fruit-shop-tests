package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handlers.BalanceOperationHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.PurchaseOperationHandler;
import core.basesyntax.strategy.handlers.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        BalanceOperationHandler balanceOperationHandler = new BalanceOperationHandler();
        SupplyOperationHandler supplyOperationHandler = new SupplyOperationHandler();
        PurchaseOperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, balanceOperationHandler);
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, supplyOperationHandler);
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseOperationHandler);
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, supplyOperationHandler);

        List<String> fruitOperationsList = new ArrayList<>();
        fruitOperationsList.add("type,fruit,quantity");
        fruitOperationsList.add("b,banana,20");
        fruitOperationsList.add("b,kiwi,10");
        fruitOperationsList.add("b,apple,100");
        fruitOperationsList.add("s,banana,100");
        fruitOperationsList.add("s,peach,50");
        fruitOperationsList.add("p,banana,13");
        fruitOperationsList.add("r,apple,10");
        fruitOperationsList.add("p,apple,20");

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        FruitShopService fruitShopService = new FruitShopServiceImpl(operationStrategy);

        TransactionParser transactionParser = new TransactionParserImpl();
        List<FruitTransaction> fruitTransaction =
                transactionParser.parseFruitTransactions(fruitOperationsList);

        fruitShopService.provideOperation(fruitTransaction);

        ReportCreator reportCreator = new ReportCreatorImpl();
        report = reportCreator.createReport(Storage.fruitsQuantity);
    }

    @Test
    public void provideOperation_validData_Ok() {
        assertTrue("peach,50", report.contains("peach,50"));
        assertTrue("kiwi,10", report.contains("kiwi,10"));
        assertTrue("apple,90", report.contains("apple,90"));
        assertTrue("banana,107", report.contains("banana,107"));
    }
}
