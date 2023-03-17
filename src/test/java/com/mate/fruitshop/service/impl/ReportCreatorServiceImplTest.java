package com.mate.fruitshop.service.impl;

import static org.junit.Assert.assertEquals;

import com.mate.fruitshop.model.FruitEntry;
import com.mate.fruitshop.service.ReportCreatorService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreator =
            new ReportCreatorServiceImpl();

    @Test
    public void createReport_Empty_Ok() {
        assertEquals("fruit,quantity" + System.lineSeparator(),
                reportCreator.createReport(new ArrayList<>()));
    }

    @Test
    public void createReport_MultipleEntries_Ok() {
        List<FruitEntry> entries = new ArrayList<>();
        entries.add(new FruitEntry("banana", 152));
        entries.add(new FruitEntry("apple", 90));
        assertEquals("fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator(),
                reportCreator.createReport(entries));
    }
}
