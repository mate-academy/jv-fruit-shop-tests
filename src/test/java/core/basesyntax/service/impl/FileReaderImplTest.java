package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private final FileReaderService fileReaderService = new FileReaderImpl();

    @Test
    void readFile_validFile_Ok() {
        List<String> result = fileReaderService.readFile("src/test/resources/testInput.csv");
        assertNotNull(result);
        assertEquals(3, result.size());
    }
}
