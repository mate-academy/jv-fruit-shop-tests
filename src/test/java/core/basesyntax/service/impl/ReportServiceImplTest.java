package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.handler.BalanceHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseHandler;
import core.basesyntax.handler.ReturnHandler;
import core.basesyntax.handler.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionParser;
import core.basesyntax.strategy.StrategyOptions;
import core.basesyntax.strategy.StrategyOptionsImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String RESUL = "fruit,quantity" + System.lineSeparator()
            + "banana,107" + System.lineSeparator()
            + "apple,10" + System.lineSeparator();
    private static ReportService reportCreated;
    private static Map<FruitTransaction.Operation, OperationHandler> doingStrategy;
    private static StrategyOptions strategyOptions;
    private static TransactionParser transactionParser;
    private static List<String> informationWithFile;

    @BeforeClass
    public static void beforeClass() {
        doingStrategy = new HashMap<>();
        doingStrategy.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        doingStrategy.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        doingStrategy.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        doingStrategy.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        strategyOptions = new StrategyOptionsImpl(doingStrategy);
        transactionParser = new TransactionParserImpl(strategyOptions);
        reportCreated = new ReportServiceImpl();
        informationWithFile = new ArrayList<>();
        informationWithFile.add("type,fruit,quantity");
        informationWithFile.add("b,banana,20");
        informationWithFile.add("s,banana,100");
        informationWithFile.add("p,banana,13");
        informationWithFile.add("b,apple,10");
    }

    @Before
    public void setUp() {
        transactionParser.saveToDb(informationWithFile);
    }

    @Test
    public void report_correctData_isOk() {
        assertEquals("Expected report: " + RESUL + ", but was: "
                + reportCreated.createReport(), reportCreated.createReport(), RESUL);
    }
}
