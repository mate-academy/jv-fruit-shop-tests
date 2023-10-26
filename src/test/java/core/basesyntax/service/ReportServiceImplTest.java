package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReportServiceImplTest {
    private static FileReader fileReader;
    private static ParseService parseService;
    private static OperationStrategy operationStrategy;
    private static ReportService reportService;
    private static final String SEPARATOR = ",";
    private static final String HEADER = "fruit,quantity";
    private static final String INPUT_1_FILE_NAME = "input1.csv";
    private static final String INPUT_2_FILE_NAME = "input2.csv";
    private static final String INPUT_3_FILE_NAME = "input3.csv";
    private static ClassLoader classLoader;

    @BeforeAll
    static void beforeAll() {
        classLoader = FileReaderImplTest.class.getClassLoader();
        fileReader = new FileReaderImpl();
        parseService = new ParseServiceImpl();
        reportService = new ReportServiceImpl();
        operationStrategy = new OperationStrategyImpl(new HashMap<>() {{
                put(FruitTransaction.Operation.BALANCE, new RemnantOperationHandler());
                put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
                put(FruitTransaction.Operation.RETURN, new SupplyOperationHandler());
                put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
            }}
        );
    }

    @Test
    @Order(3)
    void generateReportInput1File_Ok() {
        URL resources = classLoader.getResource(INPUT_1_FILE_NAME);
        List<String> readFromFile = fileReader.readFromFile(new File(resources.getFile()));
        List<FruitTransaction> fruitTransactions = parseService.parse(readFromFile);
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operationStrategy.get(fruitTransaction.getOperation())
                    .handle(fruitTransaction.getFruit(),
                            fruitTransaction.getQuantity());
        }
        String actual = reportService.generateReport();
        String expected = HEADER + System.lineSeparator()
                + "apple" + SEPARATOR + 90 + System.lineSeparator()
                + "banana" + SEPARATOR + 152 + System.lineSeparator()
                + "pear" + SEPARATOR + 140;
        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    void generateReportInput2File_Ok() {
        URL resources = classLoader.getResource(INPUT_2_FILE_NAME);
        List<String> readFromFile = fileReader.readFromFile(new File(resources.getFile()));
        List<FruitTransaction> fruitTransactions = parseService.parse(readFromFile);
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operationStrategy.get(fruitTransaction.getOperation())
                    .handle(fruitTransaction.getFruit(),
                            fruitTransaction.getQuantity());
        }
        String actual = reportService.generateReport();
        String expected = HEADER + System.lineSeparator()
                + "apple" + SEPARATOR + 160 + System.lineSeparator()
                + "banana" + SEPARATOR + 155 + System.lineSeparator()
                + "pear" + SEPARATOR + 140;
        assertEquals(expected, actual);
    }

    @Test
    @Order(1)
    void generateReportInput3File_Ok() {
        URL resources = classLoader.getResource(INPUT_3_FILE_NAME);
        List<String> readFromFile = fileReader.readFromFile(new File(resources.getFile()));
        List<FruitTransaction> fruitTransactions = parseService.parse(readFromFile);
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operationStrategy.get(fruitTransaction.getOperation())
                    .handle(fruitTransaction.getFruit(),
                            fruitTransaction.getQuantity());
        }
        String actual = reportService.generateReport();
        String expected = HEADER + System.lineSeparator()
                + "apple" + SEPARATOR + 160 + System.lineSeparator()
                + "banana" + SEPARATOR + 115 + System.lineSeparator()
                + "pear" + SEPARATOR + 140;
        assertEquals(expected, actual);
    }
}
