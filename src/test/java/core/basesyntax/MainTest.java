package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MainTest {
    private static Map<String, FruitHandler> handlersMap;
    private final MyFileReader myFileReader = new MyFileReaderImpl();
    private final FruitTransactionProcessor fruitTransactionProcessor
            = new FruitTransactionProcessorImpl(handlersMap);
    private final MyFileWriter myFileWriter = new MyFileWriterImpl();
    private final DataValidator dataValidator = new DataValidatorImpl();
    private final ReportCreator reportCreator = new ReportCreatorImpl();
    private final FruitDao fruitDao = new FruitDaoImpl();

    @BeforeAll
    static void beforeAll() {
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
    void makeReport_valid_ok() {
        writeReport("src\\main\\resources\\input.csv", "src\\main\\resources\\report.csv");
        List<String> expected;
        List<String> actual;
        try {
            expected = Files.readAllLines(Path.of("src\\main\\resources\\expected.csv"));
            actual = Files.readAllLines(Path.of("src\\main\\resources\\report.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }

    @Test
    void makeReport_forEmpty_notOk() {
        assertThrows(RuntimeException.class, () -> writeReport("src\\main\\resources\\empty.csv",
                "src\\main\\resources\\empty_report.csv"),"Should get exception for empty");
    }

    @Test
    void makeReport_InvalidFirstLine_notOk() {
        assertThrows(RuntimeException.class,
                () -> writeReport("src\\main\\resources\\inappropriate_1_line.csv",
                "src\\main\\resources\\inappropriate_1_line_report.csv"),
                "Should get exception for inappropriate 1 line");
    }

    @Test
    void makeReport_invalidOperation_notOk() {
        assertThrows(RuntimeException.class,
                () -> writeReport("src\\main\\resources\\inappropriate_operation.csv",
                "src\\main\\resources\\inappropriate_operation_report.csv"),
                "Should get exception for inappropriate operation");
    }

    @Test
    void makeReport_negativeFruitNumber_notOk() {
        assertThrows(RuntimeException.class,
                () -> writeReport("src\\main\\resources\\negative_fruit_number.csv",
                "src\\main\\resources\\negative_fruit_number_report.csv"),
                "Should get exception for negative fruit number");
    }

    @Test
    void makeReport_purchasingUnexistingFruits_notOk() {
        assertThrows(RuntimeException.class,
                () -> writeReport("src\\main\\resources\\purchasing_unexisting_fruits.csv",
                "src\\main\\resources\\purchasing_unexisting_fruits_report.csv"),
                "Should get exception for purchasing fruits that was not introduced");
    }

    @AfterEach
    void tearDown() {
        fruitDao.getStorage().clear();
    }
}
