package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadFromCsvServiceTest {
    private static Reader reader;
    private static List<String> expected;
    private static List<String> actual;
    private static final String CORRECT_FILE_PATH = "src/test/files/input/correctCsvFile.csv";
    private static final String EMPTY_FILE_PATH = "src/test/files/input/emptyCsvFile.csv";
    private static final String RANDOM_DATA_FILE_PATH = "src/test/files/input/randomCsvFile.csv";
    private static final String FILE_NOT_EXIST_PATH = "src/test/files/input/helloMentor.csv";

    @BeforeAll
    public static void setUp() {
        reader = new ReadFromCsvService();
    }

    @Test
    public void readFromCsvService_CorrectValues_Ok() {
        expected = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        actual = reader.read(CORRECT_FILE_PATH);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void readFromCsvService_EmptyFile_NotOk() {
        assertThrows(RuntimeException.class, () -> reader.read(EMPTY_FILE_PATH));
    }

    @Test
    public void readFromCsvService_randomValueFile_Ok() {
        expected = List.of("b,100",
                "banana,100",
                "p,banana",
                "r,10",
                "apple,20",
                "p,banana",
                "s,50");
        actual = reader.read(RANDOM_DATA_FILE_PATH);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void readFromCsvService_fileNotExist_NotOk() {
        assertThrows(RuntimeException.class, () -> reader.read(FILE_NOT_EXIST_PATH));
    }
}
