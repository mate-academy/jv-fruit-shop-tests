package core.basesyntax.service;

import core.basesyntax.model.Product;
import core.basesyntax.model.Transaction;
import java.util.List;

public interface TransactionProcessing {
    List<Product> filterByTransaction(List<Product> products, Transaction transaction);

    List<Product> filterExceptTransaction(List<Product> products, Transaction transaction);

    Product perform(Product product, int balanceBeforeTransaction);
}
