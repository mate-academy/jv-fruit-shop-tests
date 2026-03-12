package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    public void convertToTransaction_emptyReport_notOk() {
        List<String> report = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(report);
        });
    }

    @Test
    public void convertToTransaction_nullReport_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(null);
        });
    }

    @Test
    public void convertToTransaction_reportSizeIsOne_notOk() {
        List<String> report = new ArrayList<>();
        report.add("type,fruit,quantity");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(report);
        });
    }

    @Test
    public void convertToTransaction_linePartsIsTwo_notOk() {
        List<String> report = new ArrayList<>();
        report.add("type,fruit,quantity");
        report.add("b,banana");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(report);
        });
    }

    @Test
    public void convertToTransaction_linePartsIsFour_notOk() {
        List<String> report = new ArrayList<>();
        report.add("type,fruit,quantity");
        report.add("b,banana, 30, abc");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(report);
        });
    }

    @Test
    public void convertToTransaction_emptyFruitName_notOk() {
        List<String> report = new ArrayList<>();
        report.add("type,fruit,quantity");
        report.add("b,banana,20");
        report.add("b,,30");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(report);
        });
    }

    @Test
    public void convertToTransaction_emptyQuantity_notOk() {
        List<String> report = new ArrayList<>();
        report.add("type,fruit,quantity");
        report.add("b,banana,");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(report);
        });
    }

    @Test
    public void convertToTransaction_wrongFormatOfQuantity_notOk() {
        List<String> report = new ArrayList<>();
        report.add("type,fruit,quantity");
        report.add("b,banana, ");
        assertThrows(RuntimeException.class, () -> {
            dataConverter.convertToTransaction(report);
        });
    }

    @Test
    public void convertToTransaction_unknownOperation_notOk() {
        List<String> report = new ArrayList<>();
        report.add("type,fruit,quantity");
        report.add("q,banana,20");
        assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(report);
        });
    }

    @Test
    public void convertToTransaction_correctData_ok() {
        List<String> report = new ArrayList<>();
        report.add("type,fruit,quantity");
        report.add("b,banana,20");
        report.add("b,apple,100");
        report.add("s,banana,100");
        FruitTransaction expectedFruitTransactionFirst = new FruitTransaction();
        expectedFruitTransactionFirst.setOperation(FruitTransaction.Operation.BALANCE);
        expectedFruitTransactionFirst.setFruit("banana");
        expectedFruitTransactionFirst.setQuantity(20);
        FruitTransaction expectedFruitTransactionSecond = new FruitTransaction();
        expectedFruitTransactionSecond.setOperation(FruitTransaction.Operation.BALANCE);
        expectedFruitTransactionSecond.setFruit("apple");
        expectedFruitTransactionSecond.setQuantity(100);
        FruitTransaction expectedFruitTransactionThird = new FruitTransaction();
        expectedFruitTransactionThird.setOperation(FruitTransaction.Operation.SUPPLY);
        expectedFruitTransactionThird.setFruit("banana");
        expectedFruitTransactionThird.setQuantity(100);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(expectedFruitTransactionFirst);
        expected.add(expectedFruitTransactionSecond);
        expected.add(expectedFruitTransactionThird);
        List<FruitTransaction> actual = dataConverter.convertToTransaction(report);
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getOperation(), actual.get(i).getOperation());
            assertEquals(expected.get(i).getFruit(), actual.get(i).getFruit());
            assertEquals(expected.get(i).getQuantity(), actual.get(i).getQuantity());
        }
    }
}
