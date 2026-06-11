package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.impl.CsvDataConverterImpl;
import core.basesyntax.util.CsvUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String DEFAULT_FRUIT = "banana";
    private static final Integer DEFAULT_QUANTITY = 20;
    private static final FruitTransaction.Operation DEFAULT_OPERATION =
            FruitTransaction.Operation.BALANCE;
    private static final String UNKNOWN_OPERATION_TYPE = "u";
    private static DataConverter converter;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    public static void setUp() {
        converter = new CsvDataConverterImpl();
    }

    @BeforeEach
    public void beforeEach() {
        fruitTransaction = new FruitTransaction(DEFAULT_OPERATION, DEFAULT_FRUIT, DEFAULT_QUANTITY);
    }

    @Test
    public void convertToTransactionList_Null_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransactionList(null));
    }

    @Test
    public void convertToTransactionList_Empty_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> converter.convertToTransactionList(new ArrayList<>()));
    }

    @Test
    public void convertToTransactionList_OnlyHeader_Ok() {
        List<String> header = new ArrayList<>();
        header.add(HEADER);
        assertTrue(converter.convertToTransactionList(header).isEmpty());
    }

    @Test
    public void convertToTransactionList_Complete_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        lines.add(lineFromFruitTransaction(fruitTransaction.getOperation().getCode(),
                fruitTransaction.getFruit(), fruitTransaction.getQuantity()));
        List<FruitTransaction> transactions = converter.convertToTransactionList(lines);
        assertEquals(1, transactions.size());
        assertNotNull(transactions.get(0));
        assertEquals(transactions.get(0).getFruit(), DEFAULT_FRUIT);
        assertEquals(transactions.get(0).getOperation(), DEFAULT_OPERATION);
        assertEquals(transactions.get(0).getQuantity(), DEFAULT_QUANTITY);
    }

    @Test
    public void convertToTransactionList_UnknownOperation_NotOk() {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        lines.add(lineFromFruitTransaction(UNKNOWN_OPERATION_TYPE, fruitTransaction.getFruit(),
                fruitTransaction.getQuantity()));
        assertThrows(RuntimeException.class, () -> converter.convertToTransactionList(lines));
    }

    @Test
    public void convertToTransactionList_EmptyLine_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        lines.add("");
        assertTrue(converter.convertToTransactionList(lines).isEmpty());
    }

    private String lineFromFruitTransaction(String operation, String fruit, int quantity) {
        return operation
                + CsvUtil.CSV_SEPARATOR + fruit
                + CsvUtil.CSV_SEPARATOR + quantity;
    }
}
