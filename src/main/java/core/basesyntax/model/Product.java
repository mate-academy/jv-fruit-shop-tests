package core.basesyntax.model;

import core.basesyntax.exceptions.ProductException;
import java.util.Objects;

public class Product {
    private Transaction transaction;
    private String productName;
    private int quantity;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new ProductException("Transaction can't be null");
        }
        this.transaction = transaction;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.isEmpty()) {
            throw new ProductException("Product name can't be empty or null");
        }
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{"
            + "transaction=" + transaction
            + ", productName='" + productName + '\''
            + ", quantity=" + quantity
            + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return quantity == product.quantity
                && transaction == product.transaction
                && Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction, productName, quantity);
    }
}
