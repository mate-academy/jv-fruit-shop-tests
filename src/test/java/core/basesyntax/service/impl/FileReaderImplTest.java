package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileReaderService;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private FileReaderService fileReaderService = new FileReaderImpl();

    @Test
    public void read_invalidFile_throwsRuntimeException() {
        String invalidFilePath = "src/test/resources/output.csv";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReaderService.read(invalidFilePath);
        });
        assert exception.getMessage().contains("Error reading file");
    }
}
