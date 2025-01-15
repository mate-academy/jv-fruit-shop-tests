package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.fao.FileReaderImpl;
import core.basesyntax.fao.FileReaderMy;
import core.basesyntax.fao.FileWriterImpl;
import core.basesyntax.fao.FileWriterMy;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.ReportGenerator;
import core.basesyntax.model.ReportGeneratorImpl;
import core.basesyntax.model.convertor.DataConvertor;
import core.basesyntax.model.convertor.DataConvertorImpl;
import core.basesyntax.model.handler.BalanceOperation;
import core.basesyntax.model.handler.OperationHandler;
import core.basesyntax.model.handler.PurchaseOperation;
import core.basesyntax.model.handler.ReturnOperation;
import core.basesyntax.model.handler.SupplyOperation;
import core.basesyntax.model.strategy.OperationStrategy;
import core.basesyntax.model.strategy.OperationStrategyImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/**
 * Feel free to remove this class and create your own.
 */

public class FruitStorageTest {
    private static FileReaderMy fileReader;
    private static List<String> inputReport;
    private static DataConvertor dataConverter;
    private static List<FruitTransaction> transactions;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private static OperationStrategy operationStrategy;
    private static ShopService shopService;
    private static Map<String, Integer> storage;
    private static ReportGenerator reportGenerator;
    private static String resultingReport;
    private static FileWriterMy fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
        inputReport = fileReader.read("reportToRead.csv");
        dataConverter = new DataConvertorImpl();
        dataConverter.convertToTransaction(inputReport);
        transactions = dataConverter.convertToTransaction(inputReport);
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
        storage = shopService.process(transactions);
        reportGenerator = new ReportGeneratorImpl();
        resultingReport = reportGenerator.getReport(storage);
        fileWriter = new FileWriterImpl();
        fileWriter.write("finalReport.csv", resultingReport);
    }

    @Test
    void readFileTestOK() {

        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");

        assertEquals(expected, inputReport);
    }

    @Test
    void readFileTestNotOK() {
        String fileName = "somefile.csv";
        assertThrows(RuntimeException.class, () -> {
            List<String> inputReport = fileReader.read(fileName);
        });
    }

    @Test
    void convertorTestNotOK() {
        List<String> wrongList = new ArrayList<>();
        wrongList.add("gah ,shy, sjuw");
        wrongList.add("something");
        wrongList.add(null);
        wrongList.add("b,some,50");
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(wrongList);

        assertNotEquals(4, transactions.size());
        assertEquals(1, transactions.size());

    }

    @Test
    void convertorOk() {
        List<FruitTransaction> fruit = dataConverter.convertToTransaction(inputReport);
        assertEquals(8, fruit.size());
    }

    @Test
    void operationTestOk() {
        assertFalse(operationHandlers.isEmpty());
        assertTrue(operationHandlers.containsKey(FruitTransaction.Operation.BALANCE));
        assertTrue(operationHandlers.containsKey(FruitTransaction.Operation.PURCHASE));
        assertTrue(operationHandlers.containsKey(FruitTransaction.Operation.RETURN));
        assertTrue(operationHandlers.containsKey(FruitTransaction.Operation.SUPPLY));

    }

    @Test
    void operationTestNotOK() {
        assertFalse(operationHandlers.containsKey(new Object()));
        assertFalse(operationHandlers.containsKey(null));
        assertNotNull(operationHandlers);
    }

    @Test
    void shopServiceTestOK() {
        assertTrue(storage.containsKey("banana"));
        assertTrue(storage.containsKey("apple"));
        assertTrue(storage.get("banana") == 152);
        assertTrue(storage.get("apple") == 90);

    }

    @Test
    void shopServiceTestNotOK() {
        assertFalse(storage.isEmpty());
        assertFalse(storage.containsKey("peach"));
        assertNotNull(storage);
        assertFalse(storage.containsKey(null));
    }

    @Test
    void reportOK() {
        String expected = """
                fruit,quantity
                banana,152
                apple,90""";
        assertNotNull(resultingReport);
        assertEquals(expected, resultingReport);

    }

    @Test
    void reportNotOK() {
        assertNotEquals("random massage", resultingReport);
    }

    @Test
    void fileWriterOK() {
        assertNotNull(fileWriter);
    }
}
