package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String OUTPUT_FILE_ACTUAL = "src/test/java/core/basesyntax"
            + "/recources/outputFile1.csv";
    private static final String OUTPUT_FILE_EXPECTED = "src/test/java/core/basesyntax"
            + "/recources/outputFile2.csv";
    private static final String exceptionMessage = "Can't get the info from the file path ";
    private static WriterService writerService;
    private static String report;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
        report = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90\n"
                + "oranges,270";
    }

    @Test
    public void writeToFile_correctPath_ok() {
        writerService.writeToFile(report, OUTPUT_FILE_ACTUAL);
        List<String> actual = readFile(OUTPUT_FILE_ACTUAL);
        List<String> expected = List.of("fruit,quantity", "banana,152", "apple,90", "oranges,270");
        assertEquals(expected, actual);
    }

    public List<String> readFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException(exceptionMessage + filePath);
        }
    }
}
