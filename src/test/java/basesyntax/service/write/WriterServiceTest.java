package basesyntax.service.write;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void write_EmptyFilePathString_NotOK() {
        writerService.write("", "some report");
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_NullFilePath_NotOK() {
        writerService.write(null, "some report");
    }

    @Test (expected = NullPointerException.class)
    public void write_NullReport_NotOK() {
        writerService.write("testfile.csv", null);
    }

    @Test
    public void write_validInput_OK() {
        String report = "fruit,quantity\n"
                + "banana,18" + System.lineSeparator()
                + "apple,14" + System.lineSeparator()
                + "default,0" + System.lineSeparator();
        writerService.write("testfile.csv", report);
        List<String> actual = read("testfile.csv");
        List<String> expected = List.of("fruit,quantity","banana,18", "apple,14", "default,0");
        assertEquals(expected, actual);
    }

    @Test
    public void write_emptyReport_OK() {
        writerService.write("testfile.csv", "");
        List<String> actual = read("testfile.csv");
        List<String> expected = Collections.EMPTY_LIST;
        assertEquals(expected, actual);
    }

    private List<String> read(String filename) {
        try {
            return Files.readAllLines(new File(filename).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Test READ method filed");
        }
    }
}
