package core.basesyntax;

import static java.nio.file.Files.createFile;
import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String INPUT_FILE_EXIST = "input.csv";
    private static final String INPUT_FILE_NOT_EXIST = "notExistFile.csv";
    private static final List<String> TEST_LIST = List.of("b,banana,20", "b,apple,100",
            "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5",
            "s,banana,50");
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeAll() {
        createNewFile(Path.of(INPUT_FILE_EXIST));
        fileReaderService = new FileReaderServiceImpl();
    }

    @AfterClass
    public static void afterAll() {
        try {
            Files.deleteIfExists(Path.of(INPUT_FILE_EXIST));
        } catch (IOException e) {
            throw new RuntimeException("Remove test file error", e);
        }
    }

    @Test
    public void readFromFile_existFile_Ok() {
        List<String> actual = fileReaderService.readFromFile(INPUT_FILE_EXIST);
        assertEquals(TEST_LIST, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notExistFile_notOk() {
        fileReaderService.readFromFile(INPUT_FILE_NOT_EXIST);
    }

    private static void createNewFile(Path path) {
        try {
            createFile(path);
            Files.write(path, TEST_LIST);
        } catch (IOException e) {
            throw new RuntimeException("Can't create file", e);
        }
    }
}
