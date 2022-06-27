package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionMapperImplTest {
    private static TransactionMapper transactionMapper;
    private List<String> records;

    @BeforeClass
    public static void setUpBeforeClass() {
        transactionMapper = new TransactionMapperImpl();
    }

    @Test(expected = RuntimeException.class)
    public void map_recordsIsNull_notOk() {
        transactionMapper.map(null);
    }

    @Test(expected = RuntimeException.class)
    public void map_recordsIsEmpty_notOk() {
        records = new ArrayList<>();
        transactionMapper.map(records);
    }

    @Test
    public void map_singleRecord_ok() {
        records = List.of("b,banana,20");
        List<Transaction> actual = transactionMapper.map(records);
        assertTrue(actual.size() == records.size());
    }

    @Test
    public void map_multipleRecords_ok() {
        records = List.of("b,banana,20", "b,apple,100");
        List<Transaction> actual = transactionMapper.map(records);
        assertTrue(actual.size() == records.size());
    }

    @Test(expected = RuntimeException.class)
    public void map_recordIsNull_notOk() {
        records = List.of(null);
        transactionMapper.map(records);
    }

    @Test(expected = RuntimeException.class)
    public void map_recordIsEmpty_notOk() {
        records = List.of("");
        transactionMapper.map(records);
    }

    @Test(expected = RuntimeException.class)
    public void map_recordContainsOneElement_notOk() {
        records = List.of("banana,");
        transactionMapper.map(records);
    }

    @Test(expected = RuntimeException.class)
    public void map_recordContainsTwoElements_notOk() {
        records = List.of("b,banana,");
        transactionMapper.map(records);
    }

    @Test(expected = RuntimeException.class)
    public void map_recordContainsFourElements_notOk() {
        records = List.of("b,banana,20,b");
        transactionMapper.map(records);
    }

    @Test(expected = RuntimeException.class)
    public void map_recordContainsNonExistedOperation_notOk() {
        records = List.of("a,banana,20");
        transactionMapper.map(records);
    }

    @Test(expected = RuntimeException.class)
    public void map_recordContainsEmptyFruitName_notOk() {
        records = List.of("b,,20");
        transactionMapper.map(records);
    }

    @Test(expected = RuntimeException.class)
    public void map_recordContainsNonNumericQuantity_notOk() {
        records = List.of("b,banana,$");
        transactionMapper.map(records);
    }

    @Test(expected = RuntimeException.class)
    public void map_recordContainsNegativeQuantity_notOk() {
        records = List.of("b,banana,-20");
        transactionMapper.map(records);
    }
}

