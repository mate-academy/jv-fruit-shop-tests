package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportReaderServiceTest {
    private static final String TEST_INPUT_REPORT_PATH
            = "src/test/resources/test_input_report.csv";
    private static final String WRONG_PATH
            = "src/test/java/fhjnn/report.csv";
    private static final String TEST_EMPTY_REPORT_PATH
            = "src/test/resources/test_empty_report.csv";
    private static final String TEST_INCORRECT_REPORT_PATH
            = "src/test/resources/test_incorrect_report.csv";
    private final ReportReaderService reportReaderService = new ReportReaderServiceImpl();

    @BeforeClass
    public static void beforeClass() {
        String inputReport = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,10" + System.lineSeparator()
                + "b,apple,20" + System.lineSeparator()
                + "b,cherry,100" + System.lineSeparator()
                + "s,apple,70" + System.lineSeparator()
                + "s,banana,80" + System.lineSeparator()
                + "p,banana,10" + System.lineSeparator()
                + "r,apple,30" + System.lineSeparator()
                + "p,apple,20" + System.lineSeparator()
                + "p,banana,20" + System.lineSeparator()
                + "s,banana,40" + System.lineSeparator();
        String incorrectReport = "Hello, mates!"
                + "How are you feeling?";
        try {
            Files.write(Paths.get(TEST_INPUT_REPORT_PATH), inputReport.getBytes());
            Files.write(Paths.get(TEST_INCORRECT_REPORT_PATH), incorrectReport.getBytes());
            Files.write(Paths.get(TEST_EMPTY_REPORT_PATH), "".getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file" + e);
        }
    }

    @Test
    public void readeFile_correctData_ok() {
        List<FruitTransaction> expectedReport = new ArrayList<>();
        expectedReport.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("banana"), 10));
        expectedReport.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 20));
        expectedReport.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("cherry"), 100));
        expectedReport.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("apple"), 70));
        expectedReport.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("banana"), 80));
        expectedReport.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("banana"), 10));
        expectedReport.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                new Fruit("apple"), 30));
        expectedReport.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("apple"), 20));
        expectedReport.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                new Fruit("banana"), 20));
        expectedReport.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                new Fruit("banana"), 40));
        List<FruitTransaction> actualReport = reportReaderService.readFile(TEST_INPUT_REPORT_PATH);
        assertEquals(expectedReport.toString(), actualReport.toString());
    }

    @Test
    public void readeFile_emptyFile_ok() {
        assertTrue(reportReaderService.readFile(TEST_EMPTY_REPORT_PATH).isEmpty());
    }

    @Test (expected = RuntimeException.class)
    public void readeFile_wrongPath_notOk() {
        reportReaderService.readFile(WRONG_PATH);
        reportReaderService.readFile(TEST_INCORRECT_REPORT_PATH);
    }
}
