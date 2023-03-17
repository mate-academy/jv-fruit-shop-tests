package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.NoSuchFileEx;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static CsvFileReaderServiceImpl readerService;
    private static Path validPath;
    private static Path invalidPath;
    private static List<String> testData;

    @BeforeClass
    public static void beforeClass() {
        validPath = Path.of("src/test/resources/CsvFileReaderServiceImplTestValid.csv");
        invalidPath = Path.of("CsvFileReaderServiceImplTestValid.csv");
        readerService = new CsvFileReaderServiceImpl();
        testData = List.of("line1, 1, 2, 3",
                "line2, 1, 2, 3",
                "line3, 1, 2, 3",
                "line4, 1, 2, 3");
    }

    @Test
    public void readFromFile_validPath_ok() throws IOException {
        Files.write(validPath, testData);
        List<String> readedData = readerService.readFromFile(validPath);
        assertEquals(testData.size(), readedData.size());
        assertEquals(testData.size(),
                IntStream.range(0, testData.size())
                        .filter(i -> readedData.get(i).equals(testData.get(i))).count());
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
