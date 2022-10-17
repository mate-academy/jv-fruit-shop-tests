package core.basesyntax.fileservice;

import core.basesyntax.fileservice.impl.FileReaderServiceImpl;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class FileReaderServiceImplTest {
    public static final String ACTIVITIES_FILE_PATH = "src/test/java/resources/activities.csv";
    public static final String EMPTY_FILE_PATH = "src/test/java/resources/empty_file.csv";
    private FileReaderService fileReaderService;

    @Before
    public void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void fileReaderService_FileWithActivities_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50");
        List<String> actual = fileReaderService.readFromFile(ACTIVITIES_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readService_EmptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = fileReaderService.readFromFile(EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readService_NotExistedFile_NotOk() {
        fileReaderService.readFromFile("");
    }
}
