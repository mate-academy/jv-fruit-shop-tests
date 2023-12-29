package core.service.file;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriteToFileServiceImplTest {
    public static final String PATH_OUTPUT = "src/main/resources/report_output.csv";
    public static final String PATH_EMPTY = "";
    public static final String PATH_WRONG = "src/main/re475096kg/report_output.csv";
    private WriteToFileService writeToFileService = new WriteToFileServiceImpl();
    private List<String> actual;
    private List<String> expected;

    @Before
    public void setUp() throws Exception {
        expected = new ArrayList<>();
    }

    @Test
    public void writeReport_Ok() {
        expected.add("fruit,quantity");
        expected.add("banana,152");
        expected.add("apple,90");
        writeToFileService.writeReport(expected, PATH_OUTPUT);
        try {
            actual = Files.readAllLines(Path.of(String.valueOf(new File(PATH_OUTPUT))));
        } catch (IOException e) {
            fail("Can't read data from file");
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_NotOk() {
        writeToFileService.writeReport(null, PATH_OUTPUT);
    }

    @Test(expected = RuntimeException.class)
    public void writeReportWithEmptyPath_NotOk() {
        writeToFileService.writeReport(expected, PATH_EMPTY);
    }

    @Test(expected = RuntimeException.class)
    public void writeReportWithWrong_NotOk() {
        writeToFileService.writeReport(expected, PATH_WRONG);
    }
}
