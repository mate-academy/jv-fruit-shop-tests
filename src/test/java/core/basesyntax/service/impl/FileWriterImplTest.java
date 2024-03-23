package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_FILE_PATH = "src/test/resources/reportTest.csv";
    private static final String UNWRITABLE_FILE_PATH = "/root/unwritable/file.txt";
    private static final FileWriter fileWriter = new FileWriterImpl();

    @BeforeEach
    void setUp() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        if (!testFile.exists()) {
            testFile.createNewFile();
        }
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }        
    }

    @Test
    void writeToFile_createNewFileWithData_isOk() throws IOException {
        List<String> expected = Arrays.asList(
                new String("fruit,quantity"),
                new String("banana,10"),
                new String("apple,5"));
        fileWriter.writeToFile(expected, new File(TEST_FILE_PATH));
        List<String> actual = Files.readAllLines(Path.of(TEST_FILE_PATH));
        assertEquals(expected, actual);

    }

    @Test
    void writeToFile_unwritableFile_notOk() {
        List<String> data = Arrays.asList("fruit,quantity", "banana,10", "apple,5");
        File unwritableFile = new File(UNWRITABLE_FILE_PATH);
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(data, unwritableFile));
    }
}
