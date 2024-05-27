package core.basesyntax;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.FileParserService;
import core.basesyntax.service.impl.FileReaderService;
import core.basesyntax.service.impl.FileWriterService;
import core.basesyntax.service.impl.ReportGeneratorService;
import core.basesyntax.service.impl.TransactionProcessService;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ServicesTest {
    private static final String INPUT_FILE = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE = "src/main/resources/report.csv";
    private static final String TEST_FILE = "src/main/resources/test.csv";

    private static FileReaderService readFromFile;
    private static FileParserService fileParser;
    private static OperationStrategy operationStrategy;
    private static TransactionProcessService dataProcess;
    private static ReportGeneratorService reportGenerator;
    private static FileWriterService writeToFile;

    @AfterEach
    public void clearStorage() {
        Storage.Storage.clear();
    }

    @BeforeAll
    static void setUp() {
        readFromFile = new FileReaderService();
        fileParser = new FileParserService();
        operationStrategy = new OperationStrategyImpl(
                Map.of(Operation.BALANCE.getCode(), new BalanceOperationHandler(),
                        Operation.PURCHASE.getCode(), new PurchaseOperationHandler(),
                        Operation.RETURN.getCode(), new ReturnOperationHandler(),
                        Operation.SUPPLY.getCode(), new SupplyOperationHandler()));
        dataProcess = new TransactionProcessService(operationStrategy);
        reportGenerator = new ReportGeneratorService();
        writeToFile = new FileWriterService();
    }

    @Test
    public void read_validFile_ok() {
        String expectedResult = "type,fruit,quantityb,banana,20b,apple,100s,"
                + "banana,100p,banana,13r,apple,10p,apple,20p,banana,5s,banana,50";
        assertEquals(expectedResult, readFromFile.read(INPUT_FILE));
    }

    @Test
    public void read_invalidInput_notOk() {
        String newInput = "src/main/resources/data.csv";
        assertThrows(RuntimeException.class, () -> readFromFile.read(newInput));
    }

    @Test
    public void parse_validInput_ok() {
        String data = "type,fruit,quantityb,banana,20b,apple,100s,"
                + "banana,100p,banana,13r,apple,10p,apple,20p,banana,5s,banana,50";
        String[] expectedResult = {"b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50"};
        assertArrayEquals(expectedResult, fileParser.parse(data));
    }

    @Test
    public void process_validInput_ok() {
        String[] data = {"b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50"};
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("banana", 152);
        expectedResult.put("apple", 90);

        dataProcess.process(data);
        assertTrue(expectedResult.equals(Storage.Storage));
    }

    @Test
    public void createReport_validInput_ok() {
        String[] data = {"b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50"};
        dataProcess.process(data);
        String separator = System.lineSeparator();
        String expectedResult = "fruit,quantity" + separator
                + "banana,152" + separator
                + "apple,90" + separator;

        assertEquals(expectedResult, reportGenerator.createReport());
    }

    @Test
    public void write_validInput_ok() {
        String separator = System.lineSeparator();
        String data = "fruit,quantity" + separator
                + "banana,152" + separator
                + "apple,90" + separator;
        writeToFile.write(data, OUTPUT_FILE);
        int expectedResult = -1;

        try (BufferedInputStream file1 = new BufferedInputStream(new FileInputStream(OUTPUT_FILE));
                        BufferedInputStream file2 =
                                new BufferedInputStream(new FileInputStream(TEST_FILE))) {

            int result = 0;
            int chunk = 0;
            long position = 1;
            while ((chunk = file1.read()) != -1) {
                if (chunk != file2.read()) {
                    result = (int) position;
                }
                position++;
            }
            if (file2.read() == -1) {
                result = -1;
            } else {
                result = (int) position;
            }
            assertEquals(expectedResult, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
