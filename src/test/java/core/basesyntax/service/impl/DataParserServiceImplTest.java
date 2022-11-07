package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceImplTest {
    private static DataParserService dataParserService = new DataParserServiceImpl();
    private static List<FruitTransaction> expectedList = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
    }

    @Test
    public void getFruitsToList_validOutput_ok() {
        List<String> passedList = new ArrayList<>();
        passedList.add("type,fruit,quantity");
        passedList.add("b,banana,20");
        passedList.add("b,apple,100");
        List<FruitTransaction> actual = dataParserService.getFruitsToList(passedList);
        assertEquals(expectedList, actual);
    }

    @Test
    public void getFruitsToList_emptyList_ok() {
        List<FruitTransaction> actual = dataParserService.getFruitsToList(Collections.emptyList());
        assertNotEquals(expectedList, actual);
    }
}
