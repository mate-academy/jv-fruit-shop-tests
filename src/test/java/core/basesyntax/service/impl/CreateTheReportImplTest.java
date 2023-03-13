package core.basesyntax.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.service.CreateTheReport;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateTheReportImplTest {
    private static final String WORD_TO_IGNORE = "type";
    private static final String[] FIRST_LINE = new String[]{"type", "fruit", "quantity"};
    private static final String[] SECOND_LINE = new String[]{"b", "apple", "15"};
    private static final String FRUIT = "apple";
    private static final int EXPECTED_AMOUNT = 15;
    private static CreateTheReport theReportCreator;
    private static List<String[]> list;

    @BeforeClass
    public static void beforeClass() {
        theReportCreator = new CreateTheReportImpl();
        list = new ArrayList<>();
    }

    @After
    public void tearDownAfterTest() {
        list.clear();
        Storage.fruits.clear();
    }

    @Test(expected = RuntimeException.class)
    public void add_with_null_input_parameter_notOk() {
        theReportCreator.add(null);
        fail("The input parameter couldn't be null");
    }

    @Test(expected = RuntimeException.class)
    public void add_with_empty_input_parameter_notOk() {
        theReportCreator.add(list);
        fail("The input parameter couldn't be empty");
    }

    @Test
    public void add_Ok() {
        list.add(FIRST_LINE);
        list.add(SECOND_LINE);
        theReportCreator.add(list);
        assertSame(Storage.get(FRUIT), EXPECTED_AMOUNT);
    }

    @Test
    public void add_with_wrong_first_line_notOk() {
        list.add(FIRST_LINE);
        theReportCreator.add(list);
        assertNull(Storage.get(WORD_TO_IGNORE));
    }
}
