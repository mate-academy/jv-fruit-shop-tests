package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Database;
import core.basesyntax.db.DatabaseDaoService;
import core.basesyntax.db.impl.DatabaseDaoServiceImpl;
import core.basesyntax.service.impl.ReportCreationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportCreationServiceImplTest {
    private static final String CSV_PUNCTUATION_MARK = ",";
    private static final String CSV_FIRST_LINE = "product,quantity" + System.lineSeparator();
    private static final String PRODUCT = "apple";
    private static final Integer AMOUNT_OF_PRODUCT = 100;
    private static final String NORMAL_RESULT = CSV_FIRST_LINE + PRODUCT + CSV_PUNCTUATION_MARK
            + AMOUNT_OF_PRODUCT + System.lineSeparator();
    private static final DatabaseDaoService databaseDaoService = new DatabaseDaoServiceImpl();
    private static final StringBuilder reportStringBuilder = new StringBuilder(CSV_FIRST_LINE);
    private ReportCreationServiceImpl reportCreationService;

    @BeforeEach
    public void setUp() {
        reportCreationService = new ReportCreationServiceImpl(databaseDaoService,
                reportStringBuilder);
    }

    @Test
    public void createReport_emptyStorage_ok() {
        assertEquals(CSV_FIRST_LINE, reportCreationService.createReport());
    }

    @Test
    public void createReport_dataInStorage_ok() {
        Database.database.put(PRODUCT, AMOUNT_OF_PRODUCT);
        assertEquals(NORMAL_RESULT, reportCreationService.createReport());
    }

    @AfterEach
    public void afterEach() {
        Database.database.clear();
    }
}
