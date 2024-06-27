package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportCreator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static final String HEADER = "fruit, quantity";
    private final ReportCreator reportCreator = new ReportCreatorImpl();

    @Test
    void createReport_correctTransaction_ok() {
        Map<String, Integer> correctTransactionList = new HashMap<>();
        correctTransactionList.put("banana", 50);
        correctTransactionList.put("orange", 55);
        correctTransactionList.put("apple", 30);

        ArrayList<String> expectedData = new ArrayList<>();
        expectedData.add(HEADER);
        expectedData.add("banana,50");
        expectedData.add("orange,55");
        expectedData.add("apple,30");

        List<String> actual = reportCreator.createReport(correctTransactionList);

        assertEquals(expectedData, actual);
    }

    @Test
    void createReport_emptyTransaction_notOk() {
        Map<String, Integer> correctTransactionList = new HashMap<>();

        ArrayList<String> expectedData = new ArrayList<>();
        expectedData.add(HEADER);

        List<String> actual = reportCreator.createReport(correctTransactionList);

        assertEquals(expectedData, actual);
    }

    @Test
    void createReport_transactionsZeroQty_ok() {
        Map<String, Integer> correctTransactionList = new HashMap<>();
        correctTransactionList.put("banana", 0);
        correctTransactionList.put("orange", 0);
        correctTransactionList.put("apple", 0);

        ArrayList<String> expectedData = new ArrayList<>();
        expectedData.add(HEADER);
        expectedData.add("banana,0");
        expectedData.add("orange,0");
        expectedData.add("apple,0");

        List<String> actual = reportCreator.createReport(correctTransactionList);

        assertEquals(expectedData, actual);
    }
}
