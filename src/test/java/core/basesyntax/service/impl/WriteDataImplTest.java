package core.basesyntax.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.DataWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteDataImplTest {
    private static DataWriter dataWriter;
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String CORRECT_PATH = "src/test/resources/reportTest.csv";
    private static final String WRONG_PATH = "src////test/reso/reportTest.csv";
    private static List<String> expectedResult;

    @BeforeClass
    public static void setUp() {
        dataWriter = new WriteDataImpl();
        expectedResult = new ArrayList<>(List.of(FIRST_LINE,
                "b,apple,10", "b,banana,30", "s,banana,100", "p,apple,3"));
    }

    @Test
    public void writeData_ok() {
        boolean actual = dataWriter.writeData(expectedResult, CORRECT_PATH);
        assertTrue(actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeDataToWrongPath_notOk() {
        boolean actual = dataWriter.writeData(expectedResult, WRONG_PATH);
        assertFalse(actual);
    }
}
