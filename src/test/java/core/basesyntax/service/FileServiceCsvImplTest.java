package core.basesyntax.service;

import core.basesyntax.service.impl.FileServiceCsvImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileServiceCsvImplTest {

    private static final String TEST_FILE_NAME = "test_file.csv";
    private static final String TEST_REPORT_CONTENT = "header1,header2\nvalue1,value2\n";
    private FileServiceCsvImpl fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileServiceCsvImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_NAME));
    }

    @Test
    void readFile_existingFile_ok() throws IOException {
        Files.write(Path.of(TEST_FILE_NAME), TEST_REPORT_CONTENT.getBytes());

        List<String> result = fileService.readFile(TEST_FILE_NAME);

        org.junit.jupiter.api.Assertions.assertNotNull(result);
        org.junit.jupiter.api.Assertions.assertEquals(2, result.size());
        org.junit.jupiter.api.Assertions.assertEquals("header1,header2", result.get(0));
        org.junit.jupiter.api.Assertions.assertEquals("value1,value2", result.get(1));
    }

    @Test
    void writeFile_createsFile_ok() throws IOException {
        fileService.writeFile(TEST_REPORT_CONTENT, TEST_FILE_NAME);

        File file = new File(TEST_FILE_NAME);
        org.junit.jupiter.api.Assertions.assertTrue(file.exists());
        String content = Files.readString(Path.of(TEST_FILE_NAME));
        org.junit.jupiter.api.Assertions.assertEquals(TEST_REPORT_CONTENT, content);
    }

    @Test
    void writeFile_errorWritingFile_throwsException() {
        File directory = new File(TEST_FILE_NAME);
        directory.mkdir();

        RuntimeException exception =
                    org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
                        fileService.writeFile(TEST_REPORT_CONTENT, TEST_FILE_NAME);
                    });

        org.junit.jupiter.api.Assertions.assertTrue(exception.getMessage()
                .contains("File not generated"));

        directory.delete();
    }
}
