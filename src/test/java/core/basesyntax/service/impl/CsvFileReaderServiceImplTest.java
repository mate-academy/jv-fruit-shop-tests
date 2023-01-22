package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String VALID_PATH_FOR_SINGLE_TRANSACTION
            = "src/test/java/resources/inputSingleTransactionValid.csv";
    private static final String NOT_VALID_PATH = "not valid path";
    private FileReaderService fileReaderService = new CsvFileReaderServiceImpl();

    @Test
    public void fileReaderService_validPathToFile_isOk() {
        final Operation expectedOperation = Operation.getOperationByFirstLetter("b");
        String expectedName = "banana";
        int expectedQuantity = 20;

        List<FruitTransaction> actual = fileReaderService
                .readFromFile(VALID_PATH_FOR_SINGLE_TRANSACTION);
        assertFalse(actual.isEmpty());
        assertEquals(expectedName, actual.get(0).getFruit());
        assertEquals(expectedQuantity, actual.get(0).getQuantity());
        assertEquals(expectedOperation,
                Operation.getOperationByFirstLetter(actual.get(0).getOperation()));
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidFilePath_isNotOk() {
        fileReaderService.readFromFile(NOT_VALID_PATH);
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_nullPath_isNotOk() {
        fileReaderService = new CsvFileReaderServiceImpl();
        fileReaderService.readFromFile(null);
    }
}
