package core.basesyntax.files;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {

    private final FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    void read_validFile_ok() {
        List<String> result = fileReader.read("src/test/resources/reportToRead.csv");
        Assertions.assertFalse(result.isEmpty(), "File shouldn't be empty.");
        Assertions.assertEquals("type,fruit,quantity", result.get(0),
                "Wrong content.");
    }

    @Test
    void read_fileNotFound_exceptionThrown() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileReader.read("src/test/resources/wrong.csv");
        }, "Expected RuntimeException for non-existent file.");
    }
}
