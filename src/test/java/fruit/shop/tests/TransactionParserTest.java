package fruit.shop.tests;

import fruit.shop.model.FruitTransaction;
import fruit.shop.service.InvalidTransactionInputException;
import fruit.shop.service.TransactionParserServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionParserTest {
    private static final TransactionParserServiceImpl TRANSACTION_PARSER_SERVICE
            = new TransactionParserServiceImpl();

    @Test
    public void parser_unknownTransType_notOk() {
        List<String> transactions = List.of("x,banana,20");
        Assert.assertThrows(InvalidTransactionInputException.class,
                () -> TRANSACTION_PARSER_SERVICE.parse(transactions));
    }

    @Test
    public void parser_unknownTransType_inBetween_notOk() {
        List<String> transactions = List.of("b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "x,banana,20",
                "s,banana,200",
                "p,banana,12");
        Assert.assertThrows(InvalidTransactionInputException.class,
                () -> TRANSACTION_PARSER_SERVICE.parse(transactions));
    }

    @Test
    public void parser_validTrans_ok() {
        List<String> transactions = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<FruitTransaction> result = TRANSACTION_PARSER_SERVICE.parse(transactions);
        Assertions.assertFalse(result.isEmpty());
    }
}
