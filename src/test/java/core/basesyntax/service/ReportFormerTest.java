package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportFormerTest {
    private static final int FIRST_FRUIT_QUANTITY = 42;
    private static final int SECOND_FRUIT_QUANTITY = 10;
    private static final ReportFormerImpl reportFormer = new ReportFormerImpl();
    private static final String FIRST_EXPECTED_LINE = "fruit,42";
    private static final String FIRST_FRUIT_NAME = "fruit";
    private static final String SECOND_EXPECTED_LINE = "peach,10";
    private static final String SECOND_FRUIT_NAME = "peach";
    private static final String HEADLINE = "fruit,quantity";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @BeforeEach
    public void clearStorage() {
        Storage.reportData.clear();
    }

    @Test
    public void reportFormer_emptyDb_Ok() {
        assertEquals(HEADLINE, reportFormer.formReport());
    }

    @Test
    public void reportFormer_singleDataStorage_Ok() {
        Storage.reportData.put(FIRST_FRUIT_NAME, FIRST_FRUIT_QUANTITY);

        assertEquals(String.format(HEADLINE
                + LINE_SEPARATOR + FIRST_EXPECTED_LINE),
                reportFormer.formReport());
    }

    @Test
    public void reportFormer_MultipleDataStorage_Ok() {
        Storage.reportData.put(FIRST_FRUIT_NAME, FIRST_FRUIT_QUANTITY);
        Storage.reportData.put(SECOND_FRUIT_NAME, SECOND_FRUIT_QUANTITY);

        assertEquals(String.format(
                HEADLINE + LINE_SEPARATOR + FIRST_EXPECTED_LINE + LINE_SEPARATOR
                + SECOND_EXPECTED_LINE), reportFormer.formReport());
    }
}
