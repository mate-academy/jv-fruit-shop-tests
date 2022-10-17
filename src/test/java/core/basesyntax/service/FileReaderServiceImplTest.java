package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void read_validFilePath_ok() {
        String validFilePath =
                this.getClass().getClassLoader().getResource("FileReaderTestFile.csv").getPath();
        List<String> expectedResultList = List.of(
                "b,banana,0",
                "b,apple,10",
                "s,banana,15",
                "p,banana,5",
                "r,apple,20");
        List<String> actualResultList = fileReaderService.read(validFilePath);
        assertEquals(expectedResultList, actualResultList);
    }

    @Test(expected = RuntimeException.class)
    public void read_invalidFilePath_notOk() {
        String invalidFilePath =
                "/src/test/resources/testFiles.csv";
        fileReaderService.read(invalidFilePath);
    }

    @Test(expected = RuntimeException.class)
    public void read_correctExceptionMessage_ok() {
        String invalidFilePath =
                "/home/nata/Java/Projects/jv-fruit-shop-tests/src/test/resources/testFiles.csv";
        fileReaderService.read(invalidFilePath);
    }
}
