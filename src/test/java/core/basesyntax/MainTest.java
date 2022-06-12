package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitDao;
import core.basesyntax.db.FruitDaoImpl;
import core.basesyntax.service.DataValidator;
import core.basesyntax.service.FruitTransactionProcessor;
import core.basesyntax.service.MyFileReader;
import core.basesyntax.service.MyFileWriter;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.impl.DataValidatorImpl;
import core.basesyntax.service.impl.FruitTransactionProcessorImpl;
import core.basesyntax.service.impl.MyFileReaderImpl;
import core.basesyntax.service.impl.MyFileWriterImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.strategy.FruitAdder;
import core.basesyntax.strategy.FruitHandler;
import core.basesyntax.strategy.FruitSubtractor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MainTest {
    private static Map<String, FruitHandler> handlersMap;
    @Rule
    public ExpectedException expectionRule = ExpectedException.none();
    private final MyFileReader myFileReader = new MyFileReaderImpl();
    private final FruitTransactionProcessor fruitTransactionProcessor
            = new FruitTransactionProcessorImpl(handlersMap);
    private final MyFileWriter myFileWriter = new MyFileWriterImpl();
    private final DataValidator dataValidator = new DataValidatorImpl();
    private final ReportCreator reportCreator = new ReportCreatorImpl();
    private final FruitDao fruitDao = new FruitDaoImpl();

    @BeforeClass
    public static void beforeAll() {
        handlersMap = new HashMap<>();
        handlersMap.put("b", new FruitAdder());
        handlersMap.put("s", new FruitAdder());
        handlersMap.put("r", new FruitAdder());
        handlersMap.put("p", new FruitSubtractor());
    }

    void writeReport(String fileNameFrom, String fileNameTo) {
        List<String> info = myFileReader.readFromFile(fileNameFrom);
        dataValidator.validate(info);
        fruitTransactionProcessor.process(info);
        List<String> reportList = reportCreator.createReportList();
        myFileWriter.writeToFile(fileNameTo, reportList);
    }

    @Test
    public void makeReport_valid_ok() {
        writeReport("src/main/resources/input.csv", "src/main/resources/report.csv");
        List<String> expected;
        List<String> actual;
        try {
            expected = Files.readAllLines(Path.of("src/main/resources/expected.csv"));
            actual = Files.readAllLines(Path.of("src/main/resources/report.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void makeReport_forEmpty_notOk() {
        expectionRule.expect(RuntimeException.class);
        expectionRule.reportMissingExceptionWithMessage("Should get exception for empty");
        writeReport("src\\main\\resources\\empty.csv",
                "src\\main\\resources\\empty_report.csv");
    }

    @Test
    public void makeReport_InvalidFirstLine_notOk() {
        expectionRule.expect(RuntimeException.class);
        expectionRule.reportMissingExceptionWithMessage(
                "Should get exception for inappropriate 1 line");
        writeReport(
                "src\\main\\resources\\inappropriate_1_line.csv",
                "src\\main\\resources\\inappropriate_1_line_report.csv");
    }

    @Test
    public void makeReport_invalidOperation_notOk() {
        expectionRule.expect(RuntimeException.class);
        expectionRule.reportMissingExceptionWithMessage(
                "Should get exception for inappropriate operation");
        writeReport("src\\main\\resources\\inappropriate_operation.csv",
                "src\\main\\resources\\inappropriate_operation_report.csv");
    }

    @Test
    public void makeReport_negativeFruitNumber_notOk() {
        expectionRule.expect(RuntimeException.class);
        expectionRule.reportMissingExceptionWithMessage(
                "Should get exception for negative fruit number");
        writeReport("src\\main\\resources\\negative_fruit_number.csv",
                "src\\main\\resources\\negative_fruit_number_report.csv");
    }

    @Test
    public void makeReport_purchasingUnexistingFruits_notOk() {
        expectionRule.expect(RuntimeException.class);
        expectionRule.reportMissingExceptionWithMessage(
                "Should get exception for purchasing fruits that was not introduced");
        writeReport("src\\main\\resources\\purchasing_unexisting_fruits.csv",
                "src\\main\\resources\\purchasing_unexisting_fruits_report.csv");
    }

    @After
    public void tearDown() {
        fruitDao.getStorage().clear();
    }
}
