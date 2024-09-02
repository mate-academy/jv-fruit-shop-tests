package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String WRITE_FILE_PATH
            = "src/main/java/resources/reportToWrite.csv";
    private static final String INVALID_FILE_PATH
            = "invalid/reportToWrite.csv";
    private static final String DATA = "banana,152" + "\napple,90";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void writeToFile_correctFile_ok() throws IOException {
        fileWriter.writeToFile(DATA, WRITE_FILE_PATH);
        List<String> actual = Files.readAllLines(Paths.get(WRITE_FILE_PATH));
        List<String> expected = List.of("banana,152", "apple,90");
        assertEquals(expected, actual);
    }

    @Test
    void writeToFile_invalidFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(DATA, INVALID_FILE_PATH));
    }
}
