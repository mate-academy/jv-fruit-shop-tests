package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class CreateReportImplTest {
    private static final CreateReportImpl createReport = new CreateReportImpl();

    @After
    public void tearDown() throws Exception {
        Storage.fruitStorage.clear();
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        String expected = "fruit,quantity";
        String actual = createReport.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_Ok() {
        Storage.fruitStorage.put("banana", 10);
        Storage.fruitStorage.put("apple", 20);
        Storage.fruitStorage.put("pineapple", 30);
        Storage.fruitStorage.put("orange", 40);
        List<String> expected = new ArrayList<>(List.of("fruit,quantity",
                "banana,10",
                "apple,20",
                "pineapple,30",
                "orange,40"));
        Collections.sort(expected);
        String actualString = createReport.createReport();
        List<String> actual = new ArrayList<>(List.of(actualString.split(System.lineSeparator())));
        Collections.sort(actual);
        assertEquals(expected, actual);
    }
}
