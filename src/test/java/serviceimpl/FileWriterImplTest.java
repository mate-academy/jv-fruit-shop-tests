package serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FileWriter;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String WAY_TO_REPORT = "src/main/resources/CloseDay.csv";
    private static String expectedString;

    @BeforeClass
    public static void beforeClass() {
        expectedString = "fruits, quantity" + System.lineSeparator()
                + "banana" + "," + "152" + System.lineSeparator()
                + "apple" + "," + "90";
    }

    @Test
    public void fileWriter_Ok() throws IOException {
        String expected = expectedString;
        String actual = Files.readString(Path.of(WAY_TO_REPORT));
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void fileIsNull_notOK() {
        fileWriter.writeToFile(null, WAY_TO_REPORT);
    }

    @Test (expected = RuntimeException.class)
    public void wayToReportIsNull_notOk() {
        fileWriter.writeToFile(expectedString, null);
    }
}
