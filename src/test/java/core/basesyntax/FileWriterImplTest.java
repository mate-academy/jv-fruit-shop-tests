package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import service.FileWriter;
import service.FileWriterImpl;

class FileWriterImplTest {
    private static final String TEST_RESULT_FILE = "src/test/java/resource/test_output.csv";

    private final FileWriter fileWriter = new FileWriterImpl();

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Path.of(TEST_RESULT_FILE));
        } catch (IOException e) {
            System.out.println("We can`t create file.");
        }
    }

    @Test
    void write_ContentToFile_Ok() {
        String content = "fruit,quantity\nbanana,100";

        fileWriter.write(content, TEST_RESULT_FILE);

        try {
            String actualContent = Files.readString(Path.of(TEST_RESULT_FILE));
            assertEquals(content, actualContent);
        } catch (IOException e) {
            fail("File should exist and be readable: " + e.getMessage());
        }
    }
}
