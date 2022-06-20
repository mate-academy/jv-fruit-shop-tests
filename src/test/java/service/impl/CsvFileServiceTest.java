package service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.FileReaderService;
import service.FileWriterService;

public class CsvFileServiceTest {
    private static FileReaderService fileReadService;
    private static FileWriterService fileWriterService;
    private static final String[][] STENCIL = {{"JavaCore", "JavaSOLID", "JavaFruitShopTests"},
            {"test", "read-writeCsvFile", "2022"}};
    private static final String FILE_PATH_FROM = "src/test/resources/readingTest.csv";
    private static final String FILE_PATH_TO = "src/test/resources/writingTest.csv";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initial() {
        fileReadService = new CsvFileReaderServiceImpl();
        fileWriterService = new FileWriterServiceImpl();
        fileWriterService.writeFile(FILE_PATH_FROM,
                Arrays.stream(STENCIL).collect(Collectors.toUnmodifiableList()));
    }

    @Test
    public void readFile_readFromCsvFile_ok() {
        List<String[]> list = fileReadService.readFile(FILE_PATH_FROM);
        assertArrayEquals("strings from first row of the csv file and obtained"
                + " result aren't the same", STENCIL[0], list.get(0));
        assertArrayEquals("strings from second row of the csv file and obtained"
                + " result aren't the same", STENCIL[1], list.get(1));
        assertEquals("expected row's quantity and quantity of rows from"
                + " stencil aren't the same", STENCIL.length, list.size());
    }

    @Test
    public void readFile_throwsNullPointerExceptionIfPathIsNull_ok() {
        exception.expect(NullPointerException.class);
        fileReadService.readFile(null);
    }

    @Test
    public void readFile_throwsRuntimeExceptionIfPathIsNotCorrect_ok() {
        String filePathError = "mistaken/path/file.csv";
        exception.expect(RuntimeException.class);
        exception.expectMessage("Cannot read file");
        fileReadService.readFile(filePathError);
    }

    @Test
    public void writeFile_allDataHasBeenWritten_ok() {
        String message = "original data and data from file are differed";
        fileWriterService.writeFile(FILE_PATH_TO, Arrays.asList(STENCIL));
        assertArrayEquals(message, STENCIL[0], fileReadService.readFile(FILE_PATH_TO).get(0));
        assertArrayEquals(message, STENCIL[1], fileReadService.readFile(FILE_PATH_TO).get(1));
    }
}
