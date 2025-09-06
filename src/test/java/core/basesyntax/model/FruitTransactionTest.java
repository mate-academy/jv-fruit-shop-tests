package core.basesyntax.model;

import static core.basesyntax.model.TypeOfTransaction.B;
import static core.basesyntax.model.TypeOfTransaction.S;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final FruitTransaction fruitTransactionNo1 = new FruitTransaction();
    private static final FruitTransaction fruitTransactionNo2 = new FruitTransaction();

    @Test
    void getters_correctValues_ok() {
        fruitTransactionNo1.setTypeOfTransaction(TypeOfTransaction.R);
        fruitTransactionNo1.setFruit("orange");
        fruitTransactionNo1.setQuantity(20);

        Assertions.assertEquals(TypeOfTransaction.R, fruitTransactionNo1.getTypeOfTransaction());
        Assertions.assertEquals("orange", fruitTransactionNo1.getFruit());
        Assertions.assertEquals(20, fruitTransactionNo1.getQuantity());
    }

    @Test
    void setTypeOfTransaction_incorrectValue_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitTransactionNo1.setTypeOfTransaction(
                        TypeOfTransaction.valueOf("N")));
    }

    @Test
    void setFruit_nullOrEmpty_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitTransactionNo1.setFruit(null));
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitTransactionNo1.setFruit(""));
    }

    @Test
    void setQuantity_nullOrNegativeValue_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitTransactionNo1.setQuantity(null));
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitTransactionNo1.setQuantity(-10));
    }

    @Test
    void equals_comparisonTwoEqualTransactions_ok() {
        fruitTransactionNo1.setTypeOfTransaction(B);
        fruitTransactionNo1.setFruit("banana");
        fruitTransactionNo1.setQuantity(20);
        fruitTransactionNo2.setTypeOfTransaction(B);
        fruitTransactionNo2.setFruit("banana");
        fruitTransactionNo2.setQuantity(20);
        Assertions.assertEquals(fruitTransactionNo1, fruitTransactionNo2);
    }

    @Test
    void equals_comparisonTwoTransactionsWithDifferentTypes_not_ok() {
        fruitTransactionNo1.setTypeOfTransaction(S);
        fruitTransactionNo1.setFruit("banana");
        fruitTransactionNo1.setQuantity(15);
        fruitTransactionNo2.setTypeOfTransaction(B);
        fruitTransactionNo2.setFruit("banana");
        fruitTransactionNo2.setQuantity(15);
        Assertions.assertNotEquals(fruitTransactionNo1, fruitTransactionNo2);
    }

    @Test
    void equals_comparisonTwoTransactionsWithDifferentNamesOfFruits_not_ok() {
        fruitTransactionNo1.setTypeOfTransaction(S);
        fruitTransactionNo1.setFruit("banana");
        fruitTransactionNo1.setQuantity(15);
        fruitTransactionNo2.setTypeOfTransaction(S);
        fruitTransactionNo2.setFruit("apple");
        fruitTransactionNo2.setQuantity(15);
        Assertions.assertNotEquals(fruitTransactionNo1, fruitTransactionNo2);
    }

    @Test
    void equals_comparisonTwoTransactionsWithDifferentQuantities_not_ok() {
        fruitTransactionNo1.setTypeOfTransaction(S);
        fruitTransactionNo1.setFruit("banana");
        fruitTransactionNo1.setQuantity(20);
        fruitTransactionNo2.setTypeOfTransaction(B);
        fruitTransactionNo2.setFruit("banana");
        fruitTransactionNo2.setQuantity(15);
        Assertions.assertNotEquals(fruitTransactionNo1, fruitTransactionNo2);
    }

    @Test
    void hashCode_twoTheSameObjects_ok() {
        fruitTransactionNo1.setTypeOfTransaction(S);
        fruitTransactionNo1.setFruit("apple");
        fruitTransactionNo1.setQuantity(30);

        fruitTransactionNo2.setTypeOfTransaction(S);
        fruitTransactionNo2.setFruit("apple");
        fruitTransactionNo2.setQuantity(30);

        Assertions.assertEquals(fruitTransactionNo1.hashCode(),fruitTransactionNo2.hashCode());
    }

    @Test
    void hashCode_twoDifferentObjects_notOk() {
        fruitTransactionNo1.setTypeOfTransaction(S);
        fruitTransactionNo1.setFruit("apple");
        fruitTransactionNo1.setQuantity(30);

        fruitTransactionNo2.setTypeOfTransaction(S);
        fruitTransactionNo2.setFruit("apple");
        fruitTransactionNo2.setQuantity(35);

        Assertions.assertNotEquals(fruitTransactionNo1.hashCode(), fruitTransactionNo2.hashCode());
    }

    @Test
    void toString_ok() {
        fruitTransactionNo1.setTypeOfTransaction(S);
        fruitTransactionNo1.setFruit("orange");
        fruitTransactionNo1.setQuantity(30);
        String expected = "FruitTransaction{fruit='orange', quantity=30}";
        Assertions.assertEquals(expected, fruitTransactionNo1.toString());
    }
}
