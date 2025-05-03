package core.basesyntax.sarvice.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_fileNotExist_notOk() {
        String pathFile = "src/test/java/resources/defunct.csv";
        fileReaderService.readFromFile(pathFile);
    }

    @Test
    public void readFromFile_Ok() {
        String pathFile = "src/test/java/resources/read.csv";
        List<String> actual = fileReaderService.readFromFile(pathFile);
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,100", "s,banana,100");
        assertEquals(expected, actual);
    }
}
