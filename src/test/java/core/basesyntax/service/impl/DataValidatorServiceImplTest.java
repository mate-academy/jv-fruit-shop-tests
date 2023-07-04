package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.DataValidatorService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataValidatorServiceImplTest {
    private static final List<String> VALID_DATA = Arrays.asList("type,fruit,quantity",
            "b,banana,20",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10");
    private final DataValidatorService dataValidatorService =
            new DataValidatorServiceImpl();

    @Test
    public void test_DataValidator_DataIsValid_Ok() {
        List<String> mutableData = new ArrayList<>(VALID_DATA);
        assertTrue(dataValidatorService.isDataValid(mutableData));
    }

    @Test
    public void test_DataValidator_DataIsNull_NotOk() {
        List<String> mutableData = null;
        assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(mutableData));
    }

    @Test
    public void test_DataValidator_DataIsEmpty_NotOk() {
        List<String> mutableData = new ArrayList<>();
        String expected = "Data from input file empty";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(mutableData));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_DataValidator_DataNotHaveHeader_NotOk() {
        List<String> mutableData = new ArrayList<>(VALID_DATA);
        mutableData.remove(0);
        String expected = "Data from input file not have HEADER";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(mutableData));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_DataValidator_NotHaveStatisticLine_NotOk() {
        List<String> mutableData = new ArrayList<>();
        mutableData.add("type,fruit,quantity");
        String expected = "Data from input file not have statistic";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(mutableData));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_DataValidator_StringIsNotEqualsTreeGroups_NotOk() {
        List<String> mutableData = new ArrayList<>(VALID_DATA);
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
    public void test_DataValidator_OperationIsNotValid_NotOk() {
        List<String> mutableData = new ArrayList<>(VALID_DATA);
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
    public void test_DataValidator_ProductNameIsEmpty_NotOk() {
        List<String> mutableData = new ArrayList<>(VALID_DATA);
        mutableData.add(1, "s,,20");
        String expected = "Data from input file not correct. "
                + "The product name is empty in line 2";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_DataValidator_ProductValueIsEmpty_NotOk() {
        List<String> mutableData = new ArrayList<>(VALID_DATA);
        mutableData.add(1, "s,banana,");
        String expected = "Data from input file not correct. "
                + "The third group is not a positive number in line 2";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_DataValidator_ProductValueIsWhiteSpace_NotOk() {
        List<String> mutableData = new ArrayList<>(VALID_DATA);
        mutableData.add(1, "s,banana, ");
        String expected = "Data from input file not correct. "
                + "The third group is not a positive number in line 2";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_DataValidator_ProductValueNotNumber_NotOk() {
        List<String> mutableData = new ArrayList<>(VALID_DATA);
        mutableData.add(1, "s,banana,value20");
        String expected = "Data from input file not correct. "
                + "The third group is not a positive number in line 2";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void test_DataValidator_ProductValueNegativeNumber_NotOk() {
        List<String> mutableData = new ArrayList<>(VALID_DATA);
        mutableData.add(1, "s,banana,-20");
        String expected = "Data from input file not correct. "
                + "The third group is not a positive number in line 2";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidatorService.isDataValid(new ArrayList<>(mutableData)));
        assertEquals(expected, exception.getMessage());
    }
}
