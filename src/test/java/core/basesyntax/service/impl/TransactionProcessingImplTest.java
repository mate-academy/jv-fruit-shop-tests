package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.ProductDaoCsvImpl;
import core.basesyntax.exceptions.TransactionProcessingException;
import core.basesyntax.model.Product;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.transaction.TransactionHandler;
import core.basesyntax.service.transaction.impl.PurchaseTransactionHandler;
import core.basesyntax.service.transaction.impl.ReturnTransactionHandler;
import core.basesyntax.service.transaction.impl.SupplyTransactionHandler;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.impl.TransactionStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessingImplTest {
    public static final String TRANSACTIONS_CSV_FILE_NAME =
            "src/test/java/resources/transactions.csv";
    public static final String TRANSACTIONS_EXCEPT_BALANCE_CSV_FILE_NAME =
            "src/test/java/resources/transactionsExceptBalance.csv";
    public static final String TRANSACTIONS_ONLY_BALANCE_CSV_FILE_NAME =
            "src/test/java/resources/transactionsOnlyBalance.csv";
    private static TransactionProcessingImpl transactionProcessing;
    private static ProductDaoCsvImpl productDaoCsv;
    private static Product testProduct;
    private static Product testProductForCheck;
    private static List<Product> productList = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        Map<Transaction, TransactionHandler> transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(Transaction.SUPPLY, new SupplyTransactionHandler());
        transactionHandlerMap.put(Transaction.PURCHASE, new PurchaseTransactionHandler());
        transactionHandlerMap.put(Transaction.RETURN,
                new ReturnTransactionHandler());
        TransactionStrategy transactionStrategy =
                new TransactionStrategyImpl(transactionHandlerMap);

        transactionProcessing = new TransactionProcessingImpl(transactionStrategy);
        testProduct = new Product();
        testProductForCheck = new Product();
    }

    @BeforeEach
    void beforeEach() {
        productList.clear();
        productDaoCsv = new ProductDaoCsvImpl(TRANSACTIONS_CSV_FILE_NAME);

        testProduct.setProductName("banana");
        testProduct.setTransaction(Transaction.PURCHASE);
        testProduct.setQuantity(100);

        testProductForCheck.setProductName("banana");
        testProductForCheck.setTransaction(Transaction.PURCHASE);
        testProductForCheck.setQuantity(0);
    }

    @Test
    void filterByTransaction_nullProductsList_notOk() {
        assertThrows(TransactionProcessingException.class,
                () -> transactionProcessing.filterByTransaction(null, Transaction.PURCHASE));
    }

    @Test
    void filterByTransaction_emptyProductsList_notOk() {
        assertThrows(TransactionProcessingException.class,
                () -> transactionProcessing.filterByTransaction(productList, Transaction.PURCHASE));
    }

    @Test
    void filterByTransaction_nullTransaction_notOk() {
        productList = productDaoCsv.get();
        assertThrows(TransactionProcessingException.class,
                () -> transactionProcessing.filterByTransaction(productList, null));
    }

    @Test
    void filterByTransaction_BalanceTransaction_ok() {
        productList = productDaoCsv.get();
        List<Product> actual = transactionProcessing
                .filterByTransaction(productList, Transaction.BALANCE);

        productDaoCsv = new ProductDaoCsvImpl(TRANSACTIONS_ONLY_BALANCE_CSV_FILE_NAME);
        productList = productDaoCsv.get();

        assertEquals(productList, actual);
    }

    @Test
    void filterExceptTransaction_nullProductsList_notOk() {
        assertThrows(TransactionProcessingException.class,
                () -> transactionProcessing.filterExceptTransaction(null, Transaction.PURCHASE));
    }

    @Test
    void filterExceptTransaction_emptyProductsList_notOk() {
        assertThrows(TransactionProcessingException.class,
                () -> transactionProcessing
                    .filterExceptTransaction(productList, Transaction.PURCHASE));
    }

    @Test
    void filterExceptTransaction_nullTransaction_notOk() {
        productList = productDaoCsv.get();
        assertThrows(TransactionProcessingException.class,
                () -> transactionProcessing.filterByTransaction(productList, null));
    }

    @Test
    void filterExceptTransaction_BalanceTransaction_ok() {
        productList = productDaoCsv.get();
        List<Product> actual = transactionProcessing
                .filterExceptTransaction(productList, Transaction.BALANCE);

        productDaoCsv = new ProductDaoCsvImpl(TRANSACTIONS_EXCEPT_BALANCE_CSV_FILE_NAME);
        productList = productDaoCsv.get();

        assertEquals(productList, actual);
    }

    @Test
    void perform_nullProduct_notOk() {
        assertThrows(TransactionProcessingException.class,
                () -> transactionProcessing.perform(null, 12));
    }

    @Test
    void perform_negativeBalanceBeforeTransaction_notOk() {
        assertThrows(TransactionProcessingException.class,
                () -> transactionProcessing.perform(testProduct, -12));
    }

    @Test
    void perform_negativeBalanceAfterTransaction_notOk() {
        Product productAfterTransaction = transactionProcessing.perform(testProduct, 110);
        assertTrue(productAfterTransaction.getQuantity() > 0);
    }

    @Test
    void perform_returnNullProduct_notOk() {
        Product productAfterTransaction = transactionProcessing.perform(testProduct, 110);
        assertNotNull(productAfterTransaction);
    }

    @Test
    void perform_BalanceTransaction_notOk() {
        testProduct.setTransaction(Transaction.BALANCE);
        assertThrows(TransactionProcessingException.class,
                () -> transactionProcessing.perform(testProduct, 12));
    }

    @Test
    void perform_SupplyTransaction_ok() {
        testProduct.setTransaction(Transaction.SUPPLY);
        testProductForCheck.setTransaction(Transaction.SUPPLY);
        testProductForCheck.setQuantity(200);

        Product productAfterTransaction = transactionProcessing.perform(testProduct, 100);

        assertEquals(testProductForCheck, productAfterTransaction);
    }

    @Test
    void perform_ReturnTransaction_ok() {
        testProduct.setTransaction(Transaction.RETURN);
        testProductForCheck.setTransaction(Transaction.RETURN);
        testProductForCheck.setQuantity(120);

        Product productAfterTransaction = transactionProcessing.perform(testProduct, 20);

        assertEquals(testProductForCheck, productAfterTransaction);
    }

    @Test
    void perform_PurchaseTransaction_ok() {
        testProduct.setTransaction(Transaction.PURCHASE);
        testProductForCheck.setTransaction(Transaction.PURCHASE);
        testProductForCheck.setQuantity(50);

        Product productAfterTransaction = transactionProcessing.perform(testProduct, 150);

        assertEquals(testProductForCheck, productAfterTransaction);
    }
}
