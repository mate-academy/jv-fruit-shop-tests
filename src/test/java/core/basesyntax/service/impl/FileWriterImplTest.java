package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private FileWriter fileWriter;
    private String expectedData;

    @Before
    public void setUp() {
        fileWriter = new FileWriterImpl();
        expectedData = "expected data";
    }

    @Test
    public void writeToFile_Valid_Ok() throws IOException {
        String expectedData = "expected data";
        String actualResultPath = "src/test/resources//ActualResultTest.csv";
        fileWriter.writeToFile(actualResultPath, expectedData);
        String actualResult = Files.readString(Path.of(actualResultPath));
        assertEquals(expectedData, actualResult);
        Files.delete(Path.of(actualResultPath));
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NullPath_NotOk() {
        String notValidPathToFile = null;
        fileWriter.writeToFile(notValidPathToFile, expectedData);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NullData_NotOk() {
        String actualResultPath = "src/test/resources/ActualResultTest.csv";
        String nullData = null;
        fileWriter.writeToFile(actualResultPath, nullData);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_EmptyData_NotOk() {
        String actualResultPath = "src/test/resources/ActualResultTest.csv";
        String nullData = "";
        fileWriter.writeToFile(actualResultPath, nullData);
    }
}
