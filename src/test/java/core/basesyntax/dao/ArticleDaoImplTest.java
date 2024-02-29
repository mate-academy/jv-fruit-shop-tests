package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ArticleDaoImplTest {
    private static final int DEFAULT_STORAGE_SIZE = 0;
    private static final int DEFAULT_QUANTITY = 0;
    private static final int EXPECTED_STORAGE_SIZE = 4;
    private static final ArticleDao ARTICLE_DAO = new ArticleDaoImpl();
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String ORANGE = "orange";
    private static final String PINEAPPLE = "pineapple";
    private static final String LEMON = "lemon";

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @ParameterizedTest
    @ValueSource(strings = {BANANA, APPLE, ORANGE, PINEAPPLE, LEMON})
    void addArticle_correctArticleFormat_ok(String article) {
        ARTICLE_DAO.addArticle(article);
        assertTrue(Storage.storage.containsKey(article));
        assertEquals(Storage.storage.get(article), DEFAULT_QUANTITY);
    }

    @ParameterizedTest
    @ValueSource(strings = {BANANA, APPLE, ORANGE, PINEAPPLE, LEMON})
    void addArticle_addExistingArticle_notOk(String article) {
        Storage.storage.putAll(Map.of(
                BANANA, 0,
                APPLE, 23,
                PINEAPPLE, 10,
                ORANGE, 345,
                LEMON, 1000));
        int expectedStorageSize = Storage.storage.size();
        int expectedValue = Storage.storage.get(article);
        Throwable exception = assertThrows(RuntimeException.class,
                () -> ARTICLE_DAO.addArticle(article));
        assertEquals("""
                        Article '%s' already exist in storage"""
                        .formatted(article),
                exception.getMessage());
        assertEquals(expectedValue, Storage.storage.get(article));
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Banana", "applE", "Orange", "PineapplE", "leMon"})
    void addArticle_uppercaseLetters_notOk(String article) {
        Throwable exception = assertThrows(RuntimeException.class, () ->
                ARTICLE_DAO.addArticle(article));
        assertEquals("""
                        Article name: '%s' shouldn't contain numbers,
                        spices, special characters and upper case letters"""
                        .formatted(article), exception.getMessage());
        assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
    }

    @Test
    void addArticle_nullParameter_notOk() {
        String nullString = null;
        Throwable exception = assertThrows(RuntimeException.class, () ->
                ARTICLE_DAO.addArticle(nullString));
        assertEquals("Can't add null to storage", exception.getMessage());
        assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
    }

    @Test
    void addArticle_emptyString_notOk() {
        String emptyString = "";
        Throwable exception = assertThrows(RuntimeException.class, () ->
                ARTICLE_DAO.addArticle(emptyString));
        assertEquals("Can't add empty string to storage", exception.getMessage());
        assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Apple ", " Apple", "ap ple",
            "Banana     ", "      Ban ana", "   Banana   "})
    void addArticle_stringWithSpaces_notOk(String article) {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> ARTICLE_DAO.addArticle(article));
        assertEquals("""
                        Article name: '%s' shouldn't contain numbers,
                        spices, special characters and upper case letters"""
                        .formatted(article), exception.getMessage());
        assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Apple!", "@Apple", "Ap.ple",
            "Banana!", "Banana>>>", "Banana_(*", "$$Orange_(*",
            "Oran><>ge", "!%$#%"})
    void addArticle_specialCharacters_notOk(String article) {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> ARTICLE_DAO.addArticle(article));
        assertEquals("""
                        Article name: '%s' shouldn't contain numbers,
                        spices, special characters and upper case letters"""
                        .formatted(article),
                exception.getMessage());
        assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Apple1", "30Apple", "Ap2ple",
            "Banana410", "Ban4ana4", "100Banana", "100",
            "Oran45ge", "2345"})
    void addArticle_numbers_notOk(String article) {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> ARTICLE_DAO.addArticle(article));
        assertEquals("""
                        Article name: '%s' shouldn't contain numbers,
                        spices, special characters and upper case letters"""
                        .formatted(article),
                exception.getMessage());
        assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
    }

    @ParameterizedTest
    @MethodSource
    void updateStorage_correctParameters_ok(int quantity, String fruit) {
        Storage.storage.putAll(Map.of(
                BANANA, 100,
                APPLE, 1500,
                PINEAPPLE, 6,
                ORANGE, 0,
                LEMON, 76));
        int storageSize = Storage.storage.size();
        ARTICLE_DAO.updateStorage(fruit, quantity);
        assertEquals(quantity, Storage.storage.get(fruit));
        assertEquals(storageSize, Storage.storage.size());
    }

    private static Stream<Arguments> updateStorage_correctParameters_ok() {
        return Stream.of(
                Arguments.of(0, ORANGE),
                Arguments.of(1, PINEAPPLE),
                Arguments.of(2, BANANA),
                Arguments.of(5, LEMON),
                Arguments.of(100, APPLE),
                Arguments.of(143, ORANGE),
                Arguments.of(200, PINEAPPLE),
                Arguments.of(261, BANANA),
                Arguments.of(1001, LEMON),
                Arguments.of(10582, APPLE)
        );
    }

    @ParameterizedTest
    @MethodSource
    void updateStorage_negativeQuantity_notOk(int quantity, String fruit) {
        Storage.storage.putAll(Map.of(
                BANANA, 100,
                APPLE, 1500,
                PINEAPPLE, 6,
                ORANGE, 0,
                LEMON, 76));
        int expectedValue = Storage.storage.get(fruit);
        Throwable exception = assertThrows(RuntimeException.class,
                () -> ARTICLE_DAO.updateStorage(fruit, quantity));
        assertEquals("Quantity can't be negative",
                exception.getMessage());
        assertEquals(expectedValue, Storage.storage.get(fruit));
    }

    private static Stream<Arguments> updateStorage_negativeQuantity_notOk() {
        return Stream.of(
                Arguments.of(-1, ORANGE),
                Arguments.of(-2, PINEAPPLE),
                Arguments.of(-3, BANANA),
                Arguments.of(-10, LEMON),
                Arguments.of(-48, APPLE),
                Arguments.of(-73, ORANGE),
                Arguments.of(-100, PINEAPPLE),
                Arguments.of(-335, BANANA),
                Arguments.of(-1000, LEMON),
                Arguments.of(-7546, APPLE)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"Banana", "applE", "Orange", "PineapplE", "leMon"})
    void updateStorage_uppercaseLetters_notOk(String article) {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> ARTICLE_DAO.updateStorage(article, DEFAULT_QUANTITY));
        assertEquals("""
                        Article name: '%s' shouldn't contain numbers,
                        spices, special characters and upper case letters"""
                        .formatted(article),
                exception.getMessage());
        assertEquals(DEFAULT_STORAGE_SIZE, Storage.storage.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {BANANA, APPLE, ORANGE, PINEAPPLE, LEMON})
    void updateStorage_storageDoesntContainArticle_notOk(String article) {
        int expectedStorageSize = Storage.storage.size();
        Throwable exception = assertThrows(RuntimeException.class, () ->
                ARTICLE_DAO.updateStorage(article, DEFAULT_QUANTITY));
        assertEquals("""
                Storage doesn't contain article %s"""
                .formatted(article), exception.getMessage());
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {BANANA, APPLE, ORANGE, PINEAPPLE, LEMON})
    void getQuantity_storageContainArticle_ok(String article) {
        Storage.storage.putAll(Map.of(
                BANANA, 100,
                APPLE, 1500,
                PINEAPPLE, 6,
                ORANGE, 0,
                LEMON, 76));
        int expectedStorageSize = Storage.storage.size();
        int expectedValue = Storage.storage.get(article);
        assertEquals(expectedValue, ARTICLE_DAO.getQuantity(article));
        assertEquals(expectedStorageSize, Storage.storage.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {BANANA, APPLE, ORANGE, PINEAPPLE, LEMON})
    void getQuantity_storageDoesntContainArticle_notOk(String article) {
        Throwable exception = assertThrows(RuntimeException.class, () ->
                ARTICLE_DAO.updateStorage(article, DEFAULT_QUANTITY));
        assertEquals("""
                Storage doesn't contain article %s"""
                .formatted(article), exception.getMessage());
    }

    @Test
    void getQuantity_nullParameters_notOk() {
        String nullString = null;
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> ARTICLE_DAO.getQuantity(nullString));
        assertEquals("Argument can't be null", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {BANANA, APPLE, ORANGE, PINEAPPLE, LEMON})
    void getArticles_storageContainArticles_ok(String article) {
        Storage.storage.putAll(Map.of(
                BANANA, 100,
                APPLE, 1500,
                PINEAPPLE, 6,
                ORANGE, 0,
                LEMON, 76));
        List<String> expectedArticleList = new ArrayList<>(Storage.storage.keySet());
        List<String> currentList = ARTICLE_DAO.getArticles();
        assertEquals(expectedArticleList.size(), currentList.size());
        assertTrue(currentList.contains(article));
    }

    @Test
    void getArticles_emptyStorage_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                ARTICLE_DAO::getArticles);
        assertEquals("Storage is empty",
                exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {BANANA, APPLE, ORANGE, PINEAPPLE, LEMON})
    void removeArticle_storageContainArticle_ok(String article) {
        Storage.storage.putAll(Map.of(
                BANANA, 100,
                APPLE, 1500,
                PINEAPPLE, 6,
                ORANGE, 0,
                LEMON, 76));
        ARTICLE_DAO.removeArticle(article);
        assertFalse(Storage.storage.containsKey(article));
        assertEquals(EXPECTED_STORAGE_SIZE, Storage.storage.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {BANANA, APPLE, ORANGE, PINEAPPLE, LEMON})
    void removeArticle_storageDoesntContainArticle_notOk(String article) {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> ARTICLE_DAO.removeArticle(article));
        assertEquals("""
                Storage doesn't contain article %s"""
                .formatted(article), exception.getMessage());
    }
}
