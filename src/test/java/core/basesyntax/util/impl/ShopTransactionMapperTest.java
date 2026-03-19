package core.basesyntax.util.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.ShopTransaction;
import core.basesyntax.util.Mapper;
import org.junit.jupiter.api.Test;

class ShopTransactionMapperTest {
    private final Mapper<String, ShopTransaction> mapper = new ShopTransactionMapper();

    @Test
    void map_validCase_returnsRightsShopTransaction_ok() {
        String message =
                "map() does not return required ShopTransaction object from string: ";
        ShopTransaction expected;

        expected = new ShopTransaction(ShopTransaction.Operation.BALANCE, "banana", 10);
        assertEquals(expected, mapper.map("b,banana,10"), message + "b,banana,10");

        expected = new ShopTransaction(ShopTransaction.Operation.SUPPLY, "banana", 10);
        assertEquals(expected, mapper.map("s,banana,10"), message + "s,banana,10");

        expected = new ShopTransaction(ShopTransaction.Operation.PURCHASE, "banana", 10);
        assertEquals(expected, mapper.map("p,banana,10"), message + "p,banana,10");

        expected = new ShopTransaction(ShopTransaction.Operation.RETURN, "banana", 10);
        assertEquals(expected, mapper.map("r,banana,10"), message + "r,banana,10");

    }

    @Test
    void map_inputStringWithSpacesBetweenCommas_returnsRightsShopTransaction_ok() {
        ShopTransaction expected =
                new ShopTransaction(ShopTransaction.Operation.SUPPLY, "banana", 10);
        ShopTransaction actual = mapper.map("s, banana , 10");

        assertEquals(expected, actual,
                "map() does not trim the input string");
    }

    @Test
    void map_inputStringIsNull_notOk() {
        assertThrows(NullPointerException.class, () -> mapper.map(null));
    }

    @Test
    void map_inputStringDoesNotContainExactlyThreeParts_notOk() {
        assertThrows(RuntimeException.class, () -> mapper.map("b,banana,10,yellow"));
        assertThrows(RuntimeException.class, () -> mapper.map("b,yellow"));
    }

    @Test
    void map_inputStringQuantityArgument_wrongNumberFormat_notOk() {
        String message = "map() does not throw RuntimeException when number is: ";

        assertThrows(RuntimeException.class, () -> mapper.map("b,banana,invalid"),
                message + "invalid");
        assertThrows(RuntimeException.class, () -> mapper.map("b,banana,23.45"),
                message + "23.45");
        assertThrows(RuntimeException.class, () -> mapper.map("b,banana,2.9f"),
                message + "2.9f");
        assertThrows(RuntimeException.class, () -> mapper.map("b,banana, 99999999999"),
                message + "99999999999");
        assertThrows(RuntimeException.class, () -> mapper.map("b,banana, -99999999999"),
                message + "-99999999999");
        assertThrows(RuntimeException.class, () -> mapper.map("b,banana,"),
                message + "b,banana,");
    }

    @Test
    void map_inputStringOperationArgumentIsWrong_notOk() {
        assertThrows(RuntimeException.class, () -> mapper.map("g,banana,10"));
    }

    @Test
    void map_inputStringProductNameArgumentIsBlank_notOk() {
        assertThrows(RuntimeException.class, () -> mapper.map("b,   ,10"));
    }
}
