package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String WRITE_FILE = "src/test/resources/writerFile.csv";
    private static final String emptyFile = "src/test/resources/emptyFile.csv";
    private static FileReaderService fileReaderService;
    private static FileWriterService fileWriterService;

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    void fileWrite_OK() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100");
        String contentToWrite = String.join(System.lineSeparator(), expected);

        fileWriterService.writeToFile(WRITE_FILE, contentToWrite);
        File outputFile = new File(WRITE_FILE);
        assertTrue(outputFile.exists(), "Output file should exist");

        List<String> actual = fileReaderService.readFromFile(WRITE_FILE);
        assertEquals(expected, actual, "File content should match expected content");
    }

    @Test
    void fileWrite_CorrectFormat_OK() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100");
        String contentToWrite = String.join(System.lineSeparator(), expected);

        fileWriterService.writeToFile(WRITE_FILE, contentToWrite);

        List<String> actualData = fileReaderService.readFromFile(WRITE_FILE);

        for (String line : actualData) {
            String[] values = line.split(",");
            assertEquals(3, values.length,
                    "Each line should have three values separated by a comma");
        }
        assertEquals(expected.size(), actualData.size(),
                "Number of lines in the file should match the expected");
    }

    @Test
    void writeInvalidData_NotOK() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriterService.writeToFile(WRITE_FILE, null),
                "Method should throw IllegalArgumentException for null data");
    }

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Paths.get(WRITE_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
