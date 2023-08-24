package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import service.ReportCreator;

public class ReportCreatorImplTest {
    private static final ReportCreator REPORT_CREATOR = new ReportCreatorImpl();
    private static final Storage DEFAULT_STORAGE = new Storage(new HashMap<>());
    private static final Storage NULL_STORAGE = new Storage(null);
    private static final String EXPECTED_EMPTY_MESSAGE = "fruit,quantity";
    private static final String EXPECTED_DEFAULT_MESSAGE = "fruit,quantity"
            + System.lineSeparator() + "banana,10"
            + System.lineSeparator() + "apple,10";

    @Test
    public void nullFruitBoxStorage_notOk() {
        assertThrows(NullPointerException.class, () ->
                REPORT_CREATOR.createReport(NULL_STORAGE));
    }

    @Test
    public void nullStorage_notOk() {
        assertThrows(NullPointerException.class, () ->
                REPORT_CREATOR.createReport(null));
    }

    @Test
    public void emptyStorage_Ok() {
        assertEquals(EXPECTED_EMPTY_MESSAGE,
                REPORT_CREATOR.createReport(DEFAULT_STORAGE),
                "Empty report is expected");
    }

    @Test
    public void defaultStorage_Ok() {
        Map<String, Integer> fruitBox = new HashMap<>();
        fruitBox.put("banana", 10);
        fruitBox.put("apple", 10);
        assertEquals(EXPECTED_DEFAULT_MESSAGE,
                REPORT_CREATOR.createReport(new Storage(fruitBox)),
                "Report does not match the expected message");
    }

}
