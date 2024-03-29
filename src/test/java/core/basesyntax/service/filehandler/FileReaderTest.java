package core.basesyntax.service.filehandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private static final String STORAGE_FILE_PATH = "src/test/java/resources/StorageInfo.csv";
    private static final String NONEXISTENT_FILE_PATH = "src/test/java/resources/TestFile.csv";
    private static final String HEADER = "type,fruit,quantity";
    private static final int SUB_LIST_START_INDEX = 1;
    private static final int HEADER_INDEX = 0;
    private final FileReader read = new FileReader();

    @Test
    public void readFromFile_nonExistentFile_throwsRuntimeException_notOk() {
        assertThrows(RuntimeException.class, () -> read.readFromFile(NONEXISTENT_FILE_PATH));
    }

    @Test
    public void readFromFile_headerLine_Ok() throws IOException {
        List<String> lines = Files.readAllLines(Path.of(STORAGE_FILE_PATH));
        assertEquals(HEADER, lines.get(HEADER_INDEX), "The file should contain a header");
    }

    @Test
    public void readFromFile_returnsFileContents_Ok() throws IOException {
        List<String> expectedInfo;
        List<String> lines = Files.readAllLines(Path.of(STORAGE_FILE_PATH));
        expectedInfo = lines.subList(SUB_LIST_START_INDEX, lines.size());

        List<String> actualResult = read.readFromFile(STORAGE_FILE_PATH);
        assertEquals(expectedInfo, actualResult);
    }
}
