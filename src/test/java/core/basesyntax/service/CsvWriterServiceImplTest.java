package core.basesyntax.service;

import core.basesyntax.Constants;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.CsvReaderServiceImpl;
import core.basesyntax.service.impl.CsvWriterServiceImpl;
import core.basesyntax.service.impl.TransactionProcessorServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvWriterServiceImplTest {
    private static final int APPLE_QUANTITY = 100;
    private static final int BANANA_QUANTITY = 120;
    private static final int FRUIT_INDEX = 0;
    private static final int QUANTITY_INDEX = 1;
    private static final int ACTUAL_FIRST_INDEX = 1;
    private static final int ACTUAL_SECOND_INDEX = 2;
    private static final int EXPECTED_FIRST_INDEX = 0;
    private static final int EXPECTED_SECOND_INDEX = 1;
    private static final CsvReaderServiceImpl readerService = new CsvReaderServiceImpl();
    private static final CsvWriterServiceImpl writerService = new CsvWriterServiceImpl();

    @Test
    public void writeToFile_WriteCorrectPath_DataEquals() {
        List<FruitTransaction> inputData = readerService.readFromFile(Constants.EXISTING_PATH);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(null, Constants.BANANA, BANANA_QUANTITY));
        expected.add(new FruitTransaction(null, Constants.APPLE, APPLE_QUANTITY));

        ProcessorService processorService = new TransactionProcessorServiceImpl();
        FruitStorage storage = new FruitStorage();
        processorService.processTransactions(inputData, storage);
        writerService.writeToFile(Constants.EXISTING_PATH_OUTPUT, storage.getFruitQuantities());

        List<String> actual;
        try {
            Path filePath = Paths.get(Constants.EXISTING_PATH_OUTPUT);

            try (var ignored = Files.newBufferedReader(filePath)) {
                actual = Files.readAllLines(filePath);
            }

            String[] actualFruit1Info = actual
                    .get(ACTUAL_FIRST_INDEX)
                    .split(Constants.CSV_DELIMITER);

            String[] actualFruit2Info = actual
                    .get(ACTUAL_SECOND_INDEX)
                    .split(Constants.CSV_DELIMITER);

            String actualFruit1 = actualFruit1Info[FRUIT_INDEX];
            String actualFruit2 = actualFruit2Info[FRUIT_INDEX];
            int actualAmount1 = Integer.parseInt(actualFruit1Info[QUANTITY_INDEX]);
            int actualAmount2 = Integer.parseInt(actualFruit2Info[QUANTITY_INDEX]);

            String expectedFruit1 = expected.get(EXPECTED_FIRST_INDEX).getFruit();
            String expectedFruit2 = expected.get(EXPECTED_SECOND_INDEX).getFruit();
            int expectedAmount1 = expected.get(EXPECTED_FIRST_INDEX).getQuantity();
            int expectedAmount2 = expected.get(EXPECTED_SECOND_INDEX).getQuantity();

            Assert.assertEquals(expectedFruit1, actualFruit1);
            Assert.assertEquals(expectedAmount1, actualAmount1);
            Assert.assertEquals(expectedFruit2, actualFruit2);
            Assert.assertEquals(expectedAmount2, actualAmount2);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
