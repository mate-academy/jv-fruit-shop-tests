package core.basesyntax.service.writer;

import core.basesyntax.service.reader.ReaderFile;
import core.basesyntax.service.reader.ReaderFileImpl;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteToFileImplTest {
    private static final String INPUT_FILE = "src/test/resources/input.csv";
    private static final String OUTPUT_FILE = "src/test/resources/output.csv";
    private WriteToFile write;
    private ReaderFile readerFile;
    private File file;

    @BeforeEach
    void setUp() {
        file = new File(INPUT_FILE);
        write = new WriteToFileImpl();
        readerFile = new ReaderFileImpl();
    }

    @Test
    void fileIsExist_ok() {
        Assertions.assertNotNull(file);
    }

    @Test
    void fileIsNotEmpty_ok() {
        String content = String.valueOf(readerFile.readFile(INPUT_FILE));
        Assertions.assertFalse(content.isEmpty());
    }

    @Test
    void fileWriteIsExist_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            String content = "Where is my money Lebovski";
            String fileName = "notFile.csv";
            write.writeToFile(fileName, content);
        });
    }

    @Test
    void writeContentToFile() {
        Assertions.assertDoesNotThrow(() -> {
            String content = "something";
            write.writeToFile(OUTPUT_FILE, content);
        });
    }
}
