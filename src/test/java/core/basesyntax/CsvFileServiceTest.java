package core.basesyntax;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.FileReaderService;
import service.FileWriterService;
import service.impl.CsvFileReaderServiceImpl;
import service.impl.FileWriterServiceImpl;

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
        assertEquals("strings from first row of the csv file and obtained"
                + " result aren't the same", STENCIL[0], list.get(0));
        assertEquals("strings from second row of the csv file and obtained"
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
        assertArrayEquals(message, fileReadService.readFile(FILE_PATH_TO).get(0), STENCIL[0]);
        assertArrayEquals(message, fileReadService.readFile(FILE_PATH_TO).get(1), STENCIL[1]);
    }

    @AfterClass
    public static void tearDown() {
        File destroyReadingFile = new File("src/test/resources/readingTest.csv");
        File destroyWritingFile = new File("src/test/resources/writingTest.csv");
        destroyReadingFile.delete();
        destroyWritingFile.delete();
    }
}
