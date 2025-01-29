package core.basesyntax.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CustomFileReaderTest {
    private static final String FILE_TO_READ = "reportToRead.csv";
    private static final String TEST_FILE = "emptyfile.csv";
    private static final String WRONG_PATH_FILE = "someFile";
    private static CustomFileReader customFileReader;

    @BeforeAll
    static void beforeAll() {

        customFileReader = new CustomFileReaderImpl();
    }

    @Test
    void read_size_ok() {
        List<String> read = customFileReader.read(FILE_TO_READ);
        assertNotNull(read);
        assertTrue(read.size() == 9);

    }

    @Test
    void read_nonExistentFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                customFileReader.read(WRONG_PATH_FILE));
    }

    @Test
    void read_content_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        List<String> read = customFileReader.read(FILE_TO_READ);
        assertEquals(expected,read);
    }

    @Test
    void read_emptyContent_notOk() {
        List<String> read = customFileReader.read(TEST_FILE);
        assertTrue(read.isEmpty());
        assertTrue(read.size() == 0);
    }
}
