package core.basesyntax.datavalidation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataValidatorImplTest {
    private static DataValidator dataValidator;
    private final List<String> dataList = new ArrayList<>();

    @BeforeClass
    public static void initialize() {
        dataValidator = new DataValidatorImpl();
    }

    @Before
    public void clearData() {
        dataList.clear();
    }

    @Test
    public void validateData_ValidData_Ok() {
        dataList.add("type,fruit,quantity");
        dataList.add("b,apple,10");
        assertTrue(dataValidator.validateData(dataList));
    }

    @Test(expected = RuntimeException.class)
    public void validateData_EmptyData_Bad() {
        dataValidator.validateData(dataList);
    }

    @Test
    public void validateData_ValidDataWithSpaces_Ok() {
        dataList.add("  b ,  apple,  10 ");
        assertTrue(dataValidator.validateData(dataList));
    }

    @Test(expected = RuntimeException.class)
    public void validateData_ValidOnlyFirstLineNoInfoAboutFruits_Bad() {
        dataList.add("type,fruit,quantity");
        dataValidator.validateData(dataList);
    }

    @Test
    public void validateData_ValidWithNoSuchFruitInStorage_Ok() {
        dataList.add("s,mango,10");
        assertTrue(dataValidator.validateData(dataList));
    }

    @Test(expected = RuntimeException.class)
    public void validateData_InvalidOperationInData_Bad() {
        dataList.add("t,apple,7");
        dataValidator.validateData(dataList);
    }

    @Test(expected = RuntimeException.class)
    public void validateData_NullData_Bad() {
        dataList.add(null);
        dataValidator.validateData(dataList);
    }

    @Test(expected = RuntimeException.class)
    public void validateData_CharactersInsteadOfNumbersAmountData_Bad() {
        dataList.add("s,apple,five");
        dataValidator.validateData(dataList);
    }

    @Test(expected = RuntimeException.class)
    public void validateData_NegativeAmountData_Bad() {
        dataList.add("s,apple,-7");
        dataValidator.validateData(dataList);
    }
}
