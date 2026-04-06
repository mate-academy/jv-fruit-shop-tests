package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    @Test
    void writeAndRead_ok() throws IOException {
        Path tempFile = Files.createTempFile("test_write", ".csv");
        String data = "fruit,quantity" + System.lineSeparator() + "apple,10";
        new FileWriterImpl().write(data, tempFile.toString());
        List<String> result = new FileReaderImpl().read(tempFile.toString());
        assertEquals("apple,10", result.get(1));
        Files.deleteIfExists(tempFile);
    }

    @Test
    void write_toInvalidPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                new FileWriterImpl().write("data", "/invalid/path/file.csv"));
    }
}
