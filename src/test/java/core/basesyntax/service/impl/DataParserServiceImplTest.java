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
    private static DataParserService dataParserService;
    private static List<FruitTransaction> expectedList;

    @BeforeClass
    public static void beforeClass() {
        dataParserService = new DataParserServiceImpl();
        expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 15));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10));
        expectedList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 40));
    }

    @Test
    public void parseFruits_validOutput_ok() {
        List<String> passedList = new ArrayList<>();
        passedList.add("type,fruit,quantity");
        passedList.add("b,apple,15");
        passedList.add("b,banana,10");
        passedList.add("b,apple,40");
        List<FruitTransaction> actual = dataParserService.parseFruits(passedList);
        assertEquals(expectedList, actual);
    }

    @Test
    public void parseFruits_emptyList_ok() {
        List<FruitTransaction> actual = dataParserService.parseFruits(Collections.emptyList());
        assertNotEquals(expectedList, actual);
    }
}
