package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReadCsvFileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadCsvFileServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/input.csv";
    private static final String INVALID_FILE_PATH = "not.today";
    private static final String DATA_FOR_TEST_FILE =
            String.format("type,fruit,quantity%nb,banana,20%nb,apple,100");
    private static ReadCsvFileService readCsvFileService;

    @BeforeClass
    public static void beforeClass() {
        readCsvFileService = new ReadCsvFileServiceImpl();
    }

    @Test
    public void readFromFile_validFilePath_ok() {
        writeToTestFile();
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        List<String> actual = readCsvFileService.readFromFile(VALID_FILE_PATH);
        assertEquals(String.format("Should return list -> %s%nbut was -> %s",
                expected, actual), expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_invalidFilePath_notOk() {
        readCsvFileService.readFromFile(INVALID_FILE_PATH);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_pathIsNull_notOk() {
        readCsvFileService.readFromFile(null);
    }

    private void writeToTestFile() {
        try {
            Files.writeString(Path.of(ReadCsvFileServiceImplTest.VALID_FILE_PATH),
                    ReadCsvFileServiceImplTest.DATA_FOR_TEST_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data to file");
        }
    }
}
