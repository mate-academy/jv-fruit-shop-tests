package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CheckDataService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CheckDataServiceImplTest {
    private static final String TEST_FRUIT_INFO = "b,banana,";
    private static final int MIN_TEST_QUANTITY = 1;
    private static final int MAX_TEST_QUANTITY = 10000;
    private static CheckDataService checkDataService;
    private static final List<String> correctList = new ArrayList<>();

    @BeforeAll
    static void initVariables() {
        checkDataService = new CheckDataServiceImpl();

        correctList.add("type,fruit,quantity");
        correctList.add("b,banana,20");
        correctList.add("b,apple,100");
        correctList.add("s,banana,100");
        correctList.add("p,banana,13");
        correctList.add("r,apple,10");
        correctList.add("p,apple,20");
        correctList.add("p,banana,5");
        correctList.add("s,banana,50");
    }

    @Test
    void checkData_correctList_ok() {
        checkDataService.checkData(correctList);
    }

    @Test
    void checkData_emptyList_notOk() {
        List<String> emptyList = new ArrayList<>();
        assertThrows(RuntimeException.class,
                () -> checkDataService.checkData(emptyList));
    }

    @Test
    void checkData_listWithoutTitleString_notOk() {
        List<String> listWithoutTitleString =
                List.copyOf(correctList.subList(1, correctList.size()));
        assertThrows(RuntimeException.class,
                () -> checkDataService.checkData(listWithoutTitleString));
    }

    @Test
    void checkData_zeroQuantity_ok() {
        List<String> testingList = new ArrayList<>(List.copyOf(correctList));
        testingList.set(1, TEST_FRUIT_INFO + 0);
        checkDataService.checkData(testingList);
    }

    @Test
    void checkData_positiveQuantity_ok() {
        List<String> testingList = new ArrayList<>(List.copyOf(correctList));
        for (int i = MIN_TEST_QUANTITY; i <= MAX_TEST_QUANTITY; i++) {
            testingList.set(1, TEST_FRUIT_INFO + (i));
            checkDataService.checkData(testingList);
        }
    }

    @Test
    void checkData_negativeQuantity_notOk() {
        List<String> testingList = new ArrayList<>(List.copyOf(correctList));
        for (int i = MIN_TEST_QUANTITY; i <= MAX_TEST_QUANTITY; i++) {
            testingList.set(1, TEST_FRUIT_INFO + (-i));
            assertThrows(RuntimeException.class, () -> checkDataService.checkData(testingList));
        }
    }
}
