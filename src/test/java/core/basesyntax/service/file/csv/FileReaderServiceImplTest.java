package core.basesyntax.service.file.csv;

import core.basesyntax.service.csvfileservice.FileReaderService;
import core.basesyntax.service.csvfileservice.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String FILE_TO_READ = "src/test/resources/input_test_data.csv";
    private static final String EMPTY_FILE_TO_READ = "src/test/resources/input_test_empty_data.csv";
    private static final String NO_FILE_TO_READ = "";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_fileNotExisted_notOk() {
        fileReaderService.readFromFile(NO_FILE_TO_READ);
    }

    @Test
    public void readFromFile_fileExisted_Ok() {
        List<String> expectedListOfLines = List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,80", "s,banana,100", "r,apple,10");
        List<String> actualListOfLines = fileReaderService.readFromFile(FILE_TO_READ);
        Assert.assertEquals(expectedListOfLines, actualListOfLines);
    }

    @Test
    public void readFromFile_fileEmpty_Ok() {
        List<String> expectedListOfLines = new ArrayList<>();
        List<String> actualListOfLines = fileReaderService.readFromFile(EMPTY_FILE_TO_READ);
        Assert.assertEquals(expectedListOfLines, actualListOfLines);
    }
}
