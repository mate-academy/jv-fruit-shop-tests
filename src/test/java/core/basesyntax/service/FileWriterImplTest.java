package core.basesyntax.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    public static final String SOME_DATA = "Some data...";
    public static final String NON_EXISTENT_PATH = "src/main/main/java/java/non_existent_file.csv";
    public static final String CORRECT_PATH = "src/main/java/resources/existent_file.csv";
    private FileWriterImpl fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_ValidData_Ok() throws IOException {
        String expectedContent = String.join(System.lineSeparator(),
                "fruit,quantity",
                "apple,64",
                "banana,45");
        fileWriter.write(expectedContent, CORRECT_PATH);
        String actualContent = Files.readString(Paths.get(CORRECT_PATH)).trim();
        Assertions.assertEquals(expectedContent, actualContent);
    }

    @Test
    void write_NonExistentPath_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileWriter.write(SOME_DATA, NON_EXISTENT_PATH));
    }

    @Test
    void write_NullData_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileWriter.write(null, null));
    }

    @AfterAll
    static void cleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(CORRECT_PATH));

    }
}
