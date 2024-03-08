package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static final String existingFile = "src/test/resources/data.csv";
    private static final String nonexistentFileName = "src/test/resources/nonexistent.csv";
    private static final String emptyFile = "src/test/resources/emptyFile.csv";
    private static FileReaderService fileReaderService;

    @BeforeAll
    public static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void file_Read_OK() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100");
        List<String> actual = fileReaderService.readFromFile(existingFile);
        assertEquals(expected, actual, "File content was read wrong!");
    }

    @Test
    public void readFromFile_fileNotFound_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile(nonexistentFileName),
                "RuntimeException should be thrown if file is not found!");
    }

    @Test
    public void readFromFile_emptyFile_NotOK() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReaderService.readFromFile(emptyFile);
        assertEquals(expected, actual, "Empty file was read wrong!");
    }
}
