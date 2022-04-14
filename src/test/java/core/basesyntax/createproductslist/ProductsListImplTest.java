package core.basesyntax.createproductslist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.operationswithfile.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ProductsListImplTest {
    private static final List<Transaction> transactionList = new ArrayList<>();
    private static final Map<String, Integer> expected = new HashMap<>();
    private static final ProductsListImpl productsList = new ProductsListImpl();
    private static final Transaction transaction = new Transaction();
    private static final Transaction transaction2 = new Transaction();

    @Before
    public void addToTransactionList() {
        transaction.setOperationType("b");
        transaction.setName("banana");
        transaction.setCount(40);
        transaction2.setOperationType("b");
        transaction2.setName("apple");
        transaction2.setCount(40);
        transactionList.add(transaction);
        transactionList.add(transaction2);
    }

    @Test
    public void getProductListWithLegalData_ok() {
        expected.put("banana",40);
        expected.put("apple",40);
        Map<String, Integer> productList = productsList.getProductList(transactionList);
        assertEquals(expected,productList);
    }

    @Test
    public void getProductListWithIllegalData_notOk() {
        expected.put("banana",50);
        expected.put("pineapples",40);
        Map<String, Integer> productList = productsList.getProductList(transactionList);
        assertNotEquals(expected,productList);
    }
}
