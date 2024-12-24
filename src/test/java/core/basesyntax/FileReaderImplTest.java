package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private final FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    void read_validFile_ok() {
        String header = "type,fruit,quantity";
        List<String> lines = fileReader.read("src/test/resources/toRead.csv");
        assertNotNull(lines);
        assertEquals(header, lines.get(0));
    }

    @Test
    void read_invalidFilePath_throwsException() {
        assertThrows(RuntimeException.class, () -> fileReader.read("invalid/path.csv"));
    }
}
