package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.DataDao;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.implementation.DataDaoImpl;
import core.basesyntax.dao.implementation.FruitDaoImpl;
import core.basesyntax.service.DataService;
import core.basesyntax.service.FileService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataServiceImplTest {
    private static final String FROM_FILE_PATH = "src/main/resources/test.csv";
    private static final String[] INCORRECT_FILES
                = {"src/main/resources/incorrect/EmptyFile.csv",
                    "src/main/resources/incorrect/IncorrectAction.csv",
                    "src/main/resources/incorrect/IncorrectAmount.csv",
                    "src/main/resources/incorrect/IncorrectNumberOfFields.csv"};
    private static DataDao dataDao;
    private static FruitDao fruitDao;
    private static DataService dataService;
    private static FileService fileService;

    @BeforeAll
    public static void setUp() {
        dataDao = new DataDaoImpl();
        fruitDao = new FruitDaoImpl();
        dataService = new DataServiceImpl();
        fileService = new FileServiceImpl();
    }

    @BeforeEach
    public void beforeEach() {
        dataDao.getData().clear();
        fruitDao.getMap().clear();
    }

    @Test
    public void fillDataStorage_CorrectData_ok() {
        List<String> dataFromFile = fileService.readFromFile(FROM_FILE_PATH);
        List<String> expected = dataFromFile.stream()
                .skip(1)
                .map(string -> string
                        .replaceAll("\\s", ""))
                .toList();
        dataService.fillDataStorage(dataFromFile);
        List<String> actual = dataDao.getData();
        assertEquals(expected, actual);
    }

    @Test
    public void fillFruitStorage_CorrectData_ok() {
        Map<String, Integer> expected = Map
                .of("banana", 0, "apple", 0);
        List<String> dataFromFile = fileService.readFromFile(FROM_FILE_PATH);
        dataService.fillDataStorage(dataFromFile);
        dataService.fillFruitStorage();
        Map<String, Integer> actual = fruitDao.getMap();
        assertEquals(expected, actual);
    }

    @Test
    public void fillDataStorage_IncorrectData_NotOk() {
        for (String filePath : INCORRECT_FILES) {
            List<String> dataFromFile = fileService.readFromFile(filePath);
            assertThrows(RuntimeException.class, () -> {
                dataService.fillDataStorage(dataFromFile);
            });
        }
    }

}
