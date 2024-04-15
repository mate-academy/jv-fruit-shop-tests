package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private final ReportCreator reportCreator =
            new ReportCreatorImpl(new FruitTransactionDaoImpl());

    @AfterEach
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void create_normalData_ok() {
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("apple", 5);
        Storage.fruits.put("something", 10);

        String expect = "fruit,quantity\n"
                + "banana,10\n"
                + "apple,5\n"
                + "something,10";

        String actual = reportCreator.create();

        assertEquals(expect, actual);
    }

    @Test
    public void create_empty_ok() {
        String expect = "fruit,quantity\n";

        String actual = reportCreator.create();

        assertEquals(expect, actual);
    }

    @Test
    public void create_nullValue_ok() {
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("apple", null);
        Storage.fruits.put("strawberry", 30);

        String expect = "fruit,quantity\n"
                + "banana,10\n"
                + "apple,null\n"
                + "strawberry,30";

        String actual = reportCreator.create();

        assertEquals(expect, actual);
    }

    @Test
    public void create_nullKey_ok() {
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("apple", 20);
        Storage.fruits.put(null, 20);
        Storage.fruits.put("strawberry", 30);

        String expect = "fruit,quantity\n"
                + "banana,10\n"
                + "apple,20\n"
                + "strawberry,30";

        String actual = reportCreator.create();

        assertEquals(expect, actual);
    }
}
