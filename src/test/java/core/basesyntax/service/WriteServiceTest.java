package core.basesyntax.service;

import core.basesyntax.service.impl.WriteServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteServiceTest {
    private static WriteService writeService;
    private static final String PATH_FILE = "src/main/resources/output.csv";
    private static final String WRONG_PATH = "";
    private static String REPORT;

    @BeforeClass
    public static void beforeAll() {
        writeService = new WriteServiceImpl();
        REPORT = "fruit,quantity" + System.lineSeparator()
                + "banana,15" + System.lineSeparator()
                + "apple,25" + System.lineSeparator() + "pear,10";
    }

    @Test
    public void write_writeAndReadCorrectContent_ok() {
        String expected = REPORT;
        writeService.write(expected, PATH_FILE);
        String actual = String.join(System.lineSeparator(), read(PATH_FILE));
        Assert.assertEquals("Content must be equal but was "
                + REPORT + "and returned " + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_emptyPath_notOk() {
        String report = REPORT;
        writeService.write(report, WRONG_PATH);
    }

    private List<String> read(String filePath) {
        try {
            return Files.readAllLines(Path.of(PATH_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Wrong file path " + PATH_FILE, e);
        }
    }
}
