package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_validFile_ok() {
        List<String> result = fileReader.read(
                "src/main/resources/reportToRead.csv");
        assertNotNull(result);
    }

    @Test
    void read_invalidFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("invalid/path.csv"));
    }
}
