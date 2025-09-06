package core.basesyntax.converter;

import static core.basesyntax.model.TypeOfTransaction.B;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.TypeOfTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConverterImplTest {

    private static final ConverterService converterService = new ConverterImpl();

    @Test
    void convertStringToTransaction_ok() {
        FruitTransaction transactionNo1 = new FruitTransaction();
        transactionNo1.setTypeOfTransaction(TypeOfTransaction.S);
        transactionNo1.setFruit("banana");
        transactionNo1.setQuantity(25);
        FruitTransaction transactionNo2 = new FruitTransaction();
        transactionNo2.setTypeOfTransaction(TypeOfTransaction.R);
        transactionNo2.setFruit("apple");
        transactionNo2.setQuantity(20);
        FruitTransaction transactionNo3 = new FruitTransaction();
        transactionNo3.setTypeOfTransaction(TypeOfTransaction.P);
        transactionNo3.setFruit("banana");
        transactionNo3.setQuantity(10);
        List<String> listOfLines = new ArrayList<>();
        String lineNo1 = "s,banana,25";
        String lineNo2 = "r,apple,20";
        String lineNo3 = "p,banana,10";
        listOfLines.add(lineNo1);
        listOfLines.add(lineNo2);
        listOfLines.add(lineNo3);
        List<FruitTransaction> actualList = converterService
                .convertStringToTransaction(listOfLines);
        List<FruitTransaction> resultList = new ArrayList<>();
        resultList.add(transactionNo1);
        resultList.add(transactionNo2);
        resultList.add(transactionNo3);
        for (int i = 0; i < listOfLines.size(); i++) {
            Assertions.assertEquals(resultList.get(i), actualList.get(i));
        }
    }

    @Test
    void convertStringToTransaction_nullLines_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> converterService.convertStringToTransaction(null));
    }

    @Test
    void convertStringToTransaction_LineIsMalformed_notOk() {
        String malformedLine = "b, orange, 12, name";
        List<String> lines = new ArrayList<>();
        lines.add(malformedLine);

        Assertions.assertThrows(RuntimeException.class,
                () -> converterService.convertStringToTransaction(lines));
    }

    @Test
    void convertStringToTransaction_TypeOfTransactionIsIncorrect_notOk() {
        String lineWithWrongType = "n,banana,40";
        List<String> lines = new ArrayList<>();
        lines.add(lineWithWrongType);
        Assertions.assertThrows(RuntimeException.class,
                () -> converterService.convertStringToTransaction(lines));
    }

    @Test
    void convertStringToTransaction_NameOfFruitIsNull_notOk() {
        String lineWithNullFruit = "b, ,40";
        List<String> lines = new ArrayList<>();
        lines.add(lineWithNullFruit);
        Assertions.assertThrows(RuntimeException.class,
                () -> converterService.convertStringToTransaction(lines));
    }

    @Test
    void convertStringToTransaction_QuantityIsNegative_notOk() {
        String lineWithWrongType = "b,banana,-120";
        List<String> lines = new ArrayList<>();
        lines.add(lineWithWrongType);
        Assertions.assertThrows(RuntimeException.class,
                () -> converterService.convertStringToTransaction(lines));
    }

    @Test
    void convertStringToTransaction_skipOverhead_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setTypeOfTransaction(B);
        transaction.setFruit("banana");
        transaction.setQuantity(120);
        List<String> lines = new ArrayList<>();
        String overHead = "type,fruit,quantity";
        lines.add(overHead);
        String transactionString = "b,banana,120";
        lines.add(transactionString);
        FruitTransaction firstTransactionInListAfterMethod = converterService
                .convertStringToTransaction(lines).get(0);
        Assertions.assertEquals(transaction,firstTransactionInListAfterMethod);
    }

    @Test
    void convertStringToTransaction_areWhiteSpacesRemoved_ok() {
        String lineWithNullFruit = " b , apple ,  40  ";
        List<String> lines = new ArrayList<>();
        lines.add(lineWithNullFruit);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setTypeOfTransaction(B);
        transaction.setFruit("apple");
        transaction.setQuantity(40);
        FruitTransaction resultTransactionInList = converterService
                .convertStringToTransaction(lines).get(0);
        Assertions.assertEquals("apple", resultTransactionInList.getFruit());
    }
}
