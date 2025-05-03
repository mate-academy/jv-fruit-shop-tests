package core.basesyntax;

import core.basesyntax.converter.DataConverterImpl;
import core.basesyntax.file.CustomFileReader;
import core.basesyntax.file.CustomFileReaderImpl;
import core.basesyntax.file.CustomFileWriter;
import core.basesyntax.file.CustomFileWriterImpl;
import core.basesyntax.operation.handler.BalanceOperation;
import core.basesyntax.operation.handler.OperationHandler;
import core.basesyntax.operation.handler.PurchaseOperation;
import core.basesyntax.operation.handler.ReturnOperation;
import core.basesyntax.operation.handler.SupplyOperation;
import core.basesyntax.operation.operationstrategy.OperationStrategyImpl;
import core.basesyntax.reportgenerator.ReportGenerator;
import core.basesyntax.reportgenerator.ReportGeneratorImpl;
import core.basesyntax.shopservice.ShopService;
import core.basesyntax.shopservice.ShopServiceImpl;
import core.basesyntax.storagedao.StorageDao;
import core.basesyntax.storagedao.StorageDaoImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitShopTests {
    private static final String READER_FILE_NAME = "src/test/java/resources/testReport.csv";
    private static final String WRITER_FILE_NAME = "src/test/java/resources/testFinalReport.csv";
    private static StorageDao storageDao = new StorageDaoImpl();

    @Before
    public void clearStorage() {
        storageDao.clear();
    }

    @Test
    public void readFile_validData_ok() {
        List<String> expectedData = readFile(READER_FILE_NAME);

        Assert.assertEquals("The data read from the file does not match the expected data.",
                expectedData, new CustomFileReaderImpl().read(READER_FILE_NAME));
    }

    @Test
    public void convertToTransaction_validData_ok() {
        List<FruitTransaction> expectedTransactions = prepareExpectedTransactions();
        List<String> testInputData = prepareTestInputData();

        Assert.assertEquals("The data converted does not match the expected data.",
                expectedTransactions, new DataConverterImpl().convertToTransaction(testInputData));
    }

    @Test
    public void convertToTransaction_null_notOk() {
        RuntimeException exception = Assert.assertThrows(RuntimeException.class,
                () -> new DataConverterImpl().convertToTransaction(null));
        Assert.assertEquals("Input report must not be null", exception.getMessage());
    }

    @Test
    public void processTransactions_validData_ok() {
        List<FruitTransaction> testTransactions = prepareExpectedTransactions();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers
                = prepareTestOperationHandlers();

        ShopService shopService = new ShopServiceImpl(new OperationStrategyImpl(operationHandlers));
        shopService.process(testTransactions);

        Assert.assertEquals("Apple quantity is incorrect after processing transactions.",
                5, storageDao.getFruitQuantity("apple"));
        Assert.assertEquals("Banana quantity is incorrect after processing transactions.",
                100, storageDao.getFruitQuantity("banana"));
        Assert.assertEquals("Grape quantity is incorrect after processing transactions.",
                0, storageDao.getFruitQuantity("grape"));
        Assert.assertEquals("Orange quantity is incorrect after processing transactions.",
                41, storageDao.getFruitQuantity("orange"));
    }

    @Test
    public void generateReport_validStorage_ok() {
        storageDao.add("apple", 50);
        storageDao.add("banana", 25);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "banana,25";

        Assert.assertEquals("The generated report does not match the expected report.",
                expectedReport, reportGenerator.getReport());
    }

    @Test
    public void processTransactions_nullTransactions_notOk() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers
                = prepareTestOperationHandlers();

        ShopService shopService = new ShopServiceImpl(new OperationStrategyImpl(operationHandlers));

        NullPointerException exception = Assert.assertThrows(NullPointerException.class,
                () -> shopService.process(null));
    }

    @Test
    public void writeReport_validData_ok() {
        String testReport = "fruit,quantity\napple,10\nbanana,20";

        CustomFileWriter fileWriter = new CustomFileWriterImpl();
        fileWriter.write(testReport, WRITER_FILE_NAME);

        final List<String> writtenData = readFile(WRITER_FILE_NAME);

        List<String> expectedData = new ArrayList<>();
        expectedData.add("fruit,quantity");
        expectedData.add("apple,10");
        expectedData.add("banana,20");

        Assert.assertEquals("The data written to the file does "
                + "not match the expected data.", expectedData, writtenData);
    }

    @Test
    public void writeReport_emptyData_ok() {
        String testReport = "";
        String testFileName = "src/test/java/resources/testEmptyReport.csv";

        CustomFileWriter fileWriter = new CustomFileWriterImpl();
        fileWriter.write(testReport, testFileName);

        List<String> writtenData = readFile(testFileName);

        Assert.assertTrue("The file should be empty for empty data.",
                writtenData.isEmpty());
    }

    @Test
    public void readFile_fileDoesNotExist_notOk() {
        String invalidFileName = "nonexistentFile.csv";

        CustomFileReader fileReader = new CustomFileReaderImpl();

        RuntimeException exception = Assert.assertThrows(RuntimeException.class,
                () -> fileReader.read(invalidFileName));
        Assert.assertTrue("Exception message should "
                        + "indicate that the file cannot be read.",
                exception.getMessage().contains("Can't read data from file"));
    }

    private List<String> readFile(String fileName) {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file: " + fileName, e);
        }
        return data;
    }

    private List<FruitTransaction> prepareExpectedTransactions() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 5));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "grape", 55));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 41));
        return transactions;
    }

    private List<String> prepareTestInputData() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,apple,5");
        inputData.add("s,banana,100");
        inputData.add("p,grape,55");
        inputData.add("r,orange,41");
        return inputData;
    }

    private Map<FruitTransaction.Operation, OperationHandler> prepareTestOperationHandlers() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        return operationHandlers;
    }
}
