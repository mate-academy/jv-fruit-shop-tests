package core.basesyntax;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.Storage;
import core.basesyntax.fao.CustomFileReader;
import core.basesyntax.fao.CustomFileReaderImpl;
import core.basesyntax.fao.CustomFileWriter;
import core.basesyntax.fao.FileWriterImpl;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AppTest {
    private static final String FILE_TO_WRITE = "finalReport.csv";
    private static final String FILE_PATH = "output/" + FILE_TO_WRITE;
    private static CustomFileReader customFileReader;
    private static List<String> read;

    private static DataConvertor dataConvertor;
    private static List<FruitTransaction> fruitTransactions;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    private static ShopService shopService;
    private static ReportGenerator reportGenerator;
    private static CustomFileWriter customFileWriter;

    @BeforeAll
    static void beforeAll() {
        customFileReader = new CustomFileReaderImpl();
        read = customFileReader.read();
        dataConvertor = new DataConvertorImpl();
        fruitTransactions = dataConvertor.convertToTransaction(read);
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
        shopService.process(fruitTransactions);
        reportGenerator = new ReportGeneratorImpl();
        customFileWriter = new FileWriterImpl();
    }

    @Test
    void readerOK() {
        assertNotNull(read);
        assertTrue(read.size() > 0);

    }

    @Test
    void readerNotOk() {

        read.clear();
        assertTrue(read.size() == 0);
        assertTrue(read.isEmpty());
    }

    @Test
    void convertorOk() {
        assertFalse(fruitTransactions.isEmpty());
        assertTrue(fruitTransactions.size() > 0);

    }

    @Test
    void convertorNotOk() {
        read.clear();
        List<FruitTransaction> fruitTransactions = dataConvertor.convertToTransaction(read);
        assertTrue(fruitTransactions.isEmpty());
    }

    @Test
    void shopServiceOK() {
        assertNotNull(shopService);
    }

    @Test
    void reportOK() {
        String info = reportGenerator.getReport(Storage.storage);
        assertNotNull(info);
        assertFalse(info.isEmpty());
        assertTrue(info.contains("apple"));
        assertTrue(info.contains("fruit,quantity"));
        assertTrue(info.contains("apple,90"));
        assertTrue(info.contains("banana,152"));
        assertFalse(info.contains("peach"));

    }

    @Test
    void writerOk() throws IOException {
        String expectedContent = "fruit,quantity\napple,90\nbanana,50";
        CustomFileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(expectedContent);
        Path filePath = Path.of(FILE_PATH);
        assertTrue(Files.exists(filePath));
        List<String> fileContent = Files.readAllLines(filePath);
        assertFalse(fileContent.isEmpty());
        assertTrue(fileContent.contains("apple,90"));
        assertTrue(fileContent.contains("banana,50"));
    }
}

