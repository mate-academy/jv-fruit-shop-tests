package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static List<FruitTransaction> expectedList;
    private static List<String> inputReport;
    private static DataConverter dataConverter;
    private static FruitTransaction balanceBanana;
    private static FruitTransaction balanceApple;
    private static FruitTransaction supplyBanana;
    private static FruitTransaction returnApple;
    private static FruitTransaction purchaseBanana;

    @BeforeAll
    static void beforeAll() {
        expectedList = new ArrayList<>();
        inputReport = new ArrayList<>();
        dataConverter = new DataConverterImpl();
        balanceBanana = new FruitTransaction(BALANCE, "banana", 100);
        balanceApple = new FruitTransaction(BALANCE, "apple", 10);
        supplyBanana = new FruitTransaction(SUPPLY, "banana", 35);
        returnApple = new FruitTransaction(RETURN, "apple", 0);
        purchaseBanana = new FruitTransaction(PURCHASE, "banana", 40);
    }

    @AfterEach
    public void afterEachTest() {
        expectedList.clear();
        inputReport.clear();
    }

    @Test
    void convertToTransaction_validDataInput_ok() {
        inputReport.add("b,banana,100");
        inputReport.add("b,apple,10");
        inputReport.add("s,banana,35");
        inputReport.add("r,apple,0");
        inputReport.add("p,banana,40");
        expectedList.add(balanceBanana);
        expectedList.add(balanceApple);
        expectedList.add(supplyBanana);
        expectedList.add(returnApple);
        expectedList.add(purchaseBanana);
        List<FruitTransaction> expected = expectedList;
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputReport);
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_emptyListInput_notOk() {
        List<FruitTransaction> expected = expectedList;
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputReport);
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_nullInputReport_notOk() {
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(null));
    }

    @Test
    void convertToTransaction_greaterThanThreeElementsInLineInput_notOk() {
        inputReport.add("b,banana,35,cocos");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_negativeQuantityInput_notOk() {
        inputReport.add("b,banana,-35");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(inputReport));
    }

    @Test
    void convertToTransaction_fruitIsEmptyInput_notOk() {
        inputReport.add("b,,5");
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(inputReport));
    }
}
