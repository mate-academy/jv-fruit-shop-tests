package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Test;

public class FileWriterTest {

    private static final String PATH_FINAL_REPORT
            = "src/test/java/resources/finalReport.csv";

    @Test
    public void testFileWriter_Ok() {
        FileWriterCsv fileWriterCsv = new FileWriterCsvImpl();
        String expected = "TEST TO READ";
        fileWriterCsv.write(PATH_FINAL_REPORT, expected);
        try {
            String actual = Files.readString(Path.of(PATH_FINAL_REPORT));
            Assert.assertEquals(expected, actual);
        } catch (IOException e) {
            Assert.fail("Failed to read from file: " + PATH_FINAL_REPORT);
        }
    }

    @Test
    public void testFileWriter_NotOk() {
        FileWriterCsv fileWriterCsv = new FileWriterCsvImpl();
        Assert.assertThrows(RuntimeException.class,
                () -> fileWriterCsv.write("path/finalReport.csv", "TEST TO READ"));
    }

}
