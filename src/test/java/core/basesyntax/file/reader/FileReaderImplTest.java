package core.basesyntax.file.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String NON_EXISTING_FILE = "non_existing_file.csv";
    private static final String FILE_NOT_FOUND_MESSAGE = "File not found: " + NON_EXISTING_FILE;
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.write(tempFile, List.of(HEADER, Operation.BALANCE + ",banana,20"));

        List<String> result = fileReader.read(tempFile.toString());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(HEADER, result.get(0));
        assertEquals(Operation.BALANCE + ",banana,20", result.get(1));

        Files.delete(tempFile);
    }

    @Test
    void read_fileNotFound_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReader.read(NON_EXISTING_FILE));
        assertEquals(FILE_NOT_FOUND_MESSAGE, exception.getMessage());
    }
}
