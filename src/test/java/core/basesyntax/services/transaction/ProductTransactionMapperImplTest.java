package core.basesyntax.services.transaction;

import core.basesyntax.services.transaction.model.ProductTransaction;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductTransactionMapperImplTest {
    private static ProductTransactionMapper mapper;

    @BeforeClass
    public static void setMapper() {
        mapper = new ProductTransactionMapperImpl();
    }

    @Test
    public void getProductTransactions_Ok() {
        List<String> lines = List.of("b,banana,100",
                "p,apple,500",
                "r,orange,89");
        List<ProductTransaction> transactions = mapper.getProductTransactions(lines);

    }
}
