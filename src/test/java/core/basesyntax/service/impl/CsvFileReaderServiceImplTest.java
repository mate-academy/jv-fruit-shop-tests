package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.WrongPathException;
import core.basesyntax.service.CsvFileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/input.csv";
    private static final String INVALID_FILE_PATH = "not.today";
    private static final String DATA_FOR_TEST_FILE =
            String.format("type,fruit,quantity%nb,banana,20%nb,apple,100");
    private static CsvFileReaderService csvFileReaderService;

    @BeforeClass
    public static void init() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test
    public void readFromFile_validFilePath_ok() {
        writeToTestFile();
        List<String> expected = new ArrayList<>(List.of("b,banana,20", "b,apple,100"));
        List<String> actual = csvFileReaderService.readFromFile(VALID_FILE_PATH);
        assertEquals(String.format("Should return list -> %s%nbut was -> %s",
                expected, actual), expected, actual);
    }

    @Test (expected = WrongPathException.class)
    public void readFromFile_invalidFilePath_notOk() {
        csvFileReaderService.readFromFile(INVALID_FILE_PATH);
    }

    @Test (expected = WrongPathException.class)
    public void readFromFile_pathIsNull_notOk() {
        csvFileReaderService.readFromFile(null);
    }

    private void writeToTestFile() {
        try {
            Files.writeString(Path.of(CsvFileReaderServiceImplTest.VALID_FILE_PATH),
                    CsvFileReaderServiceImplTest.DATA_FOR_TEST_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data to file");
        }
    }
}
