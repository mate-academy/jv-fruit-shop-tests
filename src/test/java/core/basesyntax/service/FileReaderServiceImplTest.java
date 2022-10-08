package core.basesyntax.service;

import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileReaderServiceImplTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        Files.writeString(Path.of("/home/nata/Java/Projects/jv-fruit-shop-tests/testFile.csv"),
                "b,banana,0" + System.lineSeparator() +
                        "b,apple,10" + System.lineSeparator() +
                        "s,banana,15" + System.lineSeparator() +
                        "p,banana,5" + System.lineSeparator() +
                        "r,apple,20" + System.lineSeparator());
    }

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
