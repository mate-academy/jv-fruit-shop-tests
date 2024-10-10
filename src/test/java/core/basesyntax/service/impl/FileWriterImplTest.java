package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriterImpl fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void writeFile_validContentAndFilePath_ok() throws IOException {
        String validFilePath = "src/test/resources/output.csv";
        String content = "Valid content";
        fileWriter.writeFile(validFilePath, content);
        List<String> fileContent = Files.readAllLines(Paths.get(validFilePath));
        assertEquals(List.of("Valid content"), fileContent);
    }

    @Test
    void writeFile_invalidFilePath_notOk() {
        String invalidFilePath = "src/test/resources/invalidDir/output.csv";
        String content = "Sample content";
        assertThrows(RuntimeException.class, () -> fileWriter.writeFile(invalidFilePath, content));
    }
}
