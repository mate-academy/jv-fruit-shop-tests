package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void write_dataWriteCorrectly_Ok(@TempDir Path testPath) throws IOException {
        Path test = testPath.resolve("testFile.csv");
        List<String> testContent = List.of("b,banana,20", "b,apple,100", "s,banana,100");
        fileWriter.write(testContent, test.toString());
        Files.readAllLines(test);
        assertEquals(3, testContent.size());
        assertEquals("b,banana,20", testContent.get(0));
        assertEquals("b,apple,100", testContent.get(1));
        assertEquals("s,banana,100", testContent.get(2));
    }

    @Test
    void write_writeEmptyFile_Ok(@TempDir Path testPath) throws IOException {
        Path testFile = testPath.resolve("test.csv");
        List<String> emptyFile = List.of();
        fileWriter.write(emptyFile, testFile.toString());
        List<String> emptyContent = Files.readAllLines(testFile);
        assertTrue(emptyContent.isEmpty());
    }

    @Test
    void write_writeExceptionIfFileNotFound_notOk() {
        String nameFile = "test";
        List<String> wrongPathFile = List.of(nameFile);
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> fileWriter.write(wrongPathFile, "folder/xxx/output.csv"));
        assertFalse(runtimeException.getMessage().contains("Can't write to file " + nameFile));
    }
}

