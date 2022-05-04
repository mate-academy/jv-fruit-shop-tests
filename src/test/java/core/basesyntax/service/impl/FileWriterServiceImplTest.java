package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class FileWriterServiceImplTest {
    private static final String REPORT_FILE = "src/test/resources/report.csv";
    private static FileWriterService fileWriter;
    private static String report;

    @Rule
    public final TestName name = new TestName();

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterServiceImpl();
        report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
    }

    @Before
    public void clearReportFile() {
        try {
            Files.write(Path.of(REPORT_FILE), "".getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to clear file by path: " + REPORT_FILE, e);
        }
    }

    @Test
    public void writeReport_ok() {
        fileWriter.write(REPORT_FILE, report);
        List<String> actual;

        try {
            actual = Files.readAllLines(Path.of(REPORT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file executing: " + name.getMethodName(), e);
        }

        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,152");
        expected.add("apple,90");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_emptyPath_notOk() {
        fileWriter.write("", report);
    }
}
