package core.basesyntax.services.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ValidatorImplTest {
    private static final String COMMA = ",";
    private ValidatorImpl validator;
    private List<String> listData;

    @Before
    public void setUp() {
        validator = new ValidatorImpl();
        listData = new ArrayList<>();
        listData.add("b,banana,20");
        listData.add("s,banana,100");
    }

    @Test
    public void checkInputRowData_Ok() {
        String rowData = "b,banana,20";
        boolean actual = validator.checkInputRowData(rowData.split(COMMA));
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void checkInputRowData_NotEnoughData_ThrowExc_NotOK() {
        String rowData = "b,banana";
        validator.checkInputRowData(rowData.split(COMMA));
    }

    @Test(expected = RuntimeException.class)
    public void checkInputRowData_FruitNameIsBlank_ThrowExc_NotOK() {
        String rowData = "b, ,4";
        validator.checkInputRowData(rowData.split(COMMA));
    }

    @Test(expected = RuntimeException.class)
    public void checkInputRowData_QuantityIsNegative_ThrowExc_NotOK() {
        String rowData = "b,banana,-4";
        validator.checkInputRowData(rowData.split(COMMA));
    }

    @Test(expected = RuntimeException.class)
    public void checkInputRowData_OperationLengthIsNotCorrect_ThrowExc_NotOK() {
        String rowData = "ba,banana,-4";
        validator.checkInputRowData(rowData.split(COMMA));
    }

    @Test
    public void checkInputData_OK() {
        assertTrue(validator.checkInputData(listData));
    }

    @Test(expected = RuntimeException.class)
    public void checkInputData_NotOK() {
        listData.add("ba,banana,-4");
        validator.checkInputData(listData);
    }

    @Test
    public void checkOperation_Ok() {
        boolean actual = validator.checkOperation(10);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void checkOperation_NegativeQuantity_NotOk() {
        validator.checkOperation(-10);
    }
}
