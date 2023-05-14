package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.NoSuchFileEx;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static CsvFileReaderServiceImpl readerService;
    private static Path validPath;
    private static Path invalidPath;
    private static List<String> expectedData;

    @BeforeClass
    public static void beforeClass() {
        validPath = Path.of("src/test/resources/CsvFileReaderServiceImplTestValid.csv");
        invalidPath = Path.of("CsvFileReaderServiceImplTestValid.csv");
        readerService = new CsvFileReaderServiceImpl();
        expectedData = List.of("line1, 1, 2, 3",
                "line2, 1, 2, 3",
                "line3, 1, 2, 3",
                "line4, 1, 2, 3");
    }

    @Test
    public void readFromFile_validPath_ok() throws IOException {
        Files.write(validPath, expectedData);
        List<String> actualData = readerService.readFromFile(validPath);
        assertEquals(expectedData.size(), actualData.size());
        for (int i = 0; i < expectedData.size(); i++) {
            assertEquals(expectedData.get(i), actualData.get(i));
        }
    }

    @Test
    public void readFromFile_validPathEmptyData_ok() throws IOException {
        Files.write(validPath, Collections.EMPTY_LIST);
        List<String> readedEmptyData = readerService.readFromFile(validPath);
        assertEquals(0, readedEmptyData.size());
    }

    @Test(expected = NoSuchFileEx.class)
    public void readFromFile_invalidPath_notOk() {
        readerService.readFromFile(invalidPath);
    }

    @Test(expected = NoSuchFileEx.class)
    public void readFromFile_nullPath_notOk() {
        readerService.readFromFile(null);
    }
}
