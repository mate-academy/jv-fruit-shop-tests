package core.basesyntax.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String FILES_ROOT =
            "src/test/java/resources/csv_writer/";
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        try {
            Files.createDirectories(Paths.get(FILES_ROOT));
        } catch (IOException e) {
            throw new RuntimeException("An exception occurred while creating directories", e);
        }
        fileWriter = new core.basesyntax.serviceimpl.FileWriterImpl();
    }

    @Test
    void write_validContent_ok() {
        String fileName = FILES_ROOT + "write_ok.csv";
        String content = "test content";

        fileWriter.writeToFile(fileName, content);

        File file = new File(fileName);
        Assertions.assertTrue(file.exists(), "File should be created");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            Assertions.assertEquals(content, line, "File content should match the written string");
        } catch (IOException e) {
            Assertions.fail("An exception occurred while reading the file: " + e.getMessage());
        }

        Assertions.assertTrue(file.delete(), "Test file should be deleted after the test");
    }

    @Test
    void write_nonExistenDirectory_throwsException() {
        String invalidPath = FILES_ROOT + "non_existent_dir/write_fail.csv";
        String content = "test content";

        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(invalidPath, content),
                "Writing to a non-existent directory should throw an exception");
    }

    @Test
    void write_emptyContent_createsEmptyFile() {
        String fileName = FILES_ROOT + "write_empty.csv";
        String content = "";

        fileWriter.writeToFile(fileName, content);

        File file = new File(fileName);
        Assertions.assertTrue(file.exists(), "File should be created");
        Assertions.assertEquals(0, file.length(), "File should be empty");
        Assertions.assertTrue(file.delete(), "Test file should be deleted after the test");
    }
}
