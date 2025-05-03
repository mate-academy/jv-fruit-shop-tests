package core.basesyntax.service.implementations;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String PATH_TO_REPORT
            = "src/test/resources/report.csv";
    private static final String INVALID_FILE_PATH
            = "src/test/resources/invalid_folder/report.csv";
    private static FileWriterService fileWriter;
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,152"
            + System.lineSeparator()
            + "apple,90"
            + System.lineSeparator();

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterServiceImpl();
    }

    @Test
    public void fileWriterServiceImplTest_writeToFile_Ok() {
        fileWriter.writeToFile(PATH_TO_REPORT, REPORT);
        List<String> expected = Arrays.asList(REPORT.split(System.lineSeparator()));
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(PATH_TO_REPORT));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + PATH_TO_REPORT);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void fileWriterServiceImplTest_writeToFile_invalidPath_NotOk() {
        fileWriter.writeToFile(INVALID_FILE_PATH, REPORT);
    }
}
