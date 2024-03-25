package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.FileNotExistException;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderServiceImplTest {
    private static final String PATH_TO_FILE_OK = "src/test/resources/Input.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/EmptyFile.csv";
    private static final String INCORRECT_PATH = "main/incorrectName.csv";
    private static List<String> listOk;
    private static FileReaderServiceImpl fileReaderService;

    @BeforeAll
    public static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
        listOk = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    @Test
    void readFromFile_FileExist_Ok() {
        assertEquals(listOk, fileReaderService.readFromFile(PATH_TO_FILE_OK));
    }

    @Test
    void readFromFile_EmptyFile_notOk() {
        assertThrows(FileNotExistException.class, () -> {
            fileReaderService.readFromFile(PATH_TO_EMPTY_FILE);
        }, "Expected " + FileNotExistException.class.getName());
    }

    @Test
    void readFromFile_NotFoundFile_notOk() {
        assertThrows(FileNotExistException.class, () -> {
            fileReaderService.readFromFile(INCORRECT_PATH);
        }, "Expected " + FileNotExistException.class.getName());
    }

    @Test
    void readFromFile_FileDoesNotExist_notOk() {
        assertThrows(FileNotExistException.class, () -> {
            fileReaderService.readFromFile("nonexistent_file.csv");
        }, "Expected " + FileNotExistException.class.getName());
    }
}
