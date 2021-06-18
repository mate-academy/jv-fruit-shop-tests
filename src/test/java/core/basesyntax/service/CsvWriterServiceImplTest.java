package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvWriterServiceImplTest {
    private static WriterService writerService;
    private static final String PATH_FOR_TEST
            = "src/test/java/resources/csvFileWriterTestResult.csv";
    private static final String RESULT_PATH
            = "src/test/java/resources/report.csv";
    private static final String WRONG_RESULT_PATH
            = "////";
    private static final String INPUT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new CsvWriterServiceImpl();
    }

    @Test
    public void writeToFileTestWithCorrectInput_Ok() {
        writerService.writeToFile(INPUT, PATH_FOR_TEST);

        List<String> expectedList;
        try {
            expectedList = Files.readAllLines(Path.of(RESULT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + RESULT_PATH);
        }

        List<String> actualList;
        try {
            actualList = Files.readAllLines(Path.of(PATH_FOR_TEST));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + PATH_FOR_TEST);
        }

        assertEquals(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFileTestWithWrongInput_NotOk() {
        writerService.writeToFile(INPUT, WRONG_RESULT_PATH);
    }
}
