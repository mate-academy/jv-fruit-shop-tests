package core.basesyntax.service.impl;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String FILE_PATH = "src/test/java/core/basesyntax/resources/test.csv";
    private static final String WRONG_FILE_PATH = "aboba.csv";
    private FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    void read_validFilePath_ok() {
        List<String> actualLines = fileReader.read(FILE_PATH);
        Assertions.assertNotNull(actualLines, "List should not be null");
        Assertions.assertEquals(3, actualLines.size(),
                "File should contain 3 lines");
        Assertions.assertEquals("type,fruit,quantity",
                actualLines.get(0), "First line should match");
    }

    @Test
    void read_invalidFilePath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.read(WRONG_FILE_PATH);
        }, "Should throw RuntimeException if file does not exist");
    }
}
