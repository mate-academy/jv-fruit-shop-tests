package core.basesyntax.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ParsingService;
import core.basesyntax.service.ReadingService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WritingService;
import core.basesyntax.service.impl.ParsingServiceImpl;
import core.basesyntax.service.impl.ReadingServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WritingServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.junit.Test;

public class ServicesTest {
    private ReadingService readingService = new ReadingServiceImpl();
    private ParsingService parsingService = new ParsingServiceImpl();
    private ReportService reportService = new ReportServiceImpl();
    private WritingService writingService = new WritingServiceImpl();


    @Test
    public void readingService_CorrectPath_OK() {
        String filePath = "src/test/java/core/basesyntax"
                + "/test_resouces/test_read_from.csv";
        List<String> list = new ArrayList<>(Collections.singleton("type,fruit,quantity, b,banana,20, b,apple,100"));
        assertEquals(readingService.readFromFile(filePath).toString(), list.toString());
    }

    @Test
    public void readingService_IncorrectPath_NotOk() {
        String filePath = "wrongPath";
        assertThrows(RuntimeException.class, () -> readingService.readFromFile(filePath));
    }

    @Test
    public void readingService_Null_NotOk() {
        String filePath = null;
        assertThrows(RuntimeException.class, () -> readingService.readFromFile(filePath));
    }

    @Test
    public void readingService_Empty_NotOk() {
        String filePath = "";
        assertThrows(RuntimeException.class, () -> readingService.readFromFile(filePath));
    }

    @Test
    public void parsingService_CorrectData_Ok() {
        String line = "b,banana,20";
        String expected = "FruitTransaction{operation=BALANCE, fruit='banana', quantity=20}";
        assertEquals(parsingService.parse(line).toString(), expected);
    }

    @Test
    public void parsingService_IncorrectData_NotOk() {
        String line = "03,213,one";
        assertThrows(RuntimeException.class, () -> parsingService.parse(line));
    }

    @Test
    public void parsingService_Null_NotOk() {
        String line = null;
        assertThrows(RuntimeException.class, () -> parsingService.parse(line));
    }

    @Test
    public void parsingService_Empty_NotOk() {
        String line = "";
        assertThrows(RuntimeException.class, () -> parsingService.parse(line));
    }

    @Test
    public void reportService_CorrectDataOneLine_Ok() {
        Map<String, Integer> fruitStorage = new HashMap<>();
        fruitStorage.put("banana", 120);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,120" + System.lineSeparator();
        assertEquals(expected, reportService.createReport(fruitStorage));
    }

    @Test
    public void reportService_CorrectDataTwoLines_Ok() {
        Map<String, Integer> fruitStorage = new HashMap<>();
        fruitStorage.put("banana", 120);
        fruitStorage.put("apple", 1);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,120" + System.lineSeparator()
                + "apple,1" + System.lineSeparator();
        assertEquals(expected, reportService.createReport(fruitStorage));
    }

    @Test
    public void writingService_CorrectData_Ok() {
        String filePath = "src/test/java/core/basesyntax/test_resouces/toFile.csv";
        String text = "test Text";
        boolean thrown = false;
        try {
            writingService.writeToFile(text, filePath);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        assertFalse(thrown);
    }

    @Test
    public void writingService_NullDataOfPath_NotOk() {
        assertThrows(RuntimeException.class, () -> writingService.writeToFile("123", null));
    }

    @Test
    public void writingService_NullDataOfText_NotOk() {
        String filePath = "src/test/java/core/basesyntax/test_resouces/toFile.csv";
        assertThrows(RuntimeException.class, () -> writingService.writeToFile(null, filePath));
    }

    @Test
    public void writingService_EmptyDataOfPath_NotOk() {
        assertThrows(RuntimeException.class, () -> writingService.writeToFile("1324", ""));
    }

    @Test
    public void name() {
    }
}
