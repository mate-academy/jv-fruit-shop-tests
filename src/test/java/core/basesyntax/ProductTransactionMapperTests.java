package core.basesyntax;

import core.basesyntax.services.transaction.ProductTransactionMapper;
import core.basesyntax.services.transaction.ProductTransactionMapperImpl;
import core.basesyntax.services.transaction.model.ProductTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class ProductTransactionMapperTests {
    private static ProductTransactionMapper mapper;

    @BeforeClass
    public static void setMapper() {
        mapper = new ProductTransactionMapperImpl();
    }

    @Test
    public void getProductTransactionsFromLines_Ok() {
        List<String> lines = List.of("b,banana,100",
                                     "p,apple,500",
                                     "r,orange,89");
        List<ProductTransaction> transactions = mapper.getProductTransactions(lines);

    }
}
