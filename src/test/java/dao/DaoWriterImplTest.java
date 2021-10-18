package dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DaoWriterImplTest {
    private static DaoWriter daoWriter;
    private static List<String> expectedReport;
    private static final String REPORT_FILE = "src/test/resources/report.txt";
    private static final String INCORRECT_PATH = "src/test/resources/111/report.txt";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        daoWriter = new DaoWriterImpl();
        expectedReport = new ArrayList<>();
        expectedReport.add("fruit,quantity");
        expectedReport.add("banana,152");
        expectedReport.add("apple,90");
    }

    @Test
    public void writeData_Ok() throws IOException {
        daoWriter.write(expectedReport, REPORT_FILE);
        List<String> actualReport = Files.readAllLines(Path.of(REPORT_FILE));
        Assert.assertEquals(expectedReport, actualReport);
    }

    @Test
    public void writeWithIncorrectPath_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Can't write the file: " + INCORRECT_PATH);
        daoWriter.write(expectedReport, INCORRECT_PATH);
    }

    @Test
    public void nullPath_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("File name is null");
        daoWriter.write(expectedReport, null);
    }

    @Test
    public void nullReport_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Report name is null");
        daoWriter.write(null, REPORT_FILE);
    }
}
