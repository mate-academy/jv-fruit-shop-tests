package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportCreator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private final ReportCreator reportCreator = new ReportCreatorImpl();
    private final List<String> report = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        fruitStorage.clear();
    }

    @Test
    void create_validInput_Ok() {
        String banana = "banana";
        String apple = "apple";
        int bananaCount = 10;
        int appleCount = 20;
        report.add("fruit,quantity");
        report.add(banana + "," + bananaCount);
        report.add(apple + "," + appleCount);
        fruitStorage.put("banana", 10);
        fruitStorage.put("apple", 20);
        assertEquals(report, reportCreator.create());
    }

    @Test
    void create_fruitStorageEmpty_Ok() {
        report.add("fruit,quantity");
        assertEquals(report, reportCreator.create());
    }
}
