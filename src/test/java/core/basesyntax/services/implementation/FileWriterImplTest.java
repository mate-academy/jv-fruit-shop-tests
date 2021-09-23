package core.basesyntax.services.implementation;

import core.basesyntax.services.FileReaderService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String PATH_TO_TEST_FILE = "src/main/resources/testFile.csv";
    private static final String PATH_TO_REPORT_FILE = "src/main/resources/report.csv";
    private static List<String> fruitInfo;
    private static FileWriterImpl fileWriter;
    private static FileReaderService fileReaderService;
    private static String data;

    @Before
    public void setUp() throws Exception {
        fileWriter = new FileWriterImpl();
        fileReaderService = new FileReaderImpl();
        fruitInfo = Files.readAllLines(Path.of(PATH_TO_REPORT_FILE));
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : fruitInfo) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        data = stringBuilder.toString().trim();
    }

    @Test
    public void writeDataToFile_compareFiles_ok() {
        List<String> expectedList = fruitInfo;
        List<String> actualList = fileReaderService.read(PATH_TO_REPORT_FILE);
        Assert.assertEquals("Test failed. The files are different.", expectedList, actualList);
    }

    @Test
    public void writeDataToFile_validData_ok() {
        fileWriter.write(data, PATH_TO_TEST_FILE);
        List<String> expectedList = fileReaderService.read(PATH_TO_REPORT_FILE);
        List<String> actualList = fileReaderService.read(PATH_TO_TEST_FILE);
        Assert.assertEquals("Test failed. The files are different.", expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void writeNullDataToFile_NotValidData_Not_Ok() {
        fileWriter.write(null, PATH_TO_TEST_FILE);
    }
}
