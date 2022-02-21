package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReaderImplTest {
    private static final String PATH_TO_GIVEN_DATA_FILE = "src/test/resources/daily_activities.csv";
    private static final String WRONG_PATH_TO_FILE = "1src/WRONG_PATH_TO_FILE";
    private static final List<List<String>> expectedResult = new ArrayList<>();
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();
    private final FileReader reader = new FileReaderImpl();

    @BeforeClass
    public static void beforeClass() throws Exception {
        List<String> record1 = new ArrayList<>();
        record1.add("b, banana, 20");
        List<String> record2 = new ArrayList<>();
        record2.add("b, apple, 100");
        List<String> record3 = new ArrayList<>();
        record3.add("s, banana, 100");
        List<String> record4 = new ArrayList<>();
        record4.add("p, banana, 13");
        List<String> record5 = new ArrayList<>();
        record5.add("r, apple, 10");
        List<String> record6 = new ArrayList<>();
        record6.add("p, apple, 20");
        List<String> record7 = new ArrayList<>();
        record7.add("p, banana, 5");
        List<String> record8 = new ArrayList<>();
        record8.add("s, banana, 50");
        List<String> record9 = new ArrayList<>();
        record9.add("b, cherry, 60");
        expectedResult.add(record1);
        expectedResult.add(record2);
        expectedResult.add(record3);
        expectedResult.add(record4);
        expectedResult.add(record5);
        expectedResult.add(record6);
        expectedResult.add(record7);
        expectedResult.add(record8);
        expectedResult.add(record9);
    }

    @Test
    public void getData_ok() {
        List<List<String>> actualResult = reader.getData(PATH_TO_GIVEN_DATA_FILE);
        assertNotNull(actualResult);
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    public void getData_wrongPath_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Cannot read file by path: " + WRONG_PATH_TO_FILE);
        reader.getData(WRONG_PATH_TO_FILE);
    }
}
