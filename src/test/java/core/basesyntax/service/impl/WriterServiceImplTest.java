package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {
    private static final String REPORT_FILEPATH = "src/test/resources/reportTest.csv";
    private static WriterService writerService;
    private static String report;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

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
    public void writeTo_correctReport_ok() throws IOException {
        writerService.writeTo(REPORT_FILEPATH, report);
        Assert.assertEquals(report, readFile());
    }

    @Test
    public void writeTo_incorrectPath_notOk() {
        exceptionRule.expect(RuntimeException.class);
        writerService.writeTo("some/path", report);
    }

    private String readFile() throws IOException {
        return Files.readString(Path.of(REPORT_FILEPATH));
    }
}
