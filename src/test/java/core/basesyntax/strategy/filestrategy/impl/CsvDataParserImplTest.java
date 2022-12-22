package core.basesyntax.strategy.filestrategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.filestrategy.DataParser;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvDataParserImplTest {
    private static DataParser dataParser;

    @BeforeClass
    public static void beforeClass() {
        dataParser = new CsvDataParserImpl();
    }

    @Test
    public void parseData_validInput_ok() {
        FruitTransaction transactionOne = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(FruitTransaction.Operation.BALANCE)
                .setFruit("Apple")
                .setQuantity(10).build();
        FruitTransaction transactionTwo = new FruitTransaction.FruitTransactionBuilder()
                .setOperation(FruitTransaction.Operation.SUPPLY)
                .setFruit("Apple")
                .setQuantity(20).build();
        List<FruitTransaction> expected = List.of(transactionOne, transactionTwo);
        String inputData = "header" + System.lineSeparator()
                + "b,Apple,10" + System.lineSeparator()
                + "s,Apple,20";
        List<FruitTransaction> actual = dataParser.parseData(inputData);
        assertEquals(String.format("Should return List:%n%s when input is:%n%s%n but was:%n%s",
                expected, inputData, actual), expected, actual);
    }

    @Test
    public void parseData_emptyInput_ok() {
        List<FruitTransaction> expected = Collections.emptyList();
        String inputData = "";
        List<FruitTransaction> actual = dataParser.parseData(inputData);
        assertEquals("Should return empty List when input string is empty, "
                + "but was " + actual, expected, actual);
    }

    @Test
    public void parseData_nullInput_notOk() {
        List<FruitTransaction> expected = Collections.emptyList();
        List<FruitTransaction> actual = dataParser.parseData(null);
        assertEquals("Should return empty List when input string is null, "
                + "but was " + actual, expected, actual);
    }
}
