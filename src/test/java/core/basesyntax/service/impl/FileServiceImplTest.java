package core.basesyntax.service.impl;

import core.basesyntax.service.FileService;
import java.nio.file.InvalidPathException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static FileService fileService;
    private static final String EMPTY_FILE_NAME = "src/test/resources/fruits1.csv";
    private static final String OK_HEAD_FILE = "src/test/resources/fruits2.csv";
    private static final String FULL_FILE_NAME = "src/test/resources/fruits3.csv";
    private static final String REPORT_TEST = "src/test/resources/report1.csv";
    private static final String HEAD_FILE = "type,fruit,quantity";

    @BeforeClass
    public static void before() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void readHead_OK() {
        List<String> actualResult = fileService.read(OK_HEAD_FILE);
        Assert.assertTrue(" File have wrong columns ", actualResult.stream()
                .limit(1)
                .collect(Collectors.joining())
                .equals(HEAD_FILE));
    }

    @Test
    public void readFromFile_OK() {
        List<String> excepted = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> result = fileService.read(FULL_FILE_NAME);
        Assert.assertEquals(result,excepted);
    }

    @Test
    public void readFromFile_emptyDataBase_Ok() {
        Assert.assertEquals(Collections.emptyList(),
                fileService.read(EMPTY_FILE_NAME));
    }

    @Test(expected = NullPointerException.class)
    public void readFile_RuntimeException() {
        fileService.read(null);
    }

    @Test(expected = InvalidPathException.class)
    public void readFileError() {
        fileService.read(" ");
    }

    @Test(expected = RuntimeException.class)
    public void file_not_exist_NotOK() {
        String test = "FileNotExist";
        fileService.read(test);
    }

    @Test(expected = RuntimeException.class)
    public void writeFileError() {
        fileService.write(REPORT_TEST, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeNUllPathError() {
        fileService.write(null, "some test data");
    }

    @Test
    public void write_OK() {
        String report = "some text";
        fileService.write(REPORT_TEST, report);
    }
}
