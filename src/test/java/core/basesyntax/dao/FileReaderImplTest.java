package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void readFile_validFile_success() {
        Path testFile;
        try {
            testFile = Path.of(getClass().getClassLoader()
                    .getResource("testFinalReport.csv").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        List<String> result = fileReader.readFile(testFile.toString());

        assertEquals(2, result.size());
        assertEquals("apple,10", result.get(0));
        assertEquals("banana,20", result.get(1));
    }

    @Test
    void readFile_emptyFile_success() {
        try {
            Path emptyFile = Files.createTempFile("emptyTestFile", ".csv");
            Files.write(emptyFile, List.of());

            List<String> result = fileReader.readFile(emptyFile.toString());
            assertTrue(result.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании временного файла", e);
        }
    }

    @Test
    void readFile_fileWithEmptyLines_success() {
        try {
            Path fileWithEmptyLines = Files.createTempFile("emptyLinesTestFile", ".csv");
            Files.write(fileWithEmptyLines, List.of("fruit,value","apple,10", "", "banana,20"));

            List<String> result = fileReader.readFile(fileWithEmptyLines.toString());
            assertEquals(2, result.size());
            assertEquals("apple,10", result.get(0));
            assertEquals("banana,20", result.get(1));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании временного файла", e);
        }
    }

    @Test
    void readFile_fileWithWhitespaceLines_success() {
        try {
            Path fileWithWhitespaceLines = Files.createTempFile("whitespaceTestFile", ".csv");
            Files.write(fileWithWhitespaceLines, List.of("fruit,value","apple,10", "  ",
                    "banana,20"));

            List<String> result = fileReader.readFile(fileWithWhitespaceLines.toString());
            assertEquals(2, result.size());
            assertEquals("apple,10", result.get(0));
            assertEquals("banana,20", result.get(1));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании временного файла", e);
        }
    }

    @Test
    void readFile_fileWithSpecialCharacters_success() {
        try {
            Path specialCharsFile = Files.createTempFile("specialCharsTestFile", ".csv");
            Files.write(specialCharsFile, List.of("fruit,value","яблуко,10", "банан,20",
                    "апельсин,30!@#$%^&*()"));
            List<String> result = fileReader.readFile(specialCharsFile.toString());
            assertEquals(3, result.size());
            assertEquals("яблуко,10", result.get(0));
            assertEquals("банан,20", result.get(1));
            assertEquals("апельсин,30!@#$%^&*()", result.get(2));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании временного файла", e);
        }
    }

    @Test
    void readFile_largeFile_success() {
        try {
            Path largeFile = Files.createTempFile("largeTestFile", ".csv");
            List<String> largeContent = List.of("Item,Value","item1,100", "item2,200", "item3,300",
                    "item4,400", "item5,500");
            Files.write(largeFile, largeContent);
            List<String> result = fileReader.readFile(largeFile.toString());
            assertEquals(5, result.size());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании временного файла", e);
        }
    }

    @Test
    void readFile_nonExistingFile_throwsException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileReader.readFile("non_existing_file.csv")
        );
        assertTrue(exception.getMessage().contains("Error while reading file")
                || exception.getMessage().contains("Resource not found"));
    }

    @Test
    void readFile_nullOrEmptyFileName_throwsException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileReader.readFile("")
        );
        assertTrue(exception.getMessage().contains("Error while reading file")
                || exception.getMessage().contains("Resource not found"));
    }
}
