package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileDataConvertorService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileDataConvertorServiceImplTest {
    public static final String COMA = ",";
    public static final int OPERATION_INDEX = 0;
    public static final int FRUIT_NAME_INDEX = 1;
    public static final int QUANTITY_INDEX = 2;
    private static FileDataConvertorServiceImpl fileDataConvertorService;
    private static List<String> fileLines;

    @BeforeAll
    public static void init() {
        fileDataConvertorService = new FileDataConvertorServiceImpl();
        fileLines = new ArrayList<>();
        fileLines.add("b,banana,20");
        fileLines.add("b,apple,100");
        fileLines.add("s,banana,100");
        fileLines.add("p,banana,13");
        fileLines.add("r,apple,10");
        fileLines.add("p,apple,20");
        fileLines.add("p,banana,5");
        fileLines.add("s,banana,50");
    }

    @Test
    public void implementsCorrectInterface_OK() {
        List<Class<?>> interfaces =
                Arrays.asList(fileDataConvertorService.getClass().getInterfaces());
        Assertions.assertEquals(1, interfaces.size());
        Assertions.assertTrue(interfaces.contains(FileDataConvertorService.class));
    }

    @Test
    public void convertToFruitTransaction_convertList_OK() {
        List<FruitTransaction> fruitTransactionListTest = new ArrayList<>();
        for (String line : fileLines) {
            String[] splittedLine = line.split(COMA);
            String operationCode = splittedLine[OPERATION_INDEX];
            String fruitName = splittedLine[FRUIT_NAME_INDEX];
            int quantity = Integer.parseInt(splittedLine[QUANTITY_INDEX]);
            FruitTransaction transaction =
                    new FruitTransaction(operationCode, fruitName, quantity);
            fruitTransactionListTest.add(transaction);
        }
        List<FruitTransaction> fruitTransactionListConverted =
                fileDataConvertorService.convertToFruitTransaction(fileLines);

        Assertions.assertTrue(
                fruitTransactionListTest.containsAll(fruitTransactionListConverted)
                && fruitTransactionListConverted.containsAll(fruitTransactionListTest));
    }
}
