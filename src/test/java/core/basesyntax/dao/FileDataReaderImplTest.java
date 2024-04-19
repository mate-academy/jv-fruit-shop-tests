package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class FileDataReaderImplTest {
    private final String inputFile = new String("src/main/resources/data.csv");
    private final String nonExistentFile = "nonExistentFile.txt";
    private FileDataReader fileDataReader = new FileDataReaderImpl();

    @Test
    void testFileReader_isOk() {
        List<String> expected = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");

        List<String> actual = fileDataReader.readFromFile(inputFile);
        assertEquals(expected, actual);

    }

    @Test
    void testFileReaderWithNonExistentFile_isOk() {
        assertThrows(RuntimeException.class, () -> fileDataReader.readFromFile(nonExistentFile));
    }
}
