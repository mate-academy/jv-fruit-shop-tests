package core.basesyntax.service.filehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FileFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private static final String STORAGE_FILE_PATH = "src/test/java/resources/StorageInfo.csv";
    private static final String NONEXISTENT_FILE_PATH = "src/test/java/resources/TestFile.csv";
    private static final String HEADER = "type,fruit,quantity";
    private final FileReader read = new FileReader();

    @Test
    public void throw_runtimeException_Ok() {
        assertThrows(RuntimeException.class, () -> read.readFromFile(NONEXISTENT_FILE_PATH));
    }

    @Test
    public void contains_headerLine_Ok() {
        try {
            List<String> lines = Files.readAllLines(Path.of(STORAGE_FILE_PATH));
            assertEquals(HEADER, lines.get(0), "The file should contain a header");
        } catch (IOException e) {
            throw new FileFoundException("There is no such file", e);
        }
    }

    @Test
    public void read_fromFile_Ok() {
        List<String> expectedInfo;
        try {
            List<String> lines = Files.readAllLines(Path.of(STORAGE_FILE_PATH));
            expectedInfo = lines.size() > 1 ? lines.subList(1, lines.size())
                    : Collections.emptyList();
        } catch (IOException e) {
            throw new FileFoundException("There is no such file", e);
        }

        List<String> actualResult = read.readFromFile(STORAGE_FILE_PATH);
        assertEquals(expectedInfo, actualResult);
    }
}
