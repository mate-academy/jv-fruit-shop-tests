package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String OUTPUT_FILE = "src/test/resources/outputTest.csv";
    private static final String INVALID_PATH = "src/test/java/invalidPath/inputTest.csv";
    private static final String PATH_WITH_WHITESPACE = "src/test/ resources/ inputTest.csv";

    private static WriterService writerService;
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        report = "fruit,quantity" + System.lineSeparator()
                + "banana,183" + System.lineSeparator()
                + "apple,49" + System.lineSeparator();
    }

    @Test
    public void writeToFile_Ok() {
        writerService.writeToFile(OUTPUT_FILE, report);
        List<String> actual;
        try {
            actual = Files.readAllLines(Paths.get(OUTPUT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from the file " + OUTPUT_FILE, e);
        }
        List<String> expected = List.of("fruit,quantity",
                "banana,183",
                "apple,49");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToInvalidPath_notOk() {
        writerService.writeToFile(INVALID_PATH, report);
    }

    @Test(expected = RuntimeException.class)
    public void writeToPathWithWhitespace_notOk() {
        writerService.writeToFile(PATH_WITH_WHITESPACE, report);
    }
}
