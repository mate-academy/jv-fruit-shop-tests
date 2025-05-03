package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.InvalidParametersException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.model.FruitsInStore;
import core.basesyntax.model.OperationType;
import core.basesyntax.services.DataParcerService;
import core.basesyntax.services.ParametrsValidatorService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParcerServiceImplTest {
    private static final String ROW_TITLE = "operation,fruit,quantity";
    private static DataParcerService parcerService;
    private static List<String> validData;
    private static List<String> invalidData;
    private static List<List<String>> expectedData;
    private static List<String> opratorTypeCodes;
    private static List<String> fruitsInStore;
    private static ParametrsValidatorService parametrsValidator;

    @BeforeClass
    public static void beforeClass() {
        opratorTypeCodes = Arrays.stream(OperationType.values())
                .map(OperationType::getCode)
                .collect(Collectors.toList());
        fruitsInStore = Arrays.stream(FruitsInStore.values())
                .map(FruitsInStore::getCode)
                .collect(Collectors.toList());
        parametrsValidator = new ParametrsValidatorServiseImpl(opratorTypeCodes, fruitsInStore);
        validData = List.of(ROW_TITLE, "s,apple,20", "b,banana,100", "p,apple,800");
        expectedData = List.of(List.of("s", "apple", "20"),
                List.of("b", "banana", "100"),
                List.of("p", "apple", "800"));
    }

    @Before
    public void setUp() {
        parcerService = new DataParcerServiceImpl(parametrsValidator);
        invalidData = new ArrayList<>(List.of(ROW_TITLE,
                "s,apple,20   ",
                "  b  ,  banana  ,   100",
                "  p  ,  apple , 800   "));
    }

    @Test
    public void parceDataFromCsv_validData_ok() {
        assertEquals(expectedData, parcerService.parceDataFromCsv(validData));
    }

    @Test
    public void parceDataFromCsv_invalidData_ok() {
        assertEquals(expectedData, parcerService.parceDataFromCsv(invalidData));
    }

    @Test
    public void parceDataFromCsv_emptyData_ok() {
        assertEquals(Collections.EMPTY_LIST,
                parcerService.parceDataFromCsv(Collections.EMPTY_LIST));
    }

    @Test(expected = NullDataException.class)
    public void parceDataFromCsv_dataNull_notOk() {
        parcerService.parceDataFromCsv(null);
    }

    @Test(expected = InvalidParametersException.class)
    public void parceDataFromCsv_invalidData_notOk() {
        invalidData.add("");
        parcerService.parceDataFromCsv(invalidData);
    }

    @Test(expected = NullDataException.class)
    public void parceDataFromCsv_invalidNullData_notOk() {
        invalidData.add(null);
        parcerService.parceDataFromCsv(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void parceDataFromCsv_invalidMoreParametrs_notOk() {
        invalidData.add("b,banana,100,we,w");
        parcerService.parceDataFromCsv(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void parceDataFromCsv_invalidEmptyOperation_notOk() {
        invalidData.add("  ,banana,100");
        parcerService.parceDataFromCsv(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void parceDataFromCsv_invalidEmptyFruit_notOk() {
        invalidData.add("p,  ,100");
        parcerService.parceDataFromCsv(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void parceDataFromCsv_invalidEmptyQuantity_notOk() {
        invalidData.add("p, banana ,  ");
        parcerService.parceDataFromCsv(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void parceDataFromCsv_invalidOperation_notOk() {
        invalidData.add(" ddd ,banana,100");
        parcerService.parceDataFromCsv(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void parceDataFromCsv_invalidFruit_notOk() {
        invalidData.add("p, tomato ,100");
        parcerService.parceDataFromCsv(invalidData);
    }

    @Test(expected = InvalidParametersException.class)
    public void parceDataFromCsv_invalidQuantity_notOk() {
        invalidData.add("p, banana , qw ");
        parcerService.parceDataFromCsv(invalidData);
    }
}
