package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReadImplTest {
    private final FileReadImpl fileRead = new FileReadImpl();

    @Test
    void readFileContents_readLinesFile_Ok(@TempDir Path testPath) throws IOException {
        Path testFile = testPath.resolve("test.csv");
        List<String> testContent = List.of("b,banana,20", "b,apple,100", "s,banana,100");
        Files.write(testFile, testContent);
        List<String> strings = fileRead.readFileContents(testFile.toString());

        assertEquals(3, strings.size());
        assertEquals("b,banana,20", strings.get(0));
        assertEquals("b,apple,100", strings.get(1));
        assertEquals("s,banana,100", strings.get(2));
    }

    @Test
    void readFileContents_readEmptyFile_Ok(@TempDir Path testPath) throws IOException {
        Path test = testPath.resolve("test.csv");
        List<String> emptyFile = List.of();
        Files.write(test, emptyFile);
        List<String> emptyContent = fileRead.readFileContents(test.toString());
        assertTrue(emptyContent.isEmpty());
    }

    @Test
    void readFileContents_readExceptionIfFileNotFound_notOk() {
        String fileNotFound = "fileNotFound.csv";
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> fileRead.readFileContents(fileNotFound));
        assertTrue(runtimeException.getMessage()
                .contains("Can't get data from file " + fileNotFound));
    }
}

