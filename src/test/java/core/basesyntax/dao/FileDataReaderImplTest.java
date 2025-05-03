package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class FileDataReaderImplTest {
    private static final String INPUT_FILE = "src/test/resources/data.csv";
    private static final String NON_EXISTENT_FILE = "nonExistentFile.txt";
    private final FileDataReader fileDataReader = new FileDataReaderImpl();

    @Test
    void testFileReader_convertingToString_isOk() {
        List<String> expected = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");

        List<String> actual = fileDataReader.readFromFile(INPUT_FILE);
        assertEquals(expected, actual);
    }

    @Test
    void testFileReader_NonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () -> fileDataReader.readFromFile(NON_EXISTENT_FILE));
    }
}
