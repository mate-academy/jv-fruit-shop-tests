package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportWriter;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.ReportWriterImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.stategy.BalanceHandler;
import core.basesyntax.stategy.FruitOperationHandler;
import core.basesyntax.stategy.OperationStrategy;
import core.basesyntax.stategy.OperationStrategyImpl;
import core.basesyntax.stategy.PurchaseOperation;
import core.basesyntax.stategy.ReturnOperation;
import core.basesyntax.stategy.SupplyOperation;
import core.basesyntax.validator.DataValidator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
    private File tempFileForRead;
    private File tempFileForWrite;
    private Map<String, Integer> inventoryData;
    private FileReader fileReader;
    private DataConverter dataConverter;
    private ReportGenerator reportGenerator;
    private ReportWriter reportWriter;
    private ShopService shopService;

    @BeforeEach
    void setUp() throws IOException {
        tempFileForRead = File.createTempFile("testToRead", ".csv");
        tempFileForWrite = File.createTempFile("testToWrite", ".csv");
        String content =
                "type,fruit,quantity" + System.lineSeparator()
                + "b,apple,10" + System.lineSeparator()
                + "b,banana,15" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,apple,5" + System.lineSeparator()
                + "r,banana,5";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFileForRead))) {
            writer.write(content);
        }
        inventoryData = new HashMap<>();
        fileReader = new FileReaderImpl();
        DataValidator dataValidator = new DataValidator();
        dataConverter = new DataConverterImpl(dataValidator);
        reportGenerator = new ReportGeneratorImpl();
        reportWriter = new ReportWriterImpl();
        Map<Operation, FruitOperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        delete(tempFileForWrite);
        delete(tempFileForRead);

    }
    
    private void delete(File tempFile) {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void mainTest_OK() {
        List<String> inputReport = fileReader.read(tempFileForRead.getAbsolutePath());
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);
        shopService.processTransaction(transactions, inventoryData);

        String resultingReport = reportGenerator.generateReport(inventoryData);
        reportWriter.write(resultingReport, tempFileForWrite.getAbsolutePath());

        List<String> outputReport = fileReader.read(tempFileForWrite.getAbsolutePath());
        assertNotNull(outputReport);
        assertFalse(outputReport.isEmpty());
    }
}
