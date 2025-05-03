package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReportMessageCreator;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMessageCreatorImplTest {
    private static final char SEPARATOR_TO_WORDS = ',';
    private static final String FRUIT = "fruit";
    private static final String QUANTITY = "quantity";
    private static final String APPLE = "apple";
    private static final Integer AMOUNT = 10;
    private static StringBuilder toReport;
    private static ReportMessageCreator reportMessageCreator;
    private static Map<String, Integer> map;

    @BeforeClass
    public static void beforeClass() {
        reportMessageCreator = new ReportMessageCreatorImpl();
        toReport = new StringBuilder(FRUIT + SEPARATOR_TO_WORDS
                + QUANTITY + System.lineSeparator());
        map = new HashMap<>();
    }

    @After
    public void tearDownAfterTest() {
        map.clear();
    }

    @Test(expected = RuntimeException.class)
    public void createMessage_with_null_input_parameter_notOk() {
        reportMessageCreator.createMessage(null);
        fail("The input parameter couldn't be null");
    }

    @Test(expected = RuntimeException.class)
    public void createMessage_with_empty_input_parameter_notOk() {
        reportMessageCreator.createMessage(map);
        fail("The input parameter couldn't be empty");
    }

    @Test
    public void createMessage_Ok() {
        toReport.append(APPLE)
                .append(SEPARATOR_TO_WORDS)
                .append(AMOUNT)
                .append(System.lineSeparator());
        map.put(APPLE, AMOUNT);
        assertEquals(reportMessageCreator.createMessage(map), toReport.toString());
    }
}
