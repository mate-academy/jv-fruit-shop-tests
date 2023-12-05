package core.basesyntax.service.impl;

import core.basesyntax.exceptions.TransactionProcessingException;
import core.basesyntax.model.Product;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionProcessing;
import core.basesyntax.service.transaction.TransactionHandler;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionProcessingImpl implements TransactionProcessing {
    private TransactionStrategy transactionStrategy;

    public TransactionProcessingImpl(TransactionStrategy transactionStrategy) {
        this.transactionStrategy = transactionStrategy;
    }

    @Override
    public List<Product> filterByTransaction(List<Product> products, Transaction transaction) {
        checkFilterMethodsArgs(products, transaction);
        return products.stream()
                       .filter(product -> product.getTransaction() == transaction)
                       .collect(Collectors.toList());
    }

    @Override
    public List<Product> filterExceptTransaction(List<Product> products, Transaction transaction) {
        checkFilterMethodsArgs(products, transaction);
        return products.stream()
                       .filter(product -> product.getTransaction() != transaction)
                       .collect(Collectors.toList());
    }

    @Override
    public Product perform(Product product, int balanceBeforeTransaction) {
        checkPerformMethodArgs(product, balanceBeforeTransaction);
        TransactionHandler transactionHandler = transactionStrategy.get(product.getTransaction());
        int balanceAfterTransaction = transactionHandler.perform(balanceBeforeTransaction,
                product.getQuantity());
        product.setQuantity(balanceAfterTransaction);
        return product;
    }

    private void checkFilterMethodsArgs(List<Product> products, Transaction transaction) {
        if (products == null) {
            throw new TransactionProcessingException("Products list can't be null");
        }
        if (products.isEmpty()) {
            throw new TransactionProcessingException("Products list can't be empty");
        }
        if (transaction == null) {
            throw new TransactionProcessingException("Transaction can't be null");
        }
    }

    private void checkPerformMethodArgs(Product product, int balanceBeforeTransaction) {
        if (product == null) {
            throw new TransactionProcessingException("Product can't be null");
        }
        if (product.getProductName() == null) {
            throw new TransactionProcessingException("Product name can't be null");
        }
        if (product.getProductName().isEmpty()) {
            throw new TransactionProcessingException("Product name can't be empty");
        }
        if (product.getTransaction() == null) {
            throw new TransactionProcessingException("Product transaction can't be null");
        }
        if (product.getTransaction() == Transaction.BALANCE) {
            throw new TransactionProcessingException("Balance transaction is unsupported");
        }
        if (product.getQuantity() < 0) {
            throw new TransactionProcessingException("Product quantity can't be negative");
        }
        if (balanceBeforeTransaction < 0) {
            throw new TransactionProcessingException(
                "Balance before transaction can't be negative");
        }
    }
}
