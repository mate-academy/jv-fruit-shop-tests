package core.fruitshop.service.impl;


import static junit.framework.TestCase.assertEquals;
import core.fruitshop.db.Storage;
import core.fruitshop.model.Fruit;
import core.fruitshop.service.ReportCreator;
import org.junit.After;
import org.junit.Test;

public class ReportCreatorImplTest {
        private final ReportCreator reportCreator = new ReportCreatorImpl();

        @Test
        public void createReport_validData_ok() {
            Storage.fruitsStorage.put(new Fruit("Apple"), 50);
            Storage.fruitsStorage.put(new Fruit("Peach"), 100);
            String expected = "fruit,quantity" + System.lineSeparator()
                + "Apple,50" + System.lineSeparator()
                + "Peach,100";
            assertEquals(expected, reportCreator.createReport());
        }

        @Test
        public void createReport_noData_ok() {
            String expected = "fruit,quantity";
            assertEquals(expected, reportCreator.createReport());
        }

        @After
        public void tearDown() {
            Storage.fruitsStorage.clear();
        }
    }