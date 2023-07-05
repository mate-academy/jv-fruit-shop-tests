package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.DataValidatorService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataValidatorServiceImplTest {
    private static final List<String> VALID_DATA = Arrays.asList("type,fruit,quantity",
            "b,banana,20",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10");
    private final DataValidatorService dataValidatorService =
            new DataValidatorServiceImpl();
    private List<String> mutableData;

    @BeforeEach
    public void setUp() {
        mutableData = new ArrayList<>();
    }

    @Test
    public void test_isDataValid_validData_Ok() {
        mutableData = new ArrayList<>(VALID_DATA);
        assertTrue(dataValidatorService.isDataValid(mutableData));
    }

    @Test
    public void test_isDataValid_DataIsNull_NotOk() {
        mutableData = null;
        assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(mutableData));
    }

    @Test
    public void test_isDataValid_DataIsEmpty_NotOk() {
        String expected = "Data from input file empty";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(mutableData));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_isDataValid_DataNotHaveHeader_NotOk() {
        mutableData = new ArrayList<>(VALID_DATA);
        mutableData.remove(0);
        String expected = "Data from input file not have HEADER";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(mutableData));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_isDataValid_NotHaveStatisticLine_NotOk() {
        mutableData.add("type,fruit,quantity");
        String expected = "Data from input file not have statistic";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(mutableData));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_isDataValid_StringIsNotEqualsTreeGroups_NotOk() {
        mutableData = new ArrayList<>(VALID_DATA);
        mutableData.add(1, "b,banana,,20");
        String expected = "Data from input file not correct. "
                + "String format not equal three groups, separated by a comma in line 2";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
        mutableData.add(1, ",b,banana,20");
        exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
        mutableData.add(1, ",bbanana20");
        exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_isDataValid_OperationIsNotValid_NotOk() {
        mutableData = new ArrayList<>(VALID_DATA);
        mutableData.add(1, "sb,banana,20");
        String expected = "Data from input file not correct. "
                + "Unknown operation in line 2";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
        mutableData.add(1, ",bbanana,20");
        exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_isDataValid_ProductNameIsEmpty_NotOk() {
        mutableData = new ArrayList<>(VALID_DATA);
        mutableData.add(1, "s,,20");
        String expected = "Data from input file not correct. "
                + "The product name is empty in line 2";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_isDataValid_ProductValueIsEmpty_NotOk() {
        mutableData = new ArrayList<>(VALID_DATA);
        mutableData.add(1, "s,banana,");
        String expected = "Data from input file not correct. "
                + "The third group is not a positive number in line 2";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_isDataValid_ProductValueIsWhiteSpace_NotOk() {
        mutableData = new ArrayList<>(VALID_DATA);
        mutableData.add(1, "s,banana, ");
        String expected = "Data from input file not correct. "
                + "The third group is not a positive number in line 2";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_isDataValid_ProductValueNotNumber_NotOk() {
        mutableData = new ArrayList<>(VALID_DATA);
        mutableData.add(1, "s,banana,value20");
        String expected = "Data from input file not correct. "
                + "The third group is not a positive number in line 2";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_isDataValid_ProductValueNegativeNumber_NotOk() {
        mutableData = new ArrayList<>(VALID_DATA);
        mutableData.add(1, "s,banana,-20");
        String expected = "Data from input file not correct. "
                + "The third group is not a positive number in line 2";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
    }
}
