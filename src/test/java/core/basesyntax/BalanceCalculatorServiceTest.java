package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.BalanceCalculatorService;
import core.basesyntax.service.impl.BalanceCalculatorServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceCalculatorServiceTest {
    private static final int VALID_FIRST_QUANTITY = 152;
    private static final int VALID_SECOND_QUANTITY = 240;
    private static final int VALID_THIRD_QUANTITY = 400;
    private static final int VALID_FOURTH_QUANTITY = 250;
    private static final int FRUIT_COUNT = 4;
    private static final String VALID_FIRST_FRUIT = "banana";
    private static final String VALID_SECOND_FRUIT = "apple";
    private static final String VALID_THIRD_FRUIT = "lemon";
    private static final String VALID_FOURTH_FRUIT = "strawberry";

    private static final ArrayList<String> validInputStrings = new ArrayList<>(Arrays.asList(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50",
            "s,apple,150",
            "b,lemon,200",
            "b,strawberry,250",
            "s,lemon,200",
            "p,strawberry,250",
            "r,strawberry,250",
            "p,strawberry,300",
            "s,strawberry,300"));
    private static final String EXTRA_TRANSACTION = "s,apple,50";

    private static BalanceCalculatorService balanceCalculatorService;
    private ArrayList<String> strings;

    @BeforeClass
    public static void beforeAll() {
        balanceCalculatorService = new BalanceCalculatorServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.records.clear();
        strings = validInputStrings.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    @Test
    public void balanceCalculator_validData_ok() {
        balanceCalculatorService.calculate(strings);
        long actual;
        actual = Storage.records.size();
        assertEquals("Fruit count not equal ", FRUIT_COUNT, actual);
        actual = Storage.records.get(VALID_FIRST_FRUIT);
        assertEquals("Fruit quantity not equal ", VALID_FIRST_QUANTITY, actual);
        actual = Storage.records.get(VALID_SECOND_FRUIT);
        assertEquals("Fruit quantity not equal ", VALID_SECOND_QUANTITY, actual);
        actual = Storage.records.get(VALID_THIRD_FRUIT);
        assertEquals("Fruit quantity not equal ", VALID_THIRD_QUANTITY, actual);
        actual = Storage.records.get(VALID_FOURTH_FRUIT);
        assertEquals("Fruit quantity not equal ", VALID_FOURTH_QUANTITY, actual);
    }

    @Test
    public void balanceCalculator_invalidData_notOk() {
        strings.add(EXTRA_TRANSACTION);
        balanceCalculatorService.calculate(strings);
        long actual;
        actual = Storage.records.size();
        assertEquals("Fruit count not equal ", FRUIT_COUNT, actual);
        actual = Storage.records.get(VALID_FIRST_FRUIT);
        assertEquals("Fruit quantity not equal ", VALID_FIRST_QUANTITY, actual);
        actual = Storage.records.get(VALID_SECOND_FRUIT);
        assertNotEquals("Fruit quantity equal ", VALID_SECOND_QUANTITY, actual);
        actual = Storage.records.get(VALID_THIRD_FRUIT);
        assertEquals("Fruit quantity not equal ", VALID_THIRD_QUANTITY, actual);
        actual = Storage.records.get(VALID_FOURTH_FRUIT);
        assertEquals("Fruit quantity not equal ", VALID_FOURTH_QUANTITY, actual);
    }
}
