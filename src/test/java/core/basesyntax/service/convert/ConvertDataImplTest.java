package core.basesyntax.service.convert;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConvertDataImplTest {
    private static ConvertData convertData;
    private static FruitTransaction fruitTransaction;
    private static List<String> testList;
    private static List<FruitTransaction> expectedFruitList;

    @Before
    public void setUp() {
        testList = new ArrayList<>();
        expectedFruitList = new ArrayList<>();
    }

    @After
    public void tearDown() {
        testList.clear();
        expectedFruitList.clear();
    }

    @BeforeClass
    public static void beforeClass() {
        convertData = new ConvertDataImpl();
    }

    @Test(expected = RuntimeException.class)
    public void convert_putNullToConvert_not_ok() {
        convertData.convert(null);
    }

    @Test(expected = RuntimeException.class)
    public void convert_nonCorrectPattern_not_ok() {
        testList.add("testString");
        convertData.convert(testList);
    }

    @Test
    public void convert_checkSize_ok() {
        testList.add("b,apple,12");
        testList.add("r,apple,25");
        testList.add("b,banana,34");
        testList.add("s,banana,32");
        expectedFruitList = convertData.convert(testList);
        int actual = expectedFruitList.size();
        assertEquals(4, actual);
    }

    @Test
    public void convert_validFruitTransaction_ok() {
        testList.add("b,apple,12");
        testList.add("r,apple,25");
        testList.add("b,banana,34");
        testList.add("s,banana,32");
        expectedFruitList = convertData.convert(testList);
        List<FruitTransaction> actual = new ArrayList<>();
        actual.add(new FruitTransaction(FruitTransaction.Operation.BALANCE.getOperation(),
                "apple", 12));
        actual.add(new FruitTransaction(FruitTransaction.Operation.RETURN.getOperation(),
                "apple", 25));
        actual.add(new FruitTransaction(FruitTransaction.Operation.BALANCE.getOperation(),
                "banana", 34));
        actual.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY.getOperation(),
                "banana", 32));
        for (int i = 0; i < expectedFruitList.size() - 1; i++) {
            assertEquals(expectedFruitList.get(i).getOperation(), actual.get(i).getOperation());
            assertEquals(expectedFruitList.get(i).getFruit(), actual.get(i).getFruit());
            assertEquals(expectedFruitList.get(i).getQuantity(), actual.get(i).getQuantity());
        }
    }
}
