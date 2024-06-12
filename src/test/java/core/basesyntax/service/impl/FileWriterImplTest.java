package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String OUTPUT_FILE_PATH = "src/main/resources/finalReport.csv";
    private static final String NON_EXISTING_FILE_PATH = "src/main/someResources/shop_input.csv";
    private static final String SOME_CONTENT = "Hello, World!";
    private static final String EMPTY_CONTENT = "";
    private static FileWriterService fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void test_writeToFile_successful() {
        fileWriter.write(SOME_CONTENT, OUTPUT_FILE_PATH);

        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(OUTPUT_FILE_PATH)));
            assertEquals(SOME_CONTENT, fileContent);
        } catch (IOException e) {
            Assertions.fail("Failed to read...");
        }
    }

    @Test
    public void test_writeToNonExistingFilePath_throwsException() {
        assertThrows(RuntimeException.class, () ->
                fileWriter.write(SOME_CONTENT, NON_EXISTING_FILE_PATH));
    }
}
