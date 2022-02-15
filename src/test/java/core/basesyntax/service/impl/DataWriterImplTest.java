package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.DataWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class DataWriterImplTest {
    private final DataWriter dataWriter = new DataWriterImpl();
    private final String fileFirstLine = "fruit,quantity";
    private final String correctFilePath = "src/test/resources/report.csv";
    private final String wrongFilePath = "";
    private final List<String> expectedResult = new ArrayList<>(List.of(fileFirstLine,
            "b,banana,20", "b,apple,100", "s,banana,100", "p,banana,13"));

    @Test
    public void writeDataToFile_Ok() {
        boolean actual = dataWriter.writeDataToFile(expectedResult, correctFilePath);
        assertTrue(actual);
    }

    @Test
    public void writeToWrongFileDirectory_NotOk() {
        boolean actual = dataWriter.writeDataToFile(expectedResult, wrongFilePath);
        assertFalse(actual);
    }

    @Test
    public void writeToCorrectFileDirectory_Ok() {
        boolean actual = dataWriter.writeDataToFile(expectedResult, correctFilePath);
        assertEquals(true,actual);
    }
}
