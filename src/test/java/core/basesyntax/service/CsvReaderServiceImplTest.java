package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        try {
            expected = Files.readAllLines(Path.of(FILE_FROM));
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
