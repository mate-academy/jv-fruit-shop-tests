package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static FileReader reader;
    private static Path tempFile;

    @BeforeAll
    static void beforeAll() throws IOException {
        reader = new FileReaderImpl();
        String validCsv = """
                type,fruit,quantity
                b,banana,20
                b,apple,100
                s,banana,100
                p,banana,13
                r,apple,10
                """;
        tempFile = Files.createTempFile("testFile", "csv");
        createCsvFile(tempFile, validCsv);
    }

    @Test
    void read_EmptyCsvFile_Ok() throws IOException {
        Path emptyFile = Files.createTempFile("emptyFile", "csv");
        List<String> emptyOutput = reader.read(emptyFile.toString());
        assertEquals(0, emptyOutput.size());
    }

    @Test
    void read_csvFileWithContent_Ok() {
        List<String> result = reader.read(tempFile.toString());
        assertEquals(6, result.size());
    }

    @Test
    void read_inexistentFile_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reader.read("inexistentFile.csv");
        });
        assertEquals("No file at such path: inexistentFile.csv", exception.getMessage());
    }

    @AfterAll
    static void afterAll() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    private static void createCsvFile(Path filePath, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(filePath.toString()));
        writer.write(content);
        writer.close();
    }
}
