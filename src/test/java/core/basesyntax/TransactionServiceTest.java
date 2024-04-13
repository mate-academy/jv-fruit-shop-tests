package core.basesyntax;

import core.basesyntax.database.Operation;
import core.basesyntax.impl.FileServiceImpl;
import core.basesyntax.impl.TransactionServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileService;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionServiceTest {
    private static final String PATH1 = "./src/test/resources/testData";
    private static final int INDEX_OF_FIRST_FRUIT = 0;
    private static final int INDEX_OF_SECOND_FRUIT = 1;
    private final FileService fileService = new FileServiceImpl();
    private final TransactionService transactionService = new TransactionServiceImpl();

    @Test
    void convertData_Ok() {
        List<String> strings = fileService.readDataFromFile(PATH1);
        List<FruitTransaction> actual = transactionService.parseData(strings);
        List<FruitTransaction> expected = new ArrayList<>();
        FruitTransaction fruit1 = new FruitTransaction(Operation.BALANCE, "banana", 100);
        FruitTransaction fruit2 = new FruitTransaction(Operation.BALANCE, "apple", 100);
        expected.add(fruit1);
        expected.add(fruit2);
        Assertions.assertEquals(actual.get(INDEX_OF_FIRST_FRUIT).getFruit(), fruit1.getFruit());
        Assertions.assertEquals(actual.get(INDEX_OF_SECOND_FRUIT).getFruit(), fruit2.getFruit());

    }
}
