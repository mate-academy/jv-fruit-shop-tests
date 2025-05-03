package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterCsvImplTest {
    private static Writer fileWriter;
    private static final String OUTPUT_FILE_PATH = "src/test/java/resources/output_database.csv";
    private static String report;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new WriterCsvImpl();
        report = "fruit,quantity" + System.lineSeparator()
                + "apple,63" + System.lineSeparator()
                + "banana,125";
    }

    @Test
    public void writeToFile_validFilePathAndReport_Ok() {
        fileWriter.writeToFile(report, OUTPUT_FILE_PATH);
        String expected = report;
        String actual;
        try {
            actual = Files.readString(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Files.readString method can't read file "
                    + "inside writeToFile_validFilePathAndReport_Ok method");
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_invalidFilePath_notOk() {
        String wrongOutputFilePath = "src/test/java/resources/2/output_database.csv";
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Can't write data to file " + wrongOutputFilePath);
        fileWriter.writeToFile(report, wrongOutputFilePath);
    }

    @Test
    public void writeToFile_emptyFilePath_notOk() {
        String wrongOutputFilePath = "";
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Can't write data to file " + wrongOutputFilePath);
        fileWriter.writeToFile(report, wrongOutputFilePath);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_nullFilePath_notOk() {
        String wrongOutputFilePath = null;
        fileWriter.writeToFile(report, wrongOutputFilePath);
    }

    @Test
    public void writeToFile_emptyReport_Ok() {
        String report = "";
        fileWriter.writeToFile(report, OUTPUT_FILE_PATH);
        String expected = "";
        String actual;
        try {
            actual = Files.readString(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Files.readString method can't read file inside "
                    + "writeToFile_validFilePathAndReport_Ok method");
        }
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullReport_notOk() {
        String report = null;
        fileWriter.writeToFile(report, OUTPUT_FILE_PATH);
    }
}
