package core.basesyntax.datavalidation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class DataValidatorImplTest {
    private static final DataValidator dataValidator = new DataValidatorImpl();
    private List<String> dataList = new ArrayList<>();

    @Test
    public void validateData_ValidData_Ok() {
        dataList.clear();
        dataList.add("b,apple,10");
        assertTrue(dataValidator.validateData(dataList));
    }

    @Test
    public void validateData_EmptyData_Ok() {
        dataList.clear();
        assertTrue(dataValidator.validateData(dataList));
    }

    @Test
    public void validateData_ValidDataWithSpaces_Ok() {
        dataList.clear();
        dataList.add("  b ,  apple,  10 ");
        assertTrue(dataValidator.validateData(dataList));
    }

    @Test
    public void validateData_ValidFirstLine_Ok() {
        dataList.clear();
        dataList.add("type,fruit,quantity");
        assertTrue(dataValidator.validateData(dataList));
    }

    @Test
    public void validateData_ValidWithNoSuchFruitInStorage_Ok() {
        dataList.clear();
        dataList.add("s,aple,10");
        assertTrue(dataValidator.validateData(dataList));
    }

    @Test
    public void validateData_InvalidOperationInData_Bad() {
        dataList.clear();
        dataList.add("t,apple,7");
        boolean actual;
        try {
            actual = dataValidator.validateData(dataList);
        } catch (RuntimeException e) {
            actual = false;
        }
        assertFalse(actual);
    }

    @Test
    public void validateData_NullData_Bad() {
        dataList.clear();
        dataList.add(null);
        boolean actual;
        try {
            actual = dataValidator.validateData(dataList);
        } catch (NullPointerException e) {
            actual = false;
        }
        assertFalse(actual);
    }

    @Test
    public void validateData_CharactersInsteadOfNumbersAmountData_Bad() {
        dataList.clear();
        dataList.add("s,apple,five");
        boolean actual;
        try {
            actual = dataValidator.validateData(dataList);
        } catch (RuntimeException e) {
            actual = false;
        }
        assertFalse(actual);
    }

    @Test
    public void validateData_NegativeAmountData_Bad() {
        dataList.clear();
        dataList.add("s,apple,-7");
        boolean actual;
        try {
            actual = dataValidator.validateData(dataList);
        } catch (RuntimeException e) {
            actual = false;
        }
        assertFalse(actual);
    }
}
