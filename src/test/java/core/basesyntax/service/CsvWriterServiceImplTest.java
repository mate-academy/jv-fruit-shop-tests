package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvWriterServiceImplTest {
    private static WriterService writerService;
    private static final String PATH_FOR_TEST
            = "src/test/java/resources/csvFileWriterTestResult.csv";
    private static final String RESULT_PATH
            = "src/test/java/resources/report.csv";
    private static final String WRONG_RESULT_PATH
            = "src/test/java/resources/????";
    private static final String INPUT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90";

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new CsvWriterServiceImpl();
    }

    @Test
    public void writeToFileTestWithCorrectInput_Ok() {
        writerService.writeToFile(INPUT, PATH_FOR_TEST);

        StringBuilder expected = new StringBuilder();
        try (BufferedReader readFile
                     = new BufferedReader(new FileReader(RESULT_PATH))) {
            String lineText;
            while ((lineText = readFile.readLine()) != null) {
                expected.append(lineText);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + RESULT_PATH);
        }

        StringBuilder actual = new StringBuilder();
        try (BufferedReader readFile
                     = new BufferedReader(new FileReader(PATH_FOR_TEST))) {
            String lineText;
            while ((lineText = readFile.readLine()) != null) {
                actual.append(lineText);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + PATH_FOR_TEST);
        }

        assertEquals(expected.toString(), actual.toString());
    }

    @Test(expected = RuntimeException.class)
    public void writeToFileTestWithWrongInput_NotOk() {
        writerService.writeToFile(INPUT, WRONG_RESULT_PATH);
    }
}
