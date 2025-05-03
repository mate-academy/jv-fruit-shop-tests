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
    private static final String WRITE_OK = "write_ok.csv";
    private static final String TEST_CONTENT = "test content";
    private static final String NON_EXISTENT_DIRECTORY =
            "non_existent_dir/write_fail.csv";
    private static final String WRITE_EMPTY = "write_empty.csv";
    private static final String EMPTY_STRING = "";
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
        String fileName = FILES_ROOT + WRITE_OK;

        fileWriter.writeToFile(fileName, TEST_CONTENT);

        File file = new File(fileName);
        Assertions.assertTrue(file.exists(), "File should be created");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            Assertions.assertEquals(TEST_CONTENT,
                    line, "File content should match the written string");
        } catch (IOException e) {
            Assertions.fail("An exception occurred while reading the file: " + e.getMessage());
        }

        Assertions.assertTrue(file.delete(), "Test file should be deleted after the test");
    }

    @Test
    void write_nonExistenDirectory_throwsException() {
        String invalidPath = FILES_ROOT + NON_EXISTENT_DIRECTORY;

        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(invalidPath, TEST_CONTENT),
                "Writing to a non-existent directory should throw an exception");
    }

    @Test
    void write_emptyContent_createsEmptyFile() {
        String fileName = FILES_ROOT + WRITE_EMPTY;

        fileWriter.writeToFile(fileName, EMPTY_STRING);

        File file = new File(fileName);
        Assertions.assertTrue(file.exists(), "File should be created");
        Assertions.assertEquals(0, file.length(), "File should be empty");
        Assertions.assertTrue(file.delete(), "Test file should be deleted after the test");
    }
}
