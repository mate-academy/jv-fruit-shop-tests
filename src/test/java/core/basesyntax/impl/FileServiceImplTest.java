package core.basesyntax.impl;

import core.basesyntax.service.FileService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static FileService fileService;
    private static final String CORRECT_FILE_PATH = "src/test/resources/inputdata.csv";
    private static final String WRONG_FILE_PATH = "src/java/main/resources/inputdata.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyfile.csv";
    private static final String CORRECT_FILE_PATH_TO_WRITE = "src/test/resources/outputdata.csv";
    private static final String TEST_STRING_FOR_WRITE = "some text";

    @BeforeClass
    public static void before() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void validPath_Ok() {
        fileService.read(CORRECT_FILE_PATH);
    }

    @Test (expected = RuntimeException.class)
    public void noValidPath_NotOk() {
        fileService.read(WRONG_FILE_PATH);
    }

    @Test
    public void pathToTheEmptyFile_NotOk() {
        fileService.read(EMPTY_FILE_PATH);
    }

    @Test (expected = NullPointerException.class)
    public void nullPath_NotOk() {
        fileService.read(null);
    }

    @Test
    public void isValidPathToWrite() {
        fileService.write(CORRECT_FILE_PATH_TO_WRITE,TEST_STRING_FOR_WRITE);
    }

    @Test (expected = RuntimeException.class)
    public void noValidPathToWrite() {
        fileService.write(WRONG_FILE_PATH,TEST_STRING_FOR_WRITE);
    }

    @Test
    public void correctToWrite_Ok() {
        fileService.write(CORRECT_FILE_PATH_TO_WRITE,TEST_STRING_FOR_WRITE);
    }

    @Test (expected = NullPointerException.class)
    public void writeNullPath_NotOk() {
        fileService.write(null,TEST_STRING_FOR_WRITE);
    }

    @Test (expected = RuntimeException.class)
    public void writeNullFile_NotOk() {
        fileService.write(CORRECT_FILE_PATH_TO_WRITE, null);
    }
}
