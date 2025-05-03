package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private static final String VALID_INPUT = "r,banana,20";
    private static final String INVALID_INPUT_DIGIT = "r,banana,b";
    private static final String INVALID_INPUT_PUNCTUATION = "r.banana.b";
    private static final String INVALID_INPUT_WRONG_ORDER = "20,r.banana";
    private static final String HEADER = "type,fruit,quantity";
    private ParserService parserService;
    private List<String> listString;
    private List<Transaction> listTransaction;
    private Transaction transaction;
    private Fruit fruit;

    @Before
    public void setUp() throws Exception {
        listString = new ArrayList<>();
        listString.add(HEADER);
        parserService = new ParserServiceImpl();
        fruit = new Fruit("banana");
        transaction = new Transaction("r", fruit, 20);
        listTransaction = new ArrayList<>();
        listTransaction.add(transaction);
    }

    @Test
    public void parse_validValue_Ok() {
        listString.add(VALID_INPUT);
        List<Transaction> actual = parserService.parse(listString);
        assertEquals(listTransaction, actual);
    }

    @Test (expected = NumberFormatException.class)
    public void parse_inValidValueDigit_notOk() {
        listString.add(INVALID_INPUT_DIGIT);
        parserService.parse(listString);
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void parse_inValidValuePunctuation_notOk() {
        listString.add(INVALID_INPUT_PUNCTUATION);
        parserService.parse(listString);
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void parse_inValidValueWrongOrder_notOk() {
        listString.add(INVALID_INPUT_WRONG_ORDER);
        parserService.parse(listString);
    }

    @After
    public void tearDown() throws Exception {
        listString.clear();
        listTransaction.clear();
    }
}
