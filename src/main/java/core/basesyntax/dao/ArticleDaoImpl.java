package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.List;

public class ArticleDaoImpl implements ArticleDao {
    private static final int DEFAULT_FRUIT_QUANTITY = 0;
    private static final int MIN_QUANTITY_VALUE = 0;

    @Override
    public void addArticle(String article) {
        if (article == null) {
            throw new RuntimeException("Can't add null to storage");
        }
        if (article.length() == 0) {
            throw new RuntimeException("Can't add empty string to storage");
        }
        if (Storage.storage.containsKey(article)) {
            throw new RuntimeException("Article '" + article
                    + "' already exist in storage");
        }
        if (!article.matches("[a-z]+")) {
            throw new RuntimeException("Article name: '" + article
                    + "' shouldn't contain numbers,"
                    + " spices, special characters and upper case letters");
        }
        Storage.storage.put(article, DEFAULT_FRUIT_QUANTITY);
    }

    @Override
    public void updateStorage(String article, int quantity) {
        if (!article.matches("[a-z]+")) {
            throw new RuntimeException("Article name: '" + article
                    + "' shouldn't contain numbers,"
                    + " spices, special characters and upper case letters");
        }
        if (!Storage.storage.containsKey(article)) {
            throw new RuntimeException("Storage doesn't contain article '"
                    + article + "'");
        }
        if (quantity < MIN_QUANTITY_VALUE) {
            throw new RuntimeException("Quantity can't be negative");
        }
        Storage.storage.put(article, quantity);
    }

    @Override
    public Integer getQuantity(String article) {
        if (article == null) {
            throw new IllegalArgumentException("Argument can't be null");
        }
        if (!Storage.storage.containsKey(article)) {
            throw new RuntimeException("Storage doesn't contain article '"
                    + article + "'");
        }
        return Storage.storage.get(article);
    }

    @Override
    public List<String> getArticles() {
        if (Storage.storage.isEmpty()) {
            throw new RuntimeException("Storage is empty");
        }
        return new ArrayList<>(Storage.storage.keySet());
    }

    @Override
    public void removeArticle(String article) {
        if (Storage.storage.get(article) == null) {
            throw new RuntimeException("Storage doesn't contain article '" + article + "'");
        }
        if (!Storage.storage.containsKey(article)) {
            throw new RuntimeException("Storage doesn't contain article '"
                    + article + "'");
        }
        Storage.storage.remove(article);
    }
}
