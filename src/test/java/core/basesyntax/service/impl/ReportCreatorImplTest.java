package core.basesyntax.service.impl;

import core.basesyntax.service.ReportCreator;
import core.basesyntax.storage.Storage;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static final String HEADER = "fruit,quantity" + System.lineSeparator();
    private static ReportCreator reportCreator;
    private static Map<String, Integer> oneRecordReport;
    private static Map<String, Integer> fiveRecordsReport;
    private static String oneRecordExpectedString;
    private static String fiveRecordsExpectedString;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreatorImpl();
        oneRecordReport = Map.of("pineapple", 100);
        oneRecordExpectedString = HEADER + "pineapple,100";
        fiveRecordsReport = Map.of("pineapple", 100,
                "strawberry", 50,
                "watermelon", 10,
                "raspberry", 17,
                "peach", 31);
        fiveRecordsExpectedString = HEADER
                + "peach,31" + System.lineSeparator()
                + "pineapple,100" + System.lineSeparator()
                + "raspberry,17" + System.lineSeparator()
                + "strawberry,50" + System.lineSeparator()
                + "watermelon,10";
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void create_emptyStorage_ok() {
        String result = reportCreator.create();
        Assertions.assertEquals(HEADER, result);
    }

    @Test
    void create_oneRecord_ok() {
        Storage.fruits.putAll(oneRecordReport);
        String result = reportCreator.create();
        Assertions.assertEquals(oneRecordExpectedString, result);
    }

    @Test
    void create_fiveRecords_ok() {
        Storage.fruits.putAll(fiveRecordsReport);
        String result = reportCreator.create();
        String sortedResult = Arrays.stream(result.split(System.lineSeparator()))
                .sorted()
                .collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(fiveRecordsExpectedString, sortedResult);
    }
}
