package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

public class FileReaderServiceImplTest {
    @Test
    public void read_validFilePath_ok() {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        String validFilePath = "/home/nata/Java/Projects/jv-fruit-shop-tests/testFile.csv";
        List<String> expectedResultList = List.of(
                "b,banana,0",
                "b,apple,10",
                "s,banana,15",
                "p,banana,5",
                "r,apple,20");
        assertEquals(expectedResultList, fileReaderService.read(validFilePath));
    }

    @Test(expected = RuntimeException.class)
    public void read_invalidFilePath_notOk() {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        String invalidFilePath = "/home/nata/Java/Projects/jv-fruit-shop-tests/testFiles.csv";
        fileReaderService.read(invalidFilePath);
    }
}
