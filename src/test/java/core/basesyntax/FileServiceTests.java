package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.fileserve.FileService;
import core.basesyntax.fileserve.FileServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileServiceTests {
    private static final String DATA_INPUT_PATH =
            "C:\\Users\\bahme\\IdeaProjects\\jv-fruit-shop-tests"
                    + "\\src\\main\\java\\core\\basesyntax\\files\\NormalData.txt";
    private static final String NOT_EXIST_FILE_PATH =
            "C:\\Users\\bahme\\IdeaProjects\\jv-fruit-shop-tests"
                    + "\\src\\main\\java\\core\\basesyntax\\files\\Path.txt";
    private static final String DATA_OUTPUT_PATH =
            "C:\\Users\\bahme\\IdeaProjects\\jv-fruit-shop-tests"
                    + "\\src\\main\\java\\core\\basesyntax\\files\\WrittenData.txt";
    private static final String DIRECTORY_PATH =
            "C:\\Users\\bahme\\IdeaProjects\\jv-fruit-shop-tests"
                    + "\\src\\main\\java\\core\\basesyntax\\files";
    private static final String FRUIT_DATA =
            "b,banana,100" + System.lineSeparator()
            + "b,apple,29" + System.lineSeparator()
            + "r,banana,10" + System.lineSeparator()
            + "r,apple,4" + System.lineSeparator()
            + "p,banana,20" + System.lineSeparator()
            + "p,apple,10" + System.lineSeparator()
            + "s,banana,20" + System.lineSeparator()
            + "s,apple,20" + System.lineSeparator();
    private static FileService fileService;

    @BeforeAll
    public static void beforeAll() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void getFruitData_NormalData_ok() {
        String dataFromFIle = fileService.getFruitData(DATA_INPUT_PATH);
        assertEquals(FRUIT_DATA, dataFromFIle);
    }

    @Test
    public void getFruitData_NotExistFile_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileService.getFruitData(NOT_EXIST_FILE_PATH);
        });
    }

    @Test
    public void writeToFile_normalData_ok() {
        assertTrue(fileService.writeToFile(FRUIT_DATA, DATA_OUTPUT_PATH));
    }

    @Test
    public void writeToFile_writeToDirectory_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileService.writeToFile(FRUIT_DATA, DIRECTORY_PATH);
        });
    }
}
