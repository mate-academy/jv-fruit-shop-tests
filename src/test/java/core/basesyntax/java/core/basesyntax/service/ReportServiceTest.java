package core.basesyntax.java.core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.java.core.basesyntax.dao.FruitTransactionDaoCsvImpl;
import core.basesyntax.java.core.basesyntax.db.Storage;
import core.basesyntax.java.core.basesyntax.model.FruitTransaction;
import core.basesyntax.java.core.basesyntax.service.impl.FruitTransactionServiceCsvImpl;
import core.basesyntax.java.core.basesyntax.service.impl.ReportServiceCsvImpl;
import core.basesyntax.java.core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.java.core.basesyntax.strategy.handler.BalanceHandler;
import core.basesyntax.java.core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.java.core.basesyntax.strategy.handler.PurchaseHandler;
import core.basesyntax.java.core.basesyntax.strategy.handler.ReturnHandler;
import core.basesyntax.java.core.basesyntax.strategy.handler.SupplyHandler;
import core.basesyntax.java.core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReportServiceTest {
    private static final String TRANSACTION_FULL
            = "src/test/java/resources/transaction.csv";
    private static final String NEGATIVE_TRANSACTION
            = "src/test/java/resources/negative_transaction.csv";
    private static final String TRANSACTION_MANY_DATA
            = "src/test/java/resources/transactions_many_data.csv";
    private static final String TRANSACTION_EMPTY
            = "src/test/java/resources/transaction_empty.csv";
    private static final String ACTUAL_REPORT
            = "src/main/resources/report.csv";
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;
    private static FruitTransactionService transactionService;
    private static ReportService reportService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandlerMap = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactionService = new FruitTransactionServiceCsvImpl();
        reportService
                = new ReportServiceCsvImpl(new FruitTransactionDaoCsvImpl(), operationStrategy);
    }

    @Before
    public void setUp() throws Exception {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        transactionService.addTransaction(TRANSACTION_FULL);
    }

    @Test
    public void writeReport_fullCsvFile_Ok() {
        reportService.writeReport(ACTUAL_REPORT);
        StringBuilder expected = new StringBuilder()
                .append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90");
        String actual = getActualReport();
        assertEquals(expected.toString(), actual);
    }

    @Test
    public void writeReport_emptyCsvFile_notOk() {
        exception.expect(RuntimeException.class);
        transactionService.addTransaction(TRANSACTION_EMPTY);
        reportService.writeReport(ACTUAL_REPORT);
    }

    @Test
    public void writeReport_negativeQuantity_notOk() {
        exception.expect(RuntimeException.class);
        transactionService.addTransaction(NEGATIVE_TRANSACTION);
        reportService.writeReport(ACTUAL_REPORT);
    }

    @Test
    public void writeReport_manyDataInCsvFile_Ok() {
        transactionService.addTransaction(TRANSACTION_MANY_DATA);
        reportService.writeReport(ACTUAL_REPORT);
        StringBuilder expected = new StringBuilder()
                .append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,1976")
                .append(System.lineSeparator())
                .append("apple,1170");
        String actual = getActualReport();
        assertEquals(expected.toString(), actual);
    }

    private String getActualReport() {
        List<String> report;
        try {
            report = Files.readAllLines(Path.of(ACTUAL_REPORT));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + ACTUAL_REPORT);
        }
        StringBuilder builder = new StringBuilder();
        for (String string : report) {
            builder.append(string).append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruitTransactions.clear();
    }
}
