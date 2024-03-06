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
            throw new RuntimeException("Article can't be null");
        }
        if (article.trim().isEmpty()) {
            throw new RuntimeException("Article name is empty");
        }
        if (Storage.storage.containsKey(article)) {
            throw new RuntimeException("""
                        Article '%s' already exist in storage"""
                    .formatted(article));
        }
        if (!article.matches("[a-z]+")) {
            throw new RuntimeException("""
                        Article name: '%s' shouldn't contain numbers,
                        spices, special characters and upper case letters"""
                    .formatted(article));
        }
        Storage.storage.put(article, DEFAULT_FRUIT_QUANTITY);
    }

    @Override
    public void updateStorage(String article, int quantity) {
        if (!article.matches("[a-z]+")) {
            throw new RuntimeException("""
                        Article name: '%s' shouldn't contain numbers,
                        spices, special characters and upper case letters"""
                    .formatted(article));
        }
        if (!Storage.storage.containsKey(article)) {
            throw new RuntimeException("""
                Storage doesn't contain article %s"""
                    .formatted(article));
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
            throw new RuntimeException("""
                Storage doesn't contain article %s"""
                    .formatted(article));
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
        if (!Storage.storage.containsKey(article)) {
            throw new RuntimeException("""
                Storage doesn't contain article %s"""
                    .formatted(article));
        }
        Storage.storage.remove(article);
    }

    @Override
    public boolean isContainArticle(String article) {
        return Storage.storage.containsKey(article);
    }
}
