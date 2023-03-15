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
    public void createReport_empty_Ok() {
        assertEquals("fruit,quantity\r\n",
                reportCreator.createReport(new ArrayList<>()));
    }

    @Test
    public void createReport_MultipleEntries_Ok() {
        List<FruitEntry> entries = new ArrayList<>();
        entries.add(new FruitEntry("banana", 152));
        entries.add(new FruitEntry("apple", 90));
        assertEquals("fruit,quantity\r\nbanana,152\r\napple,90\r\n",
                reportCreator.createReport(entries));
    }
}
