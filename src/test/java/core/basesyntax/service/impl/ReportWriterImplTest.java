package core.basesyntax.service.impl;

import core.basesyntax.service.ReportWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterImplTest {
    private static ReportWriter reportWriter;
    private static final String PATH_NAME = "src/test/java/core/basesyntax/report/testReport.csv";
    private static final String INVALID_PATH_NAME = "";
    private static final String TEST_STRING = "fruit, quantity";

    @BeforeClass
    public static void beforeClass() {
        reportWriter = new ReportWriterImpl();
    }

    @Test
    public void reportWriter_Ok() {
        String expected = TEST_STRING;
        reportWriter.reportWriter(PATH_NAME, expected);
        try {
            String actual = Files.readString(Path.of(PATH_NAME));
            Assert.assertEquals(actual, expected);
        } catch (IOException e) {
            throw new RuntimeException("Can't read from testFile", e);
        }
    }

    @Test
    public void invalidPath_notOk() {
        Exception exception = new Exception();
        try {
            reportWriter.reportWriter(INVALID_PATH_NAME, TEST_STRING);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertEquals(exception.getClass(), RuntimeException.class);
    }
}
