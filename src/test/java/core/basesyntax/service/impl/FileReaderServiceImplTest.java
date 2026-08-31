package core.basesyntax.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    @Test
    void read_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.writeString(tempFile, "type,fruit,quantity\nb,apple,10\ns,banana,5");

        FileReaderServiceImpl reader = new FileReaderServiceImpl();
        List<String> lines = reader.read(tempFile.toString());

        Assertions.assertEquals(2, lines.size());
        Assertions.assertTrue(lines.get(0).contains("apple"));
    }

    @Test
    void read_invalidPath_notOk() {
        FileReaderServiceImpl reader = new FileReaderServiceImpl();
        Assertions.assertThrows(RuntimeException.class, () -> reader.read("nonexistent.csv"));
    }
}
