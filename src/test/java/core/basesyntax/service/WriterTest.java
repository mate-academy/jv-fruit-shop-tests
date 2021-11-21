package core.basesyntax.service;

import core.basesyntax.service.impl.WriterCsvImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterTest {
    private static Writer writer;
    private static final String VALID_OUTPUT_DATA_PATH = "src/test/resources/validOutputData.csv";
    private static final String VALID_REPORT =
            "fruit,quantity" + System.lineSeparator()
                    + "banana,152" + System.lineSeparator()
                    + "apple,90" + System.lineSeparator();

    @BeforeClass
    public static void beforeClass() throws Exception {
        writer = new WriterCsvImpl();
    }

    @Test
    public void writeToFile_validDataAndPath_ok() {
        writer.writeToFile(VALID_REPORT, VALID_OUTPUT_DATA_PATH);
        String expected = readFromFile(VALID_OUTPUT_DATA_PATH);
        Assert.assertEquals(expected, VALID_REPORT);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_validDataAndNullPath_notOk() {
        writer.writeToFile(VALID_REPORT, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullDataAndNullPath_notOk() {
        writer.writeToFile(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullDataAndValidPath_notOk() {
        writer.writeToFile(null, VALID_OUTPUT_DATA_PATH);
    }

    @Test
    public void writeToFile_emptyDataAndValidPath_ok() {
        String actual = "";
        writer.writeToFile(actual, VALID_OUTPUT_DATA_PATH);
        String expected = readFromFile(VALID_OUTPUT_DATA_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_validDataAndEmptyFilePath_notOk() {
        writer.writeToFile(VALID_REPORT, "");
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
