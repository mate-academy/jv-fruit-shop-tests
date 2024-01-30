package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArticleDaoImplTest {
    private static final int DEFAULT_STORAGE_SIZE = 0;
    private static final int DEFAULT_QUANTITY = 0;
    private static final ArticleDao ARTICLE_DAO = new ArticleDaoImpl();
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final String PINEAPPLE = "pineapple";
    private static final String LEMON = "lemon";
    private List<String> articles;

    @BeforeEach
    void setUp() {
        articles = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
      void addArticle_correctArticleFormat_Ok() {
        articles.add(BANANA);
        articles.add(APPLE);
        articles.add(ORANGE);
        articles.add(PINEAPPLE);
        articles.add(LEMON);
        for (String article : articles) {
            ARTICLE_DAO.addArticle(article);
            assertTrue(Storage.storage.containsKey(article));
            assertTrue(Storage.storage.get(article)
                    == DEFAULT_QUANTITY);
        }
        assertTrue(articles.size() == Storage.storage.size());
    }

    @Test
    void addArticle_addExistingArticle_NotOk() {
        Storage.storage.put(BANANA, 0);
        Storage.storage.put(APPLE, 23);
        Storage.storage.put(PINEAPPLE, 10);
        Storage.storage.put(ORANGE, 345);
        Storage.storage.put(LEMON, 1000);
        int expectedStorageSize = Storage.storage.size();
        for (Map.Entry<String, Integer> set : Storage.storage.entrySet()) {
            String existingArticle = set.getKey();
            int expectedValue = set.getValue();
            Throwable exception = assertThrows(RuntimeException.class,
                    () -> ARTICLE_DAO.addArticle(existingArticle));
            assertEquals("Article '" + existingArticle
                            + "' already exist in storage",
                    exception.getMessage());
            assertTrue(expectedValue
                    == Storage.storage.get(existingArticle));
        }
        assertTrue(expectedStorageSize == Storage.storage.size());
    }

    @Test
    void addArticle_uppercaseLetters_NotOk() {
        articles.add("Banana");
        articles.add("applE");
        articles.add("Orange");
        articles.add("PineapplE");
        articles.add("leMon");
        for (String string : articles) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    ARTICLE_DAO.addArticle(string));
            assertEquals("Article name: '" + string
                    + "' shouldn't contain numbers,"
                            + " spices, special characters and upper case letters",
                    exception.getMessage());
            assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
        }
    }

    @Test
    void addArticle_nullParameter_NotOk() {
        String nullString = null;
        Throwable exception = assertThrows(RuntimeException.class, () ->
                ARTICLE_DAO.addArticle(nullString));
        assertEquals("Can't add null to storage", exception.getMessage());
        assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
    }

    @Test
    void addArticle_emptyString_Not_Ok() {
        String emptyString = "";
        Throwable exception = assertThrows(RuntimeException.class, () ->
                ARTICLE_DAO.addArticle(emptyString));
        assertEquals("Can't add empty string to storage", exception.getMessage());
        assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
    }

    @Test
    void addArticle_stringWithSpaces_Not_Ok() {
        articles.add("Apple ");
        articles.add(" Apple");
        articles.add("ap ple");
        articles.add("Banana     ");
        articles.add("      Ban ana");
        articles.add("   Banana   ");
        for (String string : articles) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    ARTICLE_DAO.addArticle(string));
            assertEquals("Article name: '" + string
                    + "' shouldn't contain numbers,"
                            + " spices, special characters and upper case letters",
                    exception.getMessage());
            assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
        }
    }

    @Test
    void addArticle_specialCharacters_Not_Ok() {
        articles.add("Apple!");
        articles.add("@Apple");
        articles.add("Ap.ple");
        articles.add("Banana!");
        articles.add("Banana>>>");
        articles.add("Banana_(*");
        articles.add("$$Orange_(*");
        articles.add("Oran><>ge");
        articles.add("!%$#%");
        for (String string : articles) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    ARTICLE_DAO.addArticle(string));
            assertEquals("Article name: '" + string
                    + "' shouldn't contain numbers,"
                            + " spices, special characters and upper case letters",
                    exception.getMessage());
            assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
        }
    }

    @Test
    void addArticle_numbers_Not_Ok() {
        articles.add("Apple1");
        articles.add("30Apple");
        articles.add("Ap2ple");
        articles.add("Banana410");
        articles.add("Ban4ana4");
        articles.add("100Banana");
        articles.add("100");
        articles.add("Oran45ge");
        articles.add("2345");
        for (String string : articles) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    ARTICLE_DAO.addArticle(string));
            assertEquals("Article name: '" + string
                    + "' shouldn't contain numbers,"
                            + " spices, special characters and upper case letters",
                    exception.getMessage());
            assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
        }
    }

    @Test
    void updateStorage_correctParameters_Ok() {
        Storage.storage.put(ORANGE, 0);
        Storage.storage.put(PINEAPPLE, 6);
        Storage.storage.put(BANANA, 100);
        Storage.storage.put(LEMON, 76);
        Storage.storage.put(APPLE, 1500);
        int[] values = {0, 1, 2, 5, 100, 143, 200,
                261, 1001, 10582};
        for (Map.Entry<String, Integer> set : Storage.storage.entrySet()) {
            String article = set.getKey();
            for (int value : values) {
                ARTICLE_DAO.updateStorage(article, value);
                assertTrue(value == Storage.storage.get(article));
            }
        }
    }

    @Test
    void updateStorage_negativeQuantity_NotOk() {
        Storage.storage.put(ORANGE, 0);
        Storage.storage.put(PINEAPPLE, 6);
        Storage.storage.put(BANANA, 100);
        Storage.storage.put(LEMON, 76);
        Storage.storage.put(APPLE, 1500);
        int[] negativeValues = {-1, -2, -3, -10, -48, -73, -100, -335, -1000};
        for (Map.Entry<String, Integer> set : Storage.storage.entrySet()) {
            String fruit = set.getKey();
            int expectedValue = set.getValue();
            for (int negativeValue : negativeValues) {
                Throwable exception = assertThrows(RuntimeException.class, () ->
                        ARTICLE_DAO.updateStorage(fruit, negativeValue));
                assertEquals("Quantity can't be negative",
                        exception.getMessage());
                assertTrue(expectedValue == Storage.storage.get(fruit));
            }
        }
    }

    @Test
    void updateStorage_uppercaseLetters_NotOk() {
        articles.add("Banana");
        articles.add("applE");
        articles.add("Orange");
        articles.add("PineapplE");
        articles.add("leMon");
        for (String string : articles) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    ARTICLE_DAO.updateStorage(string, DEFAULT_QUANTITY));
            assertEquals("Article name: '" + string
                    + "' shouldn't contain numbers,"
                            + " spices, special characters and upper case letters",
                    exception.getMessage());
            assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
        }
    }

    @Test
    void updateStorage_storageDoesntContainArticle_NotOk() {
        articles.add(BANANA);
        articles.add(APPLE);
        articles.add(ORANGE);
        articles.add(PINEAPPLE);
        articles.add(LEMON);
        int expectedStorageSize = Storage.storage.size();
        for (String string : articles) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    ARTICLE_DAO.updateStorage(string, DEFAULT_QUANTITY));
            assertEquals("Storage doesn't contain article '"
                    + string + "'", exception.getMessage());
        }
        assertTrue(expectedStorageSize == Storage.storage.size());
    }

    @Test
    void getQuantity_storageContainArticle_Ok() {
        Storage.storage.put(ORANGE, 0);
        Storage.storage.put(PINEAPPLE, 6);
        Storage.storage.put(BANANA, 100);
        Storage.storage.put(LEMON, 76);
        Storage.storage.put(APPLE, 1500);
        int expectedStorageSize = Storage.storage.size();
        for (Map.Entry<String, Integer> set :Storage.storage.entrySet()) {
            String article = set.getKey();
            int expectedValue = set.getValue();
            assertTrue(ARTICLE_DAO.getQuantity(article)
                    == expectedValue);
        }
        assertTrue(expectedStorageSize == Storage.storage.size());
    }

    @Test
    void getQuantity_storageDoesntContainArticle_NotOk() {
        articles.add(BANANA);
        articles.add(APPLE);
        articles.add(ORANGE);
        articles.add(PINEAPPLE);
        articles.add(LEMON);
        for (String string : articles) {
            Throwable exception = assertThrows(RuntimeException.class, () ->
                    ARTICLE_DAO.updateStorage(string, DEFAULT_QUANTITY));
            assertEquals("Storage doesn't contain article '"
                    + string + "'", exception.getMessage());
        }
    }

    @Test
    void getQuantity_nullParameter_NotOk() {
        String nullString = null;
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> ARTICLE_DAO.getQuantity(nullString));
        assertEquals("Argument can't be null", exception.getMessage());
    }

    @Test
    void getArticles_storageContainArticles_Ok() {
        Storage.storage.put(ORANGE, 0);
        Storage.storage.put(PINEAPPLE, 6);
        Storage.storage.put(BANANA, 100);
        Storage.storage.put(LEMON, 76);
        Storage.storage.put(APPLE, 1500);
        List<String> expectedArticleList = new ArrayList<>(Storage.storage.keySet());
        List<String> currentList = ARTICLE_DAO.getArticles();
        assertTrue(expectedArticleList.size() == currentList.size());
        for (String article : expectedArticleList) {
            assertTrue(currentList.contains(article));
        }
    }

    @Test
    void getArticles_emptyStorage_NotOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                ARTICLE_DAO::getArticles);
        assertEquals("Storage is empty",
                exception.getMessage());
    }

    @Test
    void removeArticle_storageContainArticle_Ok() {
        Storage.storage.put(ORANGE, 0);
        Storage.storage.put(PINEAPPLE, 6);
        Storage.storage.put(BANANA, 100);
        Storage.storage.put(LEMON, 76);
        Storage.storage.put(APPLE, 1500);
        int numberOfRemoved = 1;
        List<String> articlesToRemove = new ArrayList<>(Storage.storage.keySet());
        for (String article : articlesToRemove) {
            int expectedStorageSize = Storage.storage.size() - numberOfRemoved;
            ARTICLE_DAO.removeArticle(article);
            assertFalse(Storage.storage.containsKey(article));
            assertTrue(expectedStorageSize == Storage.storage.size());
        }
    }

    @Test
    void removeArticle_storageDoesntContainArticle_NotOk() {
        articles.add(BANANA);
        articles.add(APPLE);
        articles.add(ORANGE);
        articles.add(PINEAPPLE);
        articles.add(LEMON);
        for (String article : articles) {
            Throwable exception = assertThrows(RuntimeException.class,
                    () -> ARTICLE_DAO.removeArticle(article));
            assertEquals("Storage doesn't contain article '"
                            + article + "'",
                    exception.getMessage());
        }
    }
}
