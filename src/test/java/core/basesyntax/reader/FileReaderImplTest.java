package core.basesyntax.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String TEST_FILE = "src/test/java/resourses/testFile.csv";
    private FileReader fileReader = new FileReaderImpl();

    @Test
    void read_nullFile_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> fileReader.read(null));
    }

    @Test
    void read_blankFile_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> fileReader.read(""));
    }

    @Test
    void read_validFile_returnsLines() {
        List<String> actual = fileReader.read(TEST_FILE);
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,20", "p,banana,13");

        assertEquals(expected, actual);
    }
}
