package core.basesyntax.services.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operationhandlers.BalanceOperationHandler;
import core.basesyntax.operationhandlers.OperationHandler;
import core.basesyntax.operationhandlers.PurchaseOperationHandler;
import core.basesyntax.operationhandlers.ReturnOperationHandler;
import core.basesyntax.operationhandlers.SupplyOperationHandler;
import core.basesyntax.services.DataProcessing;
import core.basesyntax.services.FileDataReader;
import core.basesyntax.services.FileDataWriter;
import core.basesyntax.services.ReportGenerator;
import core.basesyntax.services.ShopService;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileDataWriterImplTest {
    private static final String OUTPUT_PATH = "src/test/resources/output1.csv";
    private static final Path INPUT_PATH = Path.of("src/test/resources/input1.csv");
    private static final String expectedString = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator()
            + System.lineSeparator();

    private FileDataReader dataReader;
    private DataProcessing dataProcessing;
    private FileDataWriter fileDataWriter;
    private OperationStrategy operationStrategy;
    private ReportGenerator reportGenerator;
    private Storage storage = new Storage();
    private ShopService shopService;
    private static Map<Operation, OperationHandler> operationMap = new HashMap<>();

    @BeforeEach
    void setUp() {
        fileDataWriter = new FileDataWriterImpl(Path.of(OUTPUT_PATH));
        dataReader = new FileDataReaderImpl();
        operationMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationMap);
        dataProcessing = new DataProcessingImpl((OperationStrategyImpl) operationStrategy, storage);
        reportGenerator = new ReportGeneratorImpl(storage);
        shopService = new ShopServiceImpl(storage);
    }

    @Test
    void writeToFile_validFile_ok() {
        List<String> readData = dataReader.readData(INPUT_PATH);
        List<FruitTransaction> fruitTransactions = dataProcessing.processData(readData);
        shopService.operations(fruitTransactions);

        String report = reportGenerator.getReport();
        fileDataWriter.writeData(report, OUTPUT_PATH);

        List<String> list = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(OUTPUT_PATH))) {
            String string;
            while ((string = bf.readLine()) != null) {
                list.add(string);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder str = new StringBuilder();
        for (String string : list) {
            str.append(string).append(System.lineSeparator());
        }
        String actualString = str.toString();

        Assertions.assertEquals(expectedString, actualString);
    }
}
