package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CalculatorServiceImpl;
import core.basesyntax.service.impl.ReportGeneratorServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.StrategyStorageImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int FIFTY = 50;
    private static final int TWENTY = 20;
    private static final int TEN = 10;
    private static final int FIVE = 5;
    private static final String EXPECTED_REPORT = "fruit quantity" + System.lineSeparator()
            + "banana,50" + System.lineSeparator()
            + "apple,40";

    private ReportGeneratorService reportGeneratorService;

    @Before
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        StrategyStorageImpl strategyStorage = new StrategyStorageImpl();
        strategyStorage.setHandlers(handlers);
        CalculatorService calculatorService = new CalculatorServiceImpl(strategyStorage);
        reportGeneratorService = new ReportGeneratorServiceImpl();
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, FIFTY);
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, TWENTY);
        FruitTransaction purchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, TEN);
        FruitTransaction returnTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, FIVE);
        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction,
                supplyTransaction,
                purchaseTransaction,
                returnTransaction);
        calculatorService.calculate(transactions);
    }

    @Test
    public void generateReport_validStorage_Ok() {
        String actualReport = reportGeneratorService.generateReport();
        assertEquals(EXPECTED_REPORT, actualReport);
    }

    @Test(expected = RuntimeException.class)
    public void generateReport_emptyStorage_notOk() {
        Storage.storage.clear();
        reportGeneratorService.generateReport();
    }

    @Test
    public void setUp_reportGeneratorServiceNotNull() {
        Assert.assertNotNull("ReportGeneratorService should not be null", reportGeneratorService);
    }
}
