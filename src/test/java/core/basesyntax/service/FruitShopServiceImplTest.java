package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.converter.DataConverter;
import core.basesyntax.converter.DataConverterImpl;
import core.basesyntax.file.reader.FileReader;
import core.basesyntax.file.writer.FileWriter;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.impl.BalanceOperationHandler;
import core.basesyntax.handler.impl.PurchaseOperationHandler;
import core.basesyntax.handler.impl.ReturnOperationHandler;
import core.basesyntax.handler.impl.SupplyOperationHandler;
import core.basesyntax.model.Operation;
import core.basesyntax.report.ReportGenerator;
import core.basesyntax.report.ReportGeneratorImpl;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private FruitShopService fruitShopService;
    private TestFileReader fileReader;
    private TestFileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileReader = new TestFileReader();
        fileWriter = new TestFileWriter();
        DataConverter dataConverter = new DataConverterImpl();
        OperationStrategy operationStrategy = new OperationStrategyImpl(createOperationHandlers());
        ReportGenerator reportGenerator = new ReportGeneratorImpl();

        fruitShopService = new FruitShopServiceImpl(fileReader, fileWriter, dataConverter,
                operationStrategy, reportGenerator);

        Storage.clear();
    }

    @Test
    void processTransactions_validData_ok() {
        String inputFilePath = "input.csv";
        String outputFilePath = "output.csv";

        fileReader.setFileData(Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,banana,10",
                "p,banana,5"
        ));

        fruitShopService.processTransactions(inputFilePath, outputFilePath);

        assertEquals(25, Storage.getFruitQuantity("banana"));

        String expectedReport = "fruit,quantity\nbanana,25\n";

        assertEquals(expectedReport, fileWriter.getWrittenData());
    }

    private Map<Operation, OperationHandler> createOperationHandlers() {
        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlers.put(Operation.RETURN, new ReturnOperationHandler());
        return operationHandlers;
    }

    private static class TestFileReader implements FileReader {
        private List<String> fileData;

        public void setFileData(List<String> fileData) {
            this.fileData = fileData;
        }

        @Override
        public List<String> read(String filePath) {
            return fileData;
        }
    }

    private static class TestFileWriter implements FileWriter {
        private String writtenData;

        @Override
        public void write(String data, String filePath) {
            this.writtenData = data;
        }

        public String getWrittenData() {
            return writtenData;
        }
    }
}
