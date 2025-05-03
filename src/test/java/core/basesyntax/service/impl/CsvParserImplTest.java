package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.CsvParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvParserImplTest {
    private static CsvParser csvParserImpl;
    private static Transaction transaction;
    private static List<String> parsingData;
    private static final String FRUIT = "orange";
    private static final String CSV_PART = "s,orange,33";
    private static final String INVALID_CSV_PART = "q,orange,33";
    private static final String HEADER = "type,fruit,quantity";
    private static final int AMOUNT = 33;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        csvParserImpl = new CsvParserImpl();
        transaction = new Transaction(Transaction.Operation.SUPPLY, FRUIT, AMOUNT);
        parsingData = new ArrayList<>();
    }

    @Before
    public void setUp() {
        parsingData.add(HEADER);
        parsingData.add(CSV_PART);
    }

    @After
    public void tearDown() {
        parsingData.clear();
    }

    @Test
    public void parse_emptyList_Ok() {
        assertTrue(csvParserImpl.parse(new ArrayList<>()).isEmpty());
    }

    @Test
    public void parse_invalidOperationAsArgument_notOk() {
        thrown.expect(RuntimeException.class);
        parsingData.add(HEADER);
        parsingData.add(INVALID_CSV_PART);
        csvParserImpl.parse(parsingData);
    }

    @Test
    public void parse_parserCanReadFruitType_Ok() {
        assertEquals(transaction.getFruit(), csvParserImpl.parse(parsingData).get(0).getFruit());
    }

    @Test
    public void parse_parserCanReadQuantity_Ok() {
        assertEquals(transaction.getQuantity(),
                csvParserImpl.parse(parsingData).get(0).getQuantity());
    }

    @Test
    public void parse_parserCanReadOperation_Ok() {
        assertEquals(transaction.getOperation(),
                csvParserImpl.parse(parsingData).get(0).getOperation());
    }

    @Test
    public void parse_nullAsArgument_notOk() {
        thrown.expect(NullPointerException.class);
        csvParserImpl.parse(null);
    }
}

