package service.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    public static final String GOOD_PATH = "src\\test\\resources\\report.csv";
    public static final String BAD_PATH = "bad\\path.csv";
    public static final String DATA = "bad data too";
    public static final String GOOD_DATA = "b, banana, 100\nh, malina, 300\n";
    public static final List<String> EXPECTED_LIST = List.of("b, banana, 100", "h, malina, 300");
    private static FileWriter fileWriter = new FileWriterImpl();

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_BadPath_Not_Ok() {
        fileWriter.writeToFile(BAD_PATH, DATA);
    }

    @Test
    public void writeToFile_GoodPath_Ok() {
        File file = new File(GOOD_PATH);
        fileWriter.writeToFile(file.getPath(), GOOD_DATA);
        List<String> expected = EXPECTED_LIST;
        try {
            List<String> actual = Files.readAllLines(file.toPath());
            assertEquals(expected, actual);
            Files.write(file.toPath(), "".getBytes());
        } catch (IOException exception) {
            throw new RuntimeException("Can't read from file");
        }
    }
}
