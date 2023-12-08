package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.DataDao;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.implementation.DataDaoImpl;
import core.basesyntax.dao.implementation.FruitDaoImpl;
import core.basesyntax.db.DataStorage;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.DataService;
import core.basesyntax.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataServiceImplTest {
    private static final String FROM_FILE_PATH = "src/test/resources/test.csv";
    private static final String[] INCORRECT_FILES
                = {"src/test/resources/incorrect/EmptyFile.csv",
                    "src/test/resources/incorrect/IncorrectAction.csv",
                    "src/test/resources/incorrect/IncorrectAmount.csv",
                    "src/test/resources/incorrect/IncorrectNumberOfFields.csv"};
    private static DataStorage dataStorage;
    private static FruitStorage fruitStorage;
    private static DataService dataService;
    private static List<String> dataFromFile;

    @BeforeAll
    public static void setUp() {
        dataService = new DataServiceImpl();
        dataStorage = new DataStorage();
        fruitStorage = new FruitStorage();
        dataFromFile = new ArrayList<>();
    }

    @BeforeEach
    public void beforeEach() {
        dataStorage.getListOfData().clear();
        fruitStorage.getMapOfFruits().clear();
        dataFromFile.clear();
    }

    @Test
    public void fillDataStorage_CorrectData_ok() {
        try {
            dataFromFile = Files.readAllLines(new File(FROM_FILE_PATH)
                    .toPath());
        } catch (IOException e) {
            throw new RuntimeException("Check your file path "
                    + FROM_FILE_PATH, e);
        }
        List<String> expected = dataFromFile.stream()
                .skip(1)
                .map(string -> string
                        .replaceAll("\\s", ""))
                .toList();
        dataService.fillDataStorage(dataFromFile);
        List<String> actual = dataStorage.getListOfData();
        assertEquals(expected, actual);
    }

    @Test
    public void fillFruitStorage_CorrectData_ok() {
        Map<String, Integer> expected = Map
                .of("banana", 0, "apple", 0);
        try {
            dataFromFile = Files.readAllLines(new File(FROM_FILE_PATH)
                    .toPath());
        } catch (IOException e) {
            throw new RuntimeException("Check your file path "
                    + FROM_FILE_PATH, e);
        }
        dataService.fillDataStorage(dataFromFile);
        dataService.fillFruitStorage();
        Map<String, Integer> actual = fruitStorage.getMapOfFruits();
        assertEquals(expected, actual);
    }

    @Test
    public void fillDataStorage_IncorrectData_NotOk() {
        for (String filePath : INCORRECT_FILES) {
            try {
                dataFromFile = Files.readAllLines(new File(filePath)
                        .toPath());
            } catch (IOException e) {
                throw new RuntimeException("Check your file path "
                        + filePath, e);
            }
            assertThrows(RuntimeException.class, () -> {
                dataService.fillDataStorage(dataFromFile);
            });
        }
    }

}
