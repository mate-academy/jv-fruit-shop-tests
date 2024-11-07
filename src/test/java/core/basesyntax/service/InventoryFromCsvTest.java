package core.basesyntax.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.StockDao;
import core.basesyntax.db.StockDaoStorageImpl;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.Test;

class InventoryFromCsvTest {
    private final StockDao stockDao = new StockDaoStorageImpl();

    @Test
    void prepare_fileDoNotExists_NotOk() {
        final String notExistingFile = "notExistingFile.csv";
        Inventory inventory = new InventoryFromCsv(stockDao, notExistingFile);
        assertThrows(RuntimeException.class,
                () -> inventory.synchronizeWithTheStorage());
    }

    @Test
    void prepare_emptyFile_Ok() {
        final String emptyFile = "emptyFile.csv";
        Inventory inventory = new InventoryFromCsv(stockDao, emptyFile);
    }

    @Test
    void prepare_addingProductData_Ok() {
        final String addingProduct = "addingProduct.csv";
        Inventory inventory = new InventoryFromCsv(stockDao, addingProduct);
        inventory.synchronizeWithTheStorage();
        String product = "banana";
        Integer amount = 20;
        assertEquals(amount, Storage.stock.get(product));
    }

    @Test
    void prepare_supply_Ok() {
        final String supply = "supply.csv";
        Inventory inventorySupply = new InventoryFromCsv(stockDao, supply);
        inventorySupply.synchronizeWithTheStorage();
        String product = "banana";
        Integer amount = 120;
        assertEquals(amount, Storage.stock.get(product));
    }

    @Test
    void prepare_return_Ok() {
        final String purchase = "return.csv";
        Inventory inventoryPurchase = new InventoryFromCsv(stockDao, purchase);
        inventoryPurchase.synchronizeWithTheStorage();
        String product = "banana";
        Integer amount = 40;
        assertEquals(amount, Storage.stock.get(product));
    }

    @Test
    void prepare_purchase_Ok() {
        final String purchase = "purchase.csv";
        Inventory inventoryPurchase = new InventoryFromCsv(stockDao, purchase);
        inventoryPurchase.synchronizeWithTheStorage();
        String product = "banana";
        Integer amount = 10;
        assertEquals(amount, Storage.stock.get(product));
    }

    @Test
    void prepare_incorrectData_TooManyComma_NotOk() {
        final String tooManyComma = "tooManyComma.csv";
        Inventory inventory = new InventoryFromCsv(stockDao, tooManyComma);
        assertThrows(InvalidDataException.class,
                () -> inventory.synchronizeWithTheStorage());
    }

    @Test
    void prepare_incorrectData_AmountIsNotANumber_NotOk() {
        final String amountIsNotANumber = "amountIsNotANumber.csv";
        Inventory inventory = new InventoryFromCsv(stockDao, amountIsNotANumber);
        assertThrows(InvalidDataException.class,
                () -> inventory.synchronizeWithTheStorage());
    }

    @Test
    void prepare_incorrectData_NotExistingOperation_NotOk() {
        final String notExistingOperation = "notExistingOperation.csv";
        Inventory inventory = new InventoryFromCsv(stockDao, notExistingOperation);
        assertThrows(InvalidDataException.class,
                () -> inventory.synchronizeWithTheStorage());
    }

    @Test
    void prepare_unacceptableOperation_decreaseBelowZero_NotOk() {
        final String decreaseBelowZero = "decreaseBelowZero.csv";
        Inventory inventoryDecreaseBelowZero = new InventoryFromCsv(stockDao, decreaseBelowZero);
        assertThrows(UnacceptableStockOperationException.class,
                () -> inventoryDecreaseBelowZero.synchronizeWithTheStorage());
    }

    @Test
    void prepare_unacceptableOperation_increaseBelowZero_NotOk() {
        final String increaseBelowZero = "increaseBelowZero.csv";
        Inventory inventoryIncreaseBelowZero = new InventoryFromCsv(stockDao, increaseBelowZero);
        assertThrows(UnacceptableStockOperationException.class,
                () -> inventoryIncreaseBelowZero.synchronizeWithTheStorage());
    }

    @Test
    void prepare_unacceptableOperation_noSuchElement_NotOk() {
        final String noSuchElement = "noSuchElement.csv";
        Inventory inventoryNoSuchElement = new InventoryFromCsv(stockDao, noSuchElement);
        assertThrows(UnacceptableStockOperationException.class,
                () -> inventoryNoSuchElement.synchronizeWithTheStorage());
    }
}
