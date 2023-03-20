package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DataParserServiceImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String INVALID_OPERATION = "q";
    private static final String INVALID_TRANSACTION_LINE = "q,apple,0";
    private static final String VALID_TRANSACTION_LINE = "b,apple,80";
    private static DataParserService dataParser;
    private static List<String> dataToParse;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        dataParser = new DataParserServiceImpl();
    }

    @Before
    public void setUp() throws Exception {
        dataToParse = new ArrayList<>();
    }

    @Test
    public void parseDataToFruitTransaction_InvalidOperationInput_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Invalid operation type " + INVALID_OPERATION);
        dataToParse.add(HEADER);
        dataToParse.add(INVALID_TRANSACTION_LINE);
        dataParser.parseDataToFruitTransaction(dataToParse);
    }

    @Test
    public void parseDataToFruitTransaction_ValidDataInput_Ok() {
        dataToParse.add(HEADER);
        dataToParse.add(VALID_TRANSACTION_LINE);
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "apple", 80);
        List<FruitTransaction> actual = dataParser.parseDataToFruitTransaction(dataToParse);
        FruitTransaction actualTransaction = actual.get(0);
        assertEquals(expected, actualTransaction);
    }
}
