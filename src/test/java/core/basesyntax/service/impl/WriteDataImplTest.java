package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.DataWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteDataImplTest {
    private static DataWriter dataWriter;
    private static String firstLine;
    private static String correctPath;
    private static String wrongPath;
    private static List<String> expectedResult;

    @BeforeClass
    public static void setUp() {
        dataWriter = new WriteDataImpl();
        firstLine = "fruit,quantity";
        correctPath = "src/test/resources/reportTest.csv";
        wrongPath = "src////test/reso/reportTest.csv";
        expectedResult = new ArrayList<>(List.of(firstLine,
                "b,apple,10", "b,banana,30", "s,banana,100", "p,apple,3"));
    }

    @Test
    public void writeData_ok() {
        boolean actual = dataWriter.writeData(expectedResult, correctPath);
        assertTrue(actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeDataToWrongPath_notOk() {
        boolean actual = dataWriter.writeData(expectedResult, wrongPath);
        assertFalse(actual);
    }

    @Test
    public void writeDataToCorrectPath_ok() {
        boolean actual = dataWriter.writeData(expectedResult, correctPath);
        assertEquals(true, actual);
    }
}
