package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReaderServiceImplTest {
    private static final String CORRECT_PATH_TO_READ = "testFile.csv";
    private static final String INCORRECT_PATH_TO_READ = "nonExistentFile.csv";
    private final ReaderService fileReader = new ReaderServiceImpl();

    @TempDir
    private Path tempDir;

    @Test
    void read_validFile_ok() throws IOException {
        Path filePath = tempDir.resolve(CORRECT_PATH_TO_READ);
        List<String> expectedContent = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,1",
                "b,apple,1");
        Files.write(filePath, expectedContent);
        assertEquals(expectedContent, fileReader.read(filePath.toString()));
    }

    @Test
    void read_nonExistentFile_notOk() {
        Path nonExistentPath = tempDir.resolve(INCORRECT_PATH_TO_READ);
        assertThrows(RuntimeException.class, () ->
                fileReader.read(nonExistentPath.toString()));
    }
}
