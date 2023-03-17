package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exeption.FruitShopException;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String PATH_TO_FILE_OK = "src/test/resources/FruitStorageOperation.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/EmptyFile.csv";
    private static final String INCORRECT_PATH = "F://incorrectName.csv";
    private static List<String> listOk;
    private static FileReaderServiceImpl fileReaderService;

    @BeforeClass
    public static void beforeClass(){
        fileReaderService = new FileReaderServiceImpl();
        listOk = new ArrayList<>();
        listOk.add("b,banana,20");
        listOk.add("b,apple,100");
        listOk.add("s,banana,100");
        listOk.add("p,banana,13" );
        listOk.add("r,apple,10");
        listOk.add("p,apple,20");
        listOk.add("p,banana,5");
        listOk.add("s,banana,50");
    }

    @Test
    public void readFromFile_ok() {
        assertEquals(listOk, fileReaderService.readFromFile(PATH_TO_FILE_OK));
    }

    @Test(expected = FruitShopException.class)
    public void readFromEmptyFile_notOk() {
        fileReaderService.readFromFile(PATH_TO_EMPTY_FILE);
        fail("Expected " + FruitShopException.class.getName() + " but it wasn't");
    }

    @Test(expected = FruitShopException.class)
    public void readFromNotFoundFile_notOk() {
        fileReaderService.readFromFile(INCORRECT_PATH);
        fail("Expected " + FruitShopException.class.getName() + " but it wasn't");
    }
}
