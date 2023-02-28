package core.basesyntax.impl;

import core.basesyntax.service.FileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static FileService fileService;
    private static final String CORRECT_FILE_PATH = "src/test/resources/inputdata.csv";
    private static final String WRONG_FILE_PATH = "src/java/main/resources/inputdata.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyfile.csv";
    private static final String CORRECT_FILE_PATH_TO_WRITE = "src/test/resources/outputdata.csv";
    private static final String TEST_STRING_FOR_WRITE = "some text";
    private static final String FILE_WITH_WRONG_FORMAT = "src/test/resources/inputdata.mp4";

    @BeforeClass
    public static void before() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void get_ValidPath_Ok() {
        fileService.read(CORRECT_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void get_InvalidPath_NotOk() {
        fileService.read(WRONG_FILE_PATH);
    }

    @Test
    public void get_PathToTheEmptyFile_NotOk() {
        fileService.read(EMPTY_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void get_InvalidFormatOfFile_NotOk() {
        List<String> expected = fileService.read(CORRECT_FILE_PATH);
        List<String> actual = fileService.read(FILE_WITH_WRONG_FORMAT);
        Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void get_ValidPathToWrite_Ok() {
        fileService.write(CORRECT_FILE_PATH_TO_WRITE, TEST_STRING_FOR_WRITE);
        String actual;
        try {
            actual = Files.readString(Path.of(CORRECT_FILE_PATH_TO_WRITE));
        } catch (IOException e) {
            throw new RuntimeException("Test was failed" + e);
        }
        Assert.assertEquals(TEST_STRING_FOR_WRITE, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_InvalidPathToWrite_NotOk() {
        fileService.write(WRONG_FILE_PATH, TEST_STRING_FOR_WRITE);
    }

    @Test
    public void get_CorrectWayToWrite_Ok() {
        fileService.write(CORRECT_FILE_PATH_TO_WRITE, TEST_STRING_FOR_WRITE);
    }

    @Test(expected = NullPointerException.class)
    public void get_WriteNullPath_NotOk() {
        fileService.write(null, TEST_STRING_FOR_WRITE);
    }

    @Test(expected = RuntimeException.class)
    public void get_ToWriteNullFile_NotOk() {
        fileService.write(CORRECT_FILE_PATH_TO_WRITE, null);
    }
}
