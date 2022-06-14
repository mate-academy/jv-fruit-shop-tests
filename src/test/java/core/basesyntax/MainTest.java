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
import java.io.File;
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
    public ExpectedException exceptionRule = ExpectedException.none();
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

    private void writeReport(String fileNameFrom, String fileNameTo) {
        List<String> info = myFileReader.readFromFile(fileNameFrom);
        dataValidator.validate(info);
        fruitTransactionProcessor.process(info);
        List<String> reportList = reportCreator.createReportList();
        myFileWriter.writeToFile(fileNameTo, reportList);
    }

    @Test
    public void makeReport_valid_ok() {
        writeReport("src" + File.separator + "main" + File.separator + "resources"
                        + File.separator + "input.csv",
                "src" + File.separator + "main" + File.separator + "resources"
                        + File.separator + "report.csv");
        List<String> expected;
        List<String> actual;
        try {
            expected = Files.readAllLines(Path.of("src" + File.separator + "main"
                    + File.separator + "resources" + File.separator + "expected.csv"));
            actual = Files.readAllLines(Path.of("src" + File.separator + "main"
                    + File.separator + "resources" + File.separator + "report.csv"));
        } catch (IOException e) {
            throw new RuntimeException("Can not read the file", e);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void makeReport_forEmpty_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.reportMissingExceptionWithMessage(
                "Should get exception for empty input file");
        writeReport("src" + File.separator + "main" + File.separator + "resources"
                        + File.separator + "empty.csv",
                "src" + File.separator + "main" + File.separator + "resources"
                        + File.separator + "empty_report.csv");
    }

    @Test
    public void makeReport_InvalidFirstLine_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.reportMissingExceptionWithMessage(
                "Should get exception for inappropriate 1 line");
        writeReport(
                "src" + File.separator + "main" + File.separator + "resources"
                        + File.separator + "inappropriate_1_line.csv",
                "src" + File.separator + "main" + File.separator + "resources"
                        + File.separator + "inappropriate_1_line_report.csv");
    }

    @Test
    public void makeReport_invalidOperation_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.reportMissingExceptionWithMessage(
                "Should get exception for inappropriate operation");
        writeReport("src" + File.separator + "main" + File.separator + "resources"
                        + File.separator + "inappropriate_operation.csv",
                "src" + File.separator + "main" + File.separator + "resources" + File.separator
                        + "inappropriate_operation_report.csv");
    }

    @Test
    public void makeReport_negativeFruitNumber_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.reportMissingExceptionWithMessage(
                "Should get exception for negative fruit number");
        writeReport("src" + File.separator + "main" + File.separator + "resources" + File.separator
                        + "negative_fruit_number.csv",
                "src" + File.separator + "main" + File.separator + "resources" + File.separator
                        + "negative_fruit_number_report.csv");
    }

    @Test
    public void makeReport_purchasingUnexistingFruits_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.reportMissingExceptionWithMessage(
                "Should get exception for purchasing fruits that was not introduced");
        writeReport("src" + File.separator + "main" + File.separator + "resources" + File.separator
                        + "purchasing_nonExistent_fruits.csv",
                "src" + File.separator + "main" + File.separator + "resources" + File.separator
                        + "purchasing_nonExistent_fruits_report.csv");
    }

    @After
    public void tearDown() {
        fruitDao.getStorage().clear();
    }
}
