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
    private static final String SOME_FILE = "someFile";
    private static CustomFileReader customFileReader;

    @BeforeAll
    static void beforeAll() {

        customFileReader = new CustomFileReaderImpl();
    }

    @Test
    void readSize() {
        List<String> read = customFileReader.read(FILE_TO_READ);
        assertNotNull(read);
        assertTrue(read.size() > 0);

    }

    @Test
    void readNonExistentFile() {
        assertThrows(RuntimeException.class, () ->
                customFileReader.read(SOME_FILE));
    }

    @Test
    void readContent() {
        List<String> read = customFileReader.read(FILE_TO_READ);
        assertEquals("type,fruit,quantity",read.get(0));
        assertEquals("b,banana,20",read.get(1));
        assertEquals("b,apple,100",read.get(2));
    }

    @Test
    void readEmptyContent() {
        List<String> read = customFileReader.read(TEST_FILE);
        assertTrue(read.isEmpty());
        assertTrue(read.size() == 0);
        assertThrows(RuntimeException.class, () ->
                read.get(1));

    }
}
