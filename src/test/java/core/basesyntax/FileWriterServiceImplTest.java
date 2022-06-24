package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String FILE_FOR_WRITER_PATH = "src/test/resources/fileForWriterTest.csv";
    private static final String TEST_FILE_PATH = "src/test/resources/testFile.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static FileWriterService fileWriterService;
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
        report = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n";
    }

    @Test
    public void writeToFile_goodTest_ok() {
        fileWriterService.writeToFile(FILE_FOR_WRITER_PATH, report);
        List<String> expected = reader(TEST_FILE_PATH);
        List<String> actual = reader(FILE_FOR_WRITER_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_emptyReport_ok() {
        fileWriterService.writeToFile(FILE_FOR_WRITER_PATH, "");
        List<String> expected = reader(EMPTY_FILE_PATH);
        List<String> actual = reader(FILE_FOR_WRITER_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_notExisting_notOk() {
        fileWriterService.writeToFile("", report);
    }

    private List<String> reader(String filePath) {
        try {
            List<String> fileList = Files.readAllLines(Path.of(filePath));
            return fileList;
        } catch (IOException e) {
            throw new RuntimeException("File is not found" + filePath);
        }
    }
}
