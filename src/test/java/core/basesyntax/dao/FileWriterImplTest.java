package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String HEADER = "fruit,quantity";
    private static final String FOOTER = ",";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int COUNT_BANANA = 152;
    private static final int COUNT_APPLE = 90;
    private final StringBuilder stringBuilder = new StringBuilder();

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
        stringBuilder.append(HEADER)
                .append(System.lineSeparator())
                .append(BANANA)
                .append(FOOTER)
                .append(COUNT_BANANA)
                .append(System.lineSeparator())
                .append(APPLE)
                .append(FOOTER)
                .append(COUNT_APPLE)
                .append(System.lineSeparator());
    }

    @Test
    void fileWrite_Ok() {
        String report = "Test report content";
        String fileName = "fileTest.txt";
        Path filePath = Path.of(fileName);

        fileWriter.write(report, fileName);

        String lastString;
        try {
            lastString = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
        assertEquals(report, lastString);
    }

    @Test
    void fileRead_NonExistentFile_ShouldThrowException() {
        Path nonExistentFile = Path.of("nonexistent.txt");

        assertThrows(NoSuchFileException.class, () -> {
            Files.readString(nonExistentFile);
        });
    }
}
