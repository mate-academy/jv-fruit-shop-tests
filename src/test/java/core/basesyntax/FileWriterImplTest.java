package core.basesyntax;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static List<String> expectedList;
    private static StringBuilder validReport;
    private static final String PATH_OUTPUT = "src/test/resources/outputTest.csv";
    private static final String INVALID_PATH_OUTPUT = "";

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
        expectedList = new ArrayList<>();
        expectedList.add("fruit,quantity");
        expectedList.add("banana,25");
        expectedList.add("apple,25");
        validReport = new StringBuilder();
        validReport.append("fruit,quantity");
        validReport.append(System.lineSeparator());
        validReport.append("banana,25");
        validReport.append(System.lineSeparator());
        validReport.append("apple,25");
        validReport.append(System.lineSeparator());
    }

    @Test
    public void writeDate_validReport_ok() throws IOException {
        fileWriter.writeData(PATH_OUTPUT, validReport.toString());
        List<String> actual = Files.readAllLines(Path.of(PATH_OUTPUT));
        Assert.assertEquals(expectedList, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeData_nullPath_notOk() throws IOException {
        fileWriter.writeData(null, validReport.toString());
    }

    @Test(expected = RuntimeException.class)
    public void writeData_incorrectPath_notOk() throws IOException {
        fileWriter.writeData(INVALID_PATH_OUTPUT, validReport.toString());
    }
}
