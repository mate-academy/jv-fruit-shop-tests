package core.basesyntax.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class ReportWriterImplTest {
    private static String FILE_NAME = "test.csv";
    private static String FILE_CONTENT = "test";
    private static ReportWriter reportWriter = new ReportWriterImpl();

    @Test
    public void checkFileContent_Ok() {
        reportWriter.writeReport(FILE_NAME, FILE_CONTENT);
        File file = new File(FILE_NAME);
        try {
            String actual = Files.readString(file.toPath());
            String expected = new String(FILE_CONTENT);
            Assert.assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + FILE_NAME, e);
        }
    }

    @AfterClass
    public static void afterClass() {
        File file = new File(FILE_NAME);
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't remove file: " + FILE_NAME, e);
        }
    }
}
