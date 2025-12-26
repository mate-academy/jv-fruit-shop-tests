package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import service.FileReader;
import service.FileReaderImpl;

class FileReaderImplTest {
    private static final String EXISTING_FILE = "src/test/java/resource/test_data.csv";
    private static final String NON_EXISTENT_FILE = "src/test/java/resource/not_Real.csv";

    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_ExistingFile_Ok() {
        List<String> lines = fileReader.read(EXISTING_FILE);
        assertEquals(6, lines.size());
    }

    @Test
    void read_NonExistentFile_ThrowRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.read(NON_EXISTENT_FILE);
        });
    }
}
