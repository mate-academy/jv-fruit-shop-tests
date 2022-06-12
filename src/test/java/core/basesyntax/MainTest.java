package core.basesyntax;

import core.basesyntax.db.FruitDao;
import core.basesyntax.db.FruitDaoImpl;
import core.basesyntax.service.*;
import core.basesyntax.service.impl.*;
import core.basesyntax.strategy.FruitAdder;
import core.basesyntax.strategy.FruitHandler;
import core.basesyntax.strategy.FruitSubtractor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    static Map<String, FruitHandler> handlersMap;
    MyFileReader myFileReader = new MyFileReaderImpl();
    FruitTransactionProcessor fruitTransactionProcessor
            = new FruitTransactionProcessorImpl(handlersMap);
    MyFileWriter myFileWriter = new MyFileWriterImpl();
    DataValidator dataValidator = new DataValidatorImpl();
    ReportCreator reportCreator = new ReportCreatorImpl();
    FruitDao fruitDao = new FruitDaoImpl();

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
        List<String> expected, actual;
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
        assertThrows(RuntimeException.class, () -> writeReport("src\\main\\resources\\inappropriate_1_line.csv",
                "src\\main\\resources\\inappropriate_1_line_report.csv"),"Should get exception for inappropriate 1 line");
    }

    @Test
    void makeReport_invalidOperation_notOk() {
        assertThrows(RuntimeException.class, () -> writeReport("src\\main\\resources\\inappropriate_operation.csv",
                "src\\main\\resources\\inappropriate_operation_report.csv"),"Should get exception for inappropriate operation");
    }

    @Test
    void makeReport_negativeFruitNumber_notOk() {
        assertThrows(RuntimeException.class, () -> writeReport("src\\main\\resources\\negative_fruit_number.csv",
                "src\\main\\resources\\negative_fruit_number_report.csv"),"Should get exception for negative fruit number");
    }

    @Test
    void makeReport_purchasingUnexistingFruits_notOk() {
        assertThrows(RuntimeException.class, () -> writeReport("src\\main\\resources\\purchasing_unexisting_fruits.csv",
                "src\\main\\resources\\purchasing_unexisting_fruits_report.csv"),
                "Should get exception for purchasing fruits that was not introduced");
    }

    @AfterEach
    void tearDown() {
        fruitDao.getStorage().clear();
    }
}