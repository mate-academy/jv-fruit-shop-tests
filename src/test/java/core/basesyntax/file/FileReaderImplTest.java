package core.basesyntax.file;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final FileReaderImpl fileReader = new FileReaderImpl();
    private static final String report = "src/main/resources/reportToRead.csv";

    @Test
    void read_validFile_ok() {
        List<String> result = fileReader.read(report);
        Assertions.assertFalse(result.isEmpty(), "File shouldn't be empty.");
        Assertions.assertEquals("type,fruit,quantity", result.get(0),
                "Wrong content.");
    }

    @Test
    void read_fileNotFound_exceptionThrown() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.read(report);
        }, "Expected RuntimeException for non-existent file.");
    }
}
