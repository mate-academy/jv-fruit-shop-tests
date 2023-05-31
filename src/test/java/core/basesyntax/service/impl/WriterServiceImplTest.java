package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String REPORT_FILEPATH = "src/test/resources/reportTest.csv";
    private static WriterService writerService;
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,100"
                + System.lineSeparator();
    }

    @Test
    public void writeTo_correctReport_ok() {
        writerService.writeTo(REPORT_FILEPATH, report);
        Assert.assertEquals(report, readFile());
    }

    @Test(expected = RuntimeException.class)
    public void writeTo_incorrectPath_notOk() {
        writerService.writeTo("some/path", report);
    }

    private String readFile() {
        try {
            return Files.readString(Path.of(REPORT_FILEPATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read test file.");
        }
    }
}
