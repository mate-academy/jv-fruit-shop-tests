package core.basesyntax.service.fileservice;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static final String EMPTY_DATA = "";
    private static final String VALID_DATA = "b,banana,20\nb,apple,100\ns,banana,100";
    private static final String VALID_FILEPATH = "src/test/resources/valid.csv";
    private static final String INVALID_FILEPATH = "serece/test/invalid.csv";
    private static final String WRITER_RESULT_FILEPATH = "src/test/resources/writerResult.csv";
    private static final String EXCEPTION_MESSAGE = "Can't write data to file";

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void fileWriterService_dataIsNull_notOk() {
        fileWriterService.write(null, WRITER_RESULT_FILEPATH);
    }

    @Test(expected = RuntimeException.class)
    public void fileWriterService_filePathIsNull_notOk() {
        fileWriterService.write(VALID_DATA, null);
    }

    @Test(expected = RuntimeException.class)
    public void fileWriterService_invalidFilePath_notOk() {
        fileWriterService.write(VALID_DATA, INVALID_FILEPATH);
    }

    @Test
    public void fileWriterService_dataIsEmpty_isOk() {
        fileWriterService.write(EMPTY_DATA, WRITER_RESULT_FILEPATH);
        List<String> expected = new ArrayList<>();
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(WRITER_RESULT_FILEPATH));
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void fileWriterService_writeFile_isOk() {
        fileWriterService.write(VALID_DATA, WRITER_RESULT_FILEPATH);
        List<String> expected;
        List<String> actual;
        try {
            expected = Files.readAllLines(Path.of(WRITER_RESULT_FILEPATH));
            actual = Files.readAllLines(Path.of(VALID_FILEPATH));
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
        assertEquals(expected, actual);
    }
}
