package core.basesyntax.service.fileReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.Assert.assertThrows;

class ReaderFileImplTest {
    private static final String INPUT_FILE = "src/test/resources/input.csv";
    private static final String OUTPUT_FILE = "src/main/resources/output.csv";
    private ReaderFile readerFile;
    private File file;

    @BeforeEach
    void setUp() {
        readerFile = new ReaderFileImpl();
        file = new File(INPUT_FILE);
    }

    @Test
    void fileIsExist() {
        Assertions.assertNotNull(file);
    }

    @Test
    void fileIsNotEmpty() {
        String fileContent = String.valueOf(readerFile.readFile(INPUT_FILE));
        Assertions.assertFalse(fileContent.isEmpty());
    }

    @Test
    void readFile_notOk() {
        assertThrows(RuntimeException.class, () -> {
            String fileContent = String.valueOf(readerFile.readFile("output.scv"));
            Assertions.assertFalse(fileContent.isEmpty());
        });
    }

    @Test
    void fileIsNotExist_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            readerFile.readFile(OUTPUT_FILE);
        });
    }
}
