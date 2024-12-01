package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dataprocessor.BalanceHandler;
import core.basesyntax.dataprocessor.DataConverter;
import core.basesyntax.dataprocessor.DataProcessor;
import core.basesyntax.dataprocessor.DefaultDataOperationStrategy;
import core.basesyntax.dataprocessor.Operation;
import core.basesyntax.dataprocessor.PurchaseHandler;
import core.basesyntax.dataprocessor.ReturnHandler;
import core.basesyntax.dataprocessor.SupplyHandler;
import core.basesyntax.fileprocessor.FileReader;
import core.basesyntax.fileprocessor.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopTest {

    private String inputFilePath;
    private String outputFilePath;
    private FruitShop fruitShop;

    @BeforeEach
    void setUp() throws IOException {
        Path tempDir = Files.createTempDirectory("fruitshop-test");
        inputFilePath = tempDir.resolve("test_input.csv").toString();
        outputFilePath = tempDir.resolve("test_output.csv").toString();
        FruitDB.getInstance().getInventory().clear();
        FileReader fileReader = new FileReader();
        FileWriter fileWriter = new FileWriter();
        DataConverter dataConverter = new DataConverter();
        DefaultDataOperationStrategy operationStrategy = new DefaultDataOperationStrategy(
                Map.of(
                        Operation.BALANCE, new BalanceHandler(),
                        Operation.SUPPLY, new SupplyHandler(),
                        Operation.PURCHASE, new PurchaseHandler(),
                        Operation.RETURN, new ReturnHandler()
                )
        );
        DataProcessor dataProcessor = new DataProcessor(operationStrategy);
        ReportGenerator reportGenerator = new ReportGenerator(FruitDB.getInstance());
        fruitShop = new FruitShop(
                fileReader, dataConverter, dataProcessor, reportGenerator, fileWriter
        );
        Files.write(Path.of(inputFilePath), List.of(
                "b,apple,50",
                "s,banana,30",
                "p,apple,10",
                "r,banana,5"
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        Path inputFile = Path.of(inputFilePath);
        Path outputFile = Path.of(outputFilePath);
        if (Files.exists(inputFile)) {
            inputFile.toFile().setWritable(true);
            Files.deleteIfExists(inputFile);
        }
        if (Files.exists(outputFile)) {
            outputFile.toFile().setWritable(true);
            Files.deleteIfExists(outputFile);
        }
        FruitDB.getInstance().getInventory().clear();
    }

    @Test
    void run_invalidOperation_throwsIllegalArgumentException() {
        FileReader fileReader = new FileReader() {
            @Override
            public List<String> read(String filePath) {
                return List.of("x,apple,50");
            }
        };
        DataConverter dataConverter = new DataConverter();
        DefaultDataOperationStrategy strategy = new DefaultDataOperationStrategy(
                Map.of(
                        Operation.BALANCE, new BalanceHandler(),
                        Operation.SUPPLY, new SupplyHandler(),
                        Operation.PURCHASE, new PurchaseHandler(),
                        Operation.RETURN, new ReturnHandler()
                )
        );
        DataProcessor dataProcessor = new DataProcessor(strategy);
        ReportGenerator reportGenerator = new ReportGenerator(FruitDB.getInstance());
        FileWriter fileWriter = new FileWriter();

        FruitShop fruitShop = new FruitShop(
                fileReader, dataConverter, dataProcessor, reportGenerator, fileWriter
        );

        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> fruitShop.run(inputFilePath, outputFilePath)
        );
        assertTrue(exception.getCause() instanceof IllegalArgumentException);
        assertEquals("Invalid operation code: x", exception.getCause().getMessage());
    }

    @Test
    void run_validData_createsReport() throws IOException {
        fruitShop.run(inputFilePath, outputFilePath);
        List<String> expectedReport = List.of("Fruit,Quantity", "apple,40", "banana,35");
        List<String> actualReport = Files.readAllLines(Path.of(outputFilePath));
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void run_fileWriterFailure_throwsRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fruitShop.run(inputFilePath, "/root/protected_output.csv");
        });
        assertTrue(exception.getMessage().contains("Failed to write data to file"));
    }
}
