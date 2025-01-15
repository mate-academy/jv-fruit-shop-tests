package core.basesyntax.store.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.store.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {

    private static final String RESOURCE_FOLDER = "src/test/resources/";
    private static final String VALID_FILE = "test-file.csv";
    private static final String INVALID_FILE = "non-existent-file.csv";
    private FileReader fileReader;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReaderImpl();

        String content = "type,fruit,quantity"
                + System.lineSeparator()
                + "b,banana,20"
                + System.lineSeparator()
                + "p,apple,11";

        Files.writeString(Path.of(RESOURCE_FOLDER + VALID_FILE), content);
    }

    @Test
    void read_validFile_ok() {
        List<String> result = fileReader.read(RESOURCE_FOLDER + VALID_FILE);
        assertEquals(3, result.size());
        assertEquals("type,fruit,quantity", result.get(0));
    }

    @Test
    void read_invalidFile_emptyList() {
        List<String> result = fileReader.read(RESOURCE_FOLDER + INVALID_FILE);
        assertTrue(result.isEmpty());
    }
}
