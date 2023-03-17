package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.InvalidPathException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.services.CsvFileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static CsvFileWriterService writerService;
    private static Path validPath;
    private static Path invalidPath;
    private static List<String> testData;

    @BeforeClass
    public static void beforeClass() {
        writerService = new CsvFileWriterServiceImpl();
        validPath = Path.of("src/test/resources/CsvFileWriterServiceImplTestValid.csv");
        invalidPath = Path.of("invalidDirectory/src/test/resources"
                + "/CsvFileWriterServiceImplTestValid.csv");
        testData = List.of("line1, 1, 2, 3",
                "line2, 1, 2, 3",
                "",
                "");
    }

    @Test
    public void writeToCsvFile_validPath_ok() throws IOException {
        writerService.writeToCsvFile(testData, validPath);
        List<String> readedData = Files.readAllLines(validPath);
        assertEquals(testData.size(), readedData.size());
        assertEquals(testData.size(),
                IntStream.range(0, testData.size())
                        .filter(i -> readedData.get(i).equals(testData.get(i))).count());
        writerService.writeToCsvFile(Collections.EMPTY_LIST, validPath);
        List<String> readedEmptyData = Files.readAllLines(validPath);
        assertEquals(0, readedEmptyData.size());
    }

    @Test(expected = InvalidPathException.class)
    public void writeToCsvFile_invalidPath_notOk() {
        writerService.writeToCsvFile(testData, invalidPath);
    }

    @Test(expected = InvalidPathException.class)
    public void writeToCsvFile_pathNull_notOk() {
        writerService.writeToCsvFile(testData, null);
    }

    @Test(expected = NullDataException.class)
    public void writeToCsvFile_nullData_notOk() {
        writerService.writeToCsvFile(null, validPath);
    }
}
