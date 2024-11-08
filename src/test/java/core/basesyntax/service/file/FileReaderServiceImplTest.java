package core.basesyntax.service.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.FileReadException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final List<String> DATA = List.of("type,fruit,quantity",
            "b,strawberry,50",
            "b,banana,40",
            "s,strawberry,15",
            "p,strawberry,38",
            "p,banana,10",
            "r,banana,5");
    private static final String DATA_BASE_PATH
            = "src/test/java/core/basesyntax/resources/DataBase.csv";
    private static final String NON_EXISTING_FILE_PATH
            = "src/test/java/core/basesyntax/resources/NonExistingFile.csv";
    private static final String EMPTY_FILE_PATH
            = "src/test/java/core/basesyntax/resources/EmptyFile.csv";
    private FileReaderService fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderServiceImpl();
    }

    @Test
    void read_isOk() {
        assertEquals(DATA, fileReader.read(DATA_BASE_PATH));
    }

    @Test
    void read_nonExistingFile_notOk() {
        assertThrows(FileReadException.class, () -> fileReader.read(NON_EXISTING_FILE_PATH));
    }

    @Test
    void read_emptyFile_isOk() {
        assertTrue(fileReader.read(EMPTY_FILE_PATH).isEmpty());
    }

    @Test
    void read_nullValue_notOk() {
        assertThrows(IllegalArgumentException.class, () -> fileReader.read(null));
    }
}
