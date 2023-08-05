package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;
    private static String emptyFilePath;
    private static String correctFIle;
    private static String fileWithNullOperations;
    private static String fileWithInvalidQuantity;
    private static List<String> correctListOfData;

    @BeforeAll
    static void beforeAll() {
        emptyFilePath = "src/test/resources/testFileReaderEmpty.csv";
        correctFIle = "src/test/resources/testFileCorrectData.csv";
        fileWithNullOperations = "src/test/resources/testFileNullOperation.csv";
        fileWithInvalidQuantity = "src/test/resources/testFileInvalidQuantity.csv";
        fileReaderService = new FileReaderServiceImpl();
        correctListOfData = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    @Test
    void readFromFile_readFromEmptyFile_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.readFromFile(emptyFilePath));
    }

    @Test
    void readFromFile_readCorrectDate_Ok() {
        List<String> actualData = fileReaderService.readFromFile(correctFIle);
        assertEquals(correctListOfData, actualData);
    }

    @Test
    void readFromFile_checkCorrectOperations_Ok() {
        List<String> actualData = fileReaderService.readFromFile(correctFIle);
        boolean actual = actualData.stream().map(str -> str.charAt(0))
                .allMatch(ch -> ch == 'b' || ch == 's' || ch == 'r' || ch == 'p');
        assertTrue(actual);
    }

    @Test
    void readFromFile_nullOperation_NotOk() {
        List<String> actualData = fileReaderService.readFromFile(fileWithNullOperations);
        boolean actual = actualData.stream().map(str -> str.charAt(0))
                .allMatch(ch -> ch == 'b' || ch == 's' || ch == 'r' || ch == 'p');
        assertFalse(actual);
    }

    @Test
    void readFromFile_readCorrectQuantityNumber_Ok() {
        List<String> actualData = fileReaderService.readFromFile(correctFIle);
        boolean actual = actualData.stream()
                .map(str -> str.substring(str.lastIndexOf(",") + 1))
                .map(Integer::parseInt)
                .allMatch(num -> num >= 0);
        assertTrue(actual);
    }

    @Test
    void readFromFile_readInvalidQuantityNumbers_NotOk() {
        List<String> actualData = fileReaderService.readFromFile(fileWithInvalidQuantity);
        boolean actual = actualData.stream()
                .map(str -> str.substring(str.lastIndexOf(",") + 1))
                .map(Integer::parseInt)
                .allMatch(num -> num > 0);
        assertFalse(actual);
    }

    @Test
    void readFromFile_readFromNonExistingFile_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fileReaderService.readFromFile("nonExistingFile.csv"));
    }
}
