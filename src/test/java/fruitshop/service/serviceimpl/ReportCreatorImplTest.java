package fruitshop.service.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fruitshop.service.ReportCreator;
import fruitshop.storage.Storage;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
        Storage.setStorage(new HashMap<>());
    }

    @Test
    void createReport_validCase_ok() {
        Storage.getStorage().put("apple", 76);
        Storage.getStorage().put("banana", 110);
        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,110" + System.lineSeparator()
                + "apple,76" + System.lineSeparator();
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void createReport_emptyListAsParameter_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }
}
