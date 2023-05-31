package core.basesyntax.services.imp;

import core.basesyntax.services.FileReadService;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class ReaderServiceImplTest {
    private final FileReadService fileReadService = new ReaderServiceImpl();

    @Test(expected = RuntimeException.class)
    public void readFromFile_fileIsNull_notOk() {
        String test = null;
        fileReadService.readFromFile(test);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notExistFile_notOk() {
        String test = "FileNotExist";
        fileReadService.readFromFile(test);
    }

    @Test
    public void read_correctFile_OK() {
        String expected = "Glory to Ukraine!";
        String filePath = "src/test/resources/ReadTestFile.csv";
        String actual = fileReadService.readFromFile(filePath).stream()
                .collect(Collectors.joining());
        Assert.assertEquals(expected,actual);
    }

}
