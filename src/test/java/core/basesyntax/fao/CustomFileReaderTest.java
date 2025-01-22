package core.basesyntax.fao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CustomFileReaderTest {
    private static final String FILE_TO_READ = "reportToRead.csv";
    private static final String TEST_FILE = "emptyfile.csv";
    private static CustomFileReader customFileReader;

    @BeforeAll
    static void beforeAll() {

        customFileReader = new CustomFileReaderImpl();
    }

    @Test
    void readOk() {
        List<String> read = customFileReader.read(FILE_TO_READ);
        assertNotNull(read);
        assertTrue(read.size() > 0);

    }

    @Test
    void readNotOk() {
        assertThrows(RuntimeException.class, () ->
                customFileReader.read("someFile"));
        List<String> read = customFileReader.read(TEST_FILE);
        assertTrue(read.isEmpty());
        assertTrue(read.size() == 0);
    }
}
