package core.basesyntax.service;

import core.basesyntax.service.impl.FileServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static FileService fileService;

    private static final String EMPTY_FILE_NAME = "src/test/resources/fruits1.csv";
    private static final String OK_HEAD_FILE = "src/test/resources/fruits2.csv";
    private static final String REPORT_TEST = "src/test/resources/report1.csv";
    private static final String HEAD_FILE = "type,fruit,quantity";

    @BeforeClass
    public static void before() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void readFromEmptyFile() {
        List<String> actualResult = fileService.read(EMPTY_FILE_NAME);
        Assert.assertFalse("File is empty",
                actualResult.isEmpty());
    }

    @Test
    public void readHead_OK() {
        List<String> actualResult = fileService.read(OK_HEAD_FILE);
        Assert.assertTrue(" File have wrong columns ", actualResult.stream()
                .limit(1)
                .collect(Collectors.joining())
                .equals(HEAD_FILE));
    }

    @Test(expected = RuntimeException.class)
    public void readFileError() {
        fileService.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void writeFileError() {
        fileService.write(REPORT_TEST, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeNUllPathError() {
        fileService.write(null, "111");
    }
}
