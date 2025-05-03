package core.basesyntax.service;

import core.basesyntax.Constants;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CsvReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvReaderServiceImplTest {
    private static final int APPLE_QUANTITY = 100;
    private static final int BANANA_QUANTITY_BATCH1 = 20;
    private static final int BANANA_QUANTITY_BATCH2 = 100;
    private static final CsvReaderServiceImpl readerService = new CsvReaderServiceImpl();

    @Test
    public void readFromFile_ReadCorrectPath_ArraysAreEqual() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                Constants.BANANA,
                BANANA_QUANTITY_BATCH1));

        expected.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                Constants.APPLE, APPLE_QUANTITY));

        expected.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                Constants.BANANA, BANANA_QUANTITY_BATCH2));

        List<FruitTransaction> actual = readerService.readFromFile(Constants.EXISTING_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_ReadNotCorrectPath_UncheckedExceptionThrown() {
        Assert.assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(Constants.ERROR_PATH);
        });
    }

    @Test
    public void readFromFile_ReadInvalidData_UncheckedExceptionThrown() {
        Assert.assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(Constants.INVALID_DATA);
        });
    }
}
