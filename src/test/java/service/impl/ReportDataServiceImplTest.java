package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ReaderDataService;
import service.ReportDataService;

public class ReportDataServiceImplTest {
    private static String EXPECTED_STRING;
    private static Map<String, Integer> DATA_FOR_PARSE;
    private static ReportDataService reportDataService;
    private static ReaderDataService readerDataService;

    @BeforeClass
    public static void beforeClass() {
        DATA_FOR_PARSE = new HashMap<>();
        readerDataService = new CsvFileReaderService();
        reportDataService = new ReportDataServiceImpl();
        EXPECTED_STRING = "fruit,quantity\n"
                + "banana,152\n"
                + "apple,90";
        DATA_FOR_PARSE.put("banana", 152);
        DATA_FOR_PARSE.put("apple", 90);
    }

    @Test
    public void report_Ok() {
        String actual = reportDataService.creatReport(DATA_FOR_PARSE);
        assertEquals(EXPECTED_STRING, actual);
    }
}
