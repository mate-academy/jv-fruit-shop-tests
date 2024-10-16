package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.FileRead;
import core.basesyntax.service.FileWrite;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.service.impl.FileReadImpl;
import core.basesyntax.service.impl.FileWriteImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.FruitOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import core.basesyntax.validation.DataValidator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopApplicationTest {
    private File tempFileForRead;
    private File tempFileForWrite;
    private Map<String, Integer> inventory;
    private FileRead fileRead;
    private DataValidator dataValidator;
    private DataConverter dataConverter;
    private ReportGenerator reportGenerator;
    private FileWrite fileWriter;
    private Map<Operation, FruitOperationHandler> operationHandlers;
    private OperationStrategy operationStrategy;
    private ShopService shopService;

    @BeforeEach
    void setUp() throws IOException {
        tempFileForRead = File.createTempFile("testReportForRead", ".txt");
        tempFileForWrite = File.createTempFile("testReportForWrite", ".txt");

        String content = "type,fruit,quantity\nb,apple,10\nb,banana,5";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFileForRead))) {
            writer.write(content);
        }

        inventory = new HashMap<>();
        fileRead = new FileReadImpl();
        dataValidator = new DataValidator();
        dataConverter = new DataConverterImpl(dataValidator);
        reportGenerator = new ReportGeneratorImpl();
        fileWriter = new FileWriteImpl();

        operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());

        operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
        tempFileForRead.deleteOnExit();
        tempFileForWrite.deleteOnExit();
    }

    @AfterEach
    public void tearDown() {
        deleteFile(tempFileForRead);
        deleteFile(tempFileForWrite);
    }

    private void deleteFile(File tempFileForWrite) {
        if (tempFileForWrite.exists()) {
            tempFileForWrite.delete();
        }
    }

    @Test
    public void mainFlowTest_main_Ok() {
        List<String> inputReport = fileRead.readDataFromFile(tempFileForRead.getAbsolutePath());
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);
        shopService.processFruitTransactions(transactions, inventory);

        String resultingReport = reportGenerator.getReport(inventory);
        fileWriter.write(resultingReport, tempFileForWrite.getAbsolutePath());

        List<String> outputReport = fileRead.readDataFromFile(tempFileForWrite.getAbsolutePath());
        Assertions.assertNotNull(outputReport);
        Assertions.assertFalse(outputReport.isEmpty());
    }
}
