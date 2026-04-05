package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    @Test
    void read_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("test_read", ".csv");
        Files.writeString(tempFile, "type,fruit,quantity\nb,banana,20");
        List<String> result = new FileReaderImpl().read(tempFile.toString());
        assertEquals(2, result.size());
        assertEquals("b,banana,20", result.get(1));
        Files.deleteIfExists(tempFile);
    }
}
