package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String CORRECT_FILE_NAME = "src/main/resources/CorrectFromFile.csv";
    private static final String WRONG_FILE_NAME = "WrongFromFile.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void getRecords_correctFileName_Ok() {
        List<String> expected = List.of("b,banana,10", "b,apple,85",
                "s,banana,38", "p,banana,13", "r,apple,3",
                "p,apple,24", "p,banana,5", "s,banana,50");
        List<String> actual = fileReaderService.getRecords(CORRECT_FILE_NAME);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void getRecords_inputNull_Ok() {
        fileReaderService.getRecords(null);
    }

    @Test (expected = RuntimeException.class)
    public void getRecords_wrongFileName_Ok() {
        fileReaderService.getRecords(WRONG_FILE_NAME);
    }
}
