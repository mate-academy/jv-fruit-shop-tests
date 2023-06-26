package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitTransactionParserImplTest {
    private static final String HEADER = "type;fruit;quantity";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final int QUANTITY1 = 50;
    private static final int QUANTITY2 = 100;
    private static final int QUANTITY3 = 30;
    private static final int QUANTITY4 = 10;
    private final FruitTransactionParserImpl fruitTransactionParser =
            new FruitTransactionParserImpl();
    private final FruitTransaction fruitTransaction1 = new FruitTransaction(FruitTransaction
            .Operation.BALANCE, BANANA, QUANTITY1);
    private final String readLine1 = "b;banana;50";
    private final FruitTransaction fruitTransaction2 = new FruitTransaction(FruitTransaction
            .Operation.BALANCE, APPLE, QUANTITY2);
    private final String readLine2 = "b;apple;100";
    private final FruitTransaction fruitTransaction3 = new FruitTransaction(FruitTransaction
            .Operation.BALANCE, ORANGE, QUANTITY1 + QUANTITY2);
    private final String readLine3 = "b;orange;150";
    private final FruitTransaction fruitTransaction4 = new FruitTransaction(FruitTransaction
            .Operation.SUPPLY, BANANA, QUANTITY3);
    private final String readLine4 = "s;banana;30";
    private final FruitTransaction fruitTransaction5 = new FruitTransaction(FruitTransaction
            .Operation.SUPPLY, APPLE, QUANTITY3 + QUANTITY4);
    private final String readLine5 = "s;apple;40";
    private final FruitTransaction fruitTransaction6 = new FruitTransaction(FruitTransaction
            .Operation.SUPPLY, ORANGE, QUANTITY1);
    private final String readLine6 = "s;orange;50";
    private final FruitTransaction fruitTransaction7 = new FruitTransaction(FruitTransaction
            .Operation.PURCHASE, BANANA, QUANTITY3);
    private final String readLine7 = "p;banana;30";
    private FruitTransaction fruitTransaction8 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 40);
    private final String readLine8 = "p;apple;40";
    private final FruitTransaction fruitTransaction9 = new FruitTransaction(FruitTransaction
            .Operation.PURCHASE, ORANGE, QUANTITY1);
    private final String readLine9 = "p;orange;50";
    private final FruitTransaction fruitTransaction10 = new FruitTransaction(FruitTransaction
            .Operation.RETURN, BANANA, QUANTITY4);
    private final String readLine10 = "r;banana;10";
    private final FruitTransaction fruitTransaction11 = new FruitTransaction(FruitTransaction
            .Operation.RETURN, APPLE, QUANTITY4);
    private final String readLine11 = "r;apple;10";
    private final FruitTransaction fruitTransaction12 = new FruitTransaction(FruitTransaction
            .Operation.RETURN, ORANGE, QUANTITY4);
    private final String readLine12 = "r;orange;10";

    @Test
    void getFruitTransactionsFromEmptyListOfOperations_notOk() {
        List<String> inputStoreActivities = new ArrayList<>();
        assertThrows(RuntimeException.class,
                () -> fruitTransactionParser.parseFruitTransaction(inputStoreActivities).isEmpty());
    }

    @Test
    void getFruitTransactionsFromListWithNoFruits_notOk() {
        List<String> inputListNoFruits = new ArrayList<>(List.of(HEADER));
        assertThrows(RuntimeException.class,
                () -> fruitTransactionParser.parseFruitTransaction(inputListNoFruits));
    }

    @Test
    void getFruitTransactionsFromCorrectListOfOperations_Ok() {
        List<FruitTransaction> list1 = List.of(fruitTransaction1, fruitTransaction4,
                fruitTransaction7, fruitTransaction10);
        List<String> inputList1 = new ArrayList<>(Arrays.asList(HEADER, readLine1, readLine4,
                readLine7, readLine10));
        List<FruitTransaction> list2 = List.of(fruitTransaction2, fruitTransaction5,
                fruitTransaction8, fruitTransaction11);
        List<String> inputList2 = new ArrayList<>(Arrays.asList(HEADER, readLine2, readLine5,
                readLine8, readLine11));
        List<FruitTransaction> list3 = List.of(fruitTransaction3, fruitTransaction6,
                fruitTransaction9, fruitTransaction12);
        List<String> inputList3 = new ArrayList<>(Arrays.asList(HEADER, readLine3, readLine6,
                readLine9, readLine12));
        List<FruitTransaction> list4 = List.of(fruitTransaction1, fruitTransaction2,
                fruitTransaction3, fruitTransaction4);
        List<String> inputList4 = new ArrayList<>(Arrays.asList(HEADER, readLine1, readLine2,
                readLine3, readLine4));
        List<FruitTransaction> list5 = List.of(fruitTransaction5, fruitTransaction6,
                fruitTransaction7, fruitTransaction8);
        List<String> inputList5 = new ArrayList<>(Arrays.asList(HEADER, readLine5, readLine6,
                readLine7, readLine8));
        List<FruitTransaction> list6 = List.of(fruitTransaction9, fruitTransaction10,
                fruitTransaction11, fruitTransaction12);
        List<String> inputList6 = new ArrayList<>(Arrays.asList(HEADER, readLine9, readLine10,
                readLine11, readLine12));

        assertAll(
                () -> assertEquals(list1, fruitTransactionParser.parseFruitTransaction(inputList1),
                        "Data are different"),
                () -> assertEquals(list2, fruitTransactionParser.parseFruitTransaction(inputList2),
                        "Data are different"),
                () -> assertEquals(list3, fruitTransactionParser.parseFruitTransaction(inputList3),
                        "Data are different"),
                () -> assertEquals(list4, fruitTransactionParser.parseFruitTransaction(inputList4),
                        "Data are different"),
                () -> assertEquals(list5, fruitTransactionParser.parseFruitTransaction(inputList5),
                        "Data are different"),
                () -> assertEquals(list6, fruitTransactionParser.parseFruitTransaction(inputList6),
                        "Data are different")
        );
    }

    @Test
    void getTransactionFromListWithIncorrectRecord_notOk() {
        String incorrectRecord1 = null;
        String incorrectRecord2 = "";
        String incorrectRecord3 = "p;orange";
        String incorrectRecord4 = "p;50";
        String incorrectRecord5 = "orange;50";
        String incorrectRecord6 = "p;orange;null";
        String incorrectRecord7 = "p/orange/50";
        List<String> incorrectList1 = new ArrayList<>(Arrays.asList(HEADER, readLine1, readLine2,
                readLine3, incorrectRecord1));
        List<String> incorrectList2 = new ArrayList<>(Arrays.asList(HEADER, readLine1, readLine2,
                readLine3, incorrectRecord2));
        List<String> incorrectList3 = new ArrayList<>(Arrays.asList(HEADER, readLine1, readLine2,
                readLine3, incorrectRecord3));
        List<String> incorrectList4 = new ArrayList<>(Arrays.asList(HEADER, readLine1, readLine2,
                readLine3, incorrectRecord4));
        List<String> incorrectList5 = new ArrayList<>(Arrays.asList(HEADER, readLine1, readLine2,
                readLine3, incorrectRecord5));
        List<String> incorrectList6 = new ArrayList<>(Arrays.asList(HEADER, readLine1, readLine2,
                readLine3, incorrectRecord6));
        List<String> incorrectList7 = new ArrayList<>(Arrays.asList(HEADER, readLine1, readLine2,
                readLine3, incorrectRecord7));

        assertAll(
                () -> assertThrows(RuntimeException.class, () -> fruitTransactionParser
                        .parseFruitTransaction(incorrectList1)),
                () -> assertThrows(RuntimeException.class, () -> fruitTransactionParser
                        .parseFruitTransaction(incorrectList2)),
                () -> assertThrows(RuntimeException.class, () -> fruitTransactionParser
                        .parseFruitTransaction(incorrectList3)),
                () -> assertThrows(RuntimeException.class, () -> fruitTransactionParser
                        .parseFruitTransaction(incorrectList4)),
                () -> assertThrows(RuntimeException.class, () -> fruitTransactionParser
                        .parseFruitTransaction(incorrectList5)),
                () -> assertThrows(RuntimeException.class, () -> fruitTransactionParser
                        .parseFruitTransaction(incorrectList6)),
                () -> assertThrows(RuntimeException.class, () -> fruitTransactionParser
                        .parseFruitTransaction(incorrectList7))
        );
    }
}
