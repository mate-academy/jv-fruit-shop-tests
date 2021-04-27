package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvReaderServiceImplTest {
    private static final String FILE_FROM = "src/test/java/resources/fileWithCorrectData.csv";
    private static final String WRONG_FILE_FROM = "src/test/java/resources/testTestTest.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        readerService = new CsvReaderServiceImpl();
    }

    @Test
    public void readFromFileTestWithCorrectInput_Ok() {
        List<String> expected = new ArrayList<>();
        try (BufferedReader readFile = new BufferedReader(new FileReader(FILE_FROM))) {
            String lineText;
            while ((lineText = readFile.readLine()) != null) {
                expected.add(lineText);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + FILE_FROM);
        }
        List<String> actual = readerService.readFromFile(FILE_FROM);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFileTestWithWrongInput_NotOk() {
        readerService.readFromFile(WRONG_FILE_FROM);
    }
}
