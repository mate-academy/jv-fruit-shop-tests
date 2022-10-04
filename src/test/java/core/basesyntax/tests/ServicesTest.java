package core.basesyntax.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class ServicesTest {
    private ReadingService readingService = new ReadingServiceImpl();
    private ParsingService parsingService = new ParsingServiceImpl();
    private ReportService reportService = new ReportServiceImpl();
    private WritingService writingService = new WritingServiceImpl();

    @Test
    public void readingService_CorrectPath_OK() {
        String filePath = "src/test/java/test_resouces/test_read_from.csv";
        List<String> list = new ArrayList<>(Collections
                .singleton("type,fruit,quantity, b,banana,20, b,apple,100"));
        assertEquals(readingService.readFromFile(filePath).toString(), list.toString());
    }

    @Test (expected = RuntimeException.class)
    public void readingService_IncorrectPath_NotOk() {
        String filePath = "wrongPath";
        readingService.readFromFile(filePath);
    }

    @Test (expected = RuntimeException.class)
    public void readingService_Null_NotOk() {
        readingService.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void readingService_Empty_NotOk() {
        String filePath = "";
        readingService.readFromFile(filePath);
    }

    @Test
    public void parsingService_CorrectData_Ok() {
        String line = "b,banana,20";
        String expected = "FruitTransaction{operation=BALANCE, fruit='banana', quantity=20}";
        assertEquals(parsingService.parse(line).toString(), expected);
    }

    @Test (expected = RuntimeException.class)
    public void parsingService_IncorrectData_NotOk() {
        String line = "03,213,one";
        parsingService.parse(line);
    }

    @Test (expected = RuntimeException.class)
    public void parsingService_Null_NotOk() {
        parsingService.parse(null);
    }

    @Test (expected = RuntimeException.class)
    public void parsingService_Empty_NotOk() {
        String line = "";
        parsingService.parse(line);
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
        String filePath = "src/test/java/test_resouces/toFile.csv";
        String text = "test Text";
        boolean thrown = false;
        try {
            writingService.writeToFile(text, filePath);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        assertFalse(thrown);
    }

    @Test (expected = RuntimeException.class)
    public void writingService_NullDataOfPath_NotOk() {
        writingService.writeToFile("123", null);
    }

    @Test (expected = RuntimeException.class)
    public void writingService_NullDataOfText_NotOk() {
        String filePath = "src/test/java/test_resoucestoFile.csv";
        writingService.writeToFile(null, filePath);
    }

    @Test (expected = RuntimeException.class)
    public void writingService_EmptyDataOfPath_NotOk() {
        writingService.writeToFile("1324", "");
    }

}
