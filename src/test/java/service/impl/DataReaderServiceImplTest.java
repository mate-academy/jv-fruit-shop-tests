package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataReaderServiceImplTest {
    private static DataReaderServiceImpl dataReaderService;
    private static final String EMPTY_FILE
            = "src/test/java/resources/empty.txt";
    private static final String PATH_FILE_TO_READ
            = "src/test/java/resources/testinput.txt";
    private static final String PATH_TO_INCORRECT_FILE
            = "src/test/java/resources/file.txt";

    @BeforeEach
    public void setDataReaderService() {
        dataReaderService = new DataReaderServiceImpl();
    }

    @Test
    public void incorrectPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> dataReaderService.readDataInFile(PATH_TO_INCORRECT_FILE));
    }

    @Test
    public void readEmptyFile_Ok() {
        List<FruitTransaction> actual =
                dataReaderService.readDataInFile(EMPTY_FILE);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void nullInFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> dataReaderService.readDataInFile(null));
    }

    @Test
    public void readData_Ok() {
        List<FruitTransaction> fruitTransactionList
                = dataReaderService.readDataInFile(PATH_FILE_TO_READ);
        String fruitFirstPosition = "apple";
        int quantityFirstPosition = 100;
        FruitTransaction.Operation thirdPosition
                = FruitTransaction.Operation.PURCHASE;

        assertEquals(fruitTransactionList
                .get(1)
                .getFruit(), fruitFirstPosition);
        assertEquals(fruitTransactionList
                .get(1)
                .getQuantity(), quantityFirstPosition);
        assertEquals(fruitTransactionList.get(3).getOperation(),
                thirdPosition);
    }

}
