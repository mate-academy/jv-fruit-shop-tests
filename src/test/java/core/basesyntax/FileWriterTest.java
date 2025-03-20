package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import core.basesyntax.serviceimpl.FileWriterImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validFile_correct() throws Exception {
        final String testPath = "src/test/java/resources/testOutput.csv";
        String content = "fruit,quantity" + System.lineSeparator()
                + "apple,30" + System.lineSeparator()
                + "pineapple,40";

        fileWriter.write(content, testPath);

        Path path = Paths.get(testPath);
        String actual = Files.readString(path).trim();

        assertEquals(content, actual);
    }

    @Test
    void write_invalidFile_throwsException() {
        final String invalidFilePath = "src/test/java/resources/";
        RuntimeException thrown = assertThrows(
                RuntimeException.class, () -> fileWriter.write("banana, 20", invalidFilePath));
        assertTrue(thrown.getMessage().contains("Can't write file to path: " + invalidFilePath));
    }
}
