package core.basesyntax.impl;

import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String[] head = {
            "fruits", "quantity\r\n"
    };
    private ReportService reportService;

    private List<String[]> convertedList;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        convertedList = new ArrayList<>();
        String[] value = {"banana", "152\r\n"};
        String[] value2 = {"apple,90"};
        convertedList.add(head);
        convertedList.add(value);
        convertedList.add(value2);
    }

    @Test
    void reportService_createReport_Ok() {
        String expected = "fruits,quantity\r\n"
                + "banana,152\r\n" + "apple,90";
        String actual = reportService.createReport(convertedList);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void reportService_emptyConvertedList() {
        List<String[]> convertedList = new ArrayList<>();
        Assertions.assertThrows(RuntimeException.class, ()
                -> reportService.createReport(convertedList));
    }

    @Test
    void reportService_covetedListNull_notOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> reportService.createReport(null));
    }
}
