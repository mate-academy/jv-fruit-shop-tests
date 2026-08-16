package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterTest {

    private final FileWriter fileWriter = new FileWriter();

    @Test
    void writeToFile_validFile_returnsContent() throws Exception {
        Path file = Files.createTempFile("test", ".csv");

        try {
            String data = "fruit,quantity\nbanana,100";

            fileWriter.writeToFile(file.toString(), data);

            String result = Files.readString(file);

            assertEquals(data, result);
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    void writeToFile_invalidPath_throwsException() {
        String filePath = "non-existing-folder/test.csv";

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileWriter.writeToFile(filePath, "banana,100")
        );

        assertEquals(
                "Could not write to file: " + filePath,
                exception.getMessage()
        );
    }
}
