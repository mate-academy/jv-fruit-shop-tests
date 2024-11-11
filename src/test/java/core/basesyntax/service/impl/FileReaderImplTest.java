package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;

public class FileReaderImplTest {
    private FileReaderService fileReaderService = new FileReaderImpl();

    @Test
    public void read_invalidFile_throwsRuntimeException() {
        String invalidFilePath = "src/test/resources/invalidFile.csv";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileReaderService.read(invalidFilePath);
        });
        assert exception.getMessage().contains("Error reading file");
    }
}
