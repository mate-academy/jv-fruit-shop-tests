package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderImplTest {
    @TempDir
    private Path tempDir;

    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_validFile_ok() throws IOException {
        Path filePath = tempDir.resolve("input.csv");
        Files.writeString(filePath, "type,fruit,quantity"
                + System.lineSeparator() + "b,banana,20");
        List<String> actual = fileReader.read(filePath.toString());
        assertEquals(2, actual.size());
        assertEquals("type,fruit,quantity", actual.get(0));
        assertEquals("b,banana,20", actual.get(1));
    }

    @Test
    void read_nonExistentFile_notOk() {
        String nonExistentPath = tempDir.resolve("doesNotExist.csv").toString();
        assertThrows(RuntimeException.class, () -> fileReader.read(nonExistentPath));
    }
}
