package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParserService;
import java.util.List;
import org.junit.Test;

public class FruitTransactionParserServiceImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String GRAPE = "grape";
    private static final FruitTransactionParserService
            fruitTransactionParserService = new FruitTransactionParserServiceImpl();

    @Test
    public void parseData_ValidInput_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 1),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 99),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, GRAPE, 400),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 18),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, APPLE, 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, GRAPE, 258),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, GRAPE, 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 100),
                new FruitTransaction(FruitTransaction.Operation.RETURN, GRAPE, 7785)
        );
        String input = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,1" + System.lineSeparator()
                + "b,apple,99" + System.lineSeparator()
                + "b,grape,400" + System.lineSeparator()
                + "s,banana,18" + System.lineSeparator()
                + "s,apple,100" + System.lineSeparator()
                + "s,grape,258" + System.lineSeparator()
                + "p,banana,20" + System.lineSeparator()
                + "p,apple,100" + System.lineSeparator()
                + "p,grape,13" + System.lineSeparator()
                + "r,banana,20" + System.lineSeparator()
                + "r,apple,100" + System.lineSeparator()
                + "r,grape,7785";
        List<FruitTransaction> actual = fruitTransactionParserService.parseData(input);
        assertEquals("Method should return List<FruitTransaction>: ",
                expected, actual);
    }

    @Test(expected = InvalidDataException.class)
    public void parseData_inputOneLine_notOk() {
        fruitTransactionParserService.parseData("invalidInput");
    }

    @Test(expected = NumberFormatException.class)
    public void parseData_thirdPartIsNotInteger_notOk() {
        String input = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,notInteger" + System.lineSeparator();
        fruitTransactionParserService.parseData(input);
    }

    @Test(expected = InvalidDataException.class)
    public void parseData_invalidOperationLetter_notOk() {
        String input = "type,fruit,quantity" + System.lineSeparator()
                + "x,banana,10" + System.lineSeparator();
        fruitTransactionParserService.parseData(input);
    }

    @Test(expected = InvalidDataException.class)
    public void parseData_nullInput_notOk() {
        fruitTransactionParserService.parseData(null);
    }

    @Test(expected = InvalidDataException.class)
    public void parseData_emptyInput_notOk() {
        fruitTransactionParserService.parseData("");
    }
}
