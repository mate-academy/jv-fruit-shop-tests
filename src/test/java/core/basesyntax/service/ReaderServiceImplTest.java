package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String PATH_TEST = "src/test/resources/";
    private static final String FILE_NAME = "fruits.csv";
    private static final String FILE_EMPTY = "fruits_empty.csv";
    private static final String FILE_NO_DATA = "fruits_no_data.csv";
    private static final String FILE_BAD_OPERATION = "fruits_bad_operation.csv";
    private static final String FILE_BAD_NUMBER = "fruits_bad_number.csv";
    private static final String FILE_STORAGE = "fruits_storage.csv";
    private static final File testFile = new File(PATH_TEST + FILE_NAME);
    private static final File emptyFile = new File(PATH_TEST + FILE_EMPTY);
    private static final File noDataFile = new File(PATH_TEST + FILE_NO_DATA);
    private static final File badOperationFile = new File(PATH_TEST + FILE_BAD_OPERATION);
    private static final File badNumberFile = new File(PATH_TEST + FILE_BAD_NUMBER);
    private static final File storageFile = new File(PATH_TEST + FILE_STORAGE);
    private static final String FIRST_LINE = "type,fruit,quantity";
    private static final String NEW_LINE = System.lineSeparator();
    private ReaderService readerService = new ReaderServiceImpl();

    @BeforeAll
    static void beforeAll() {
        createFileTest();
        createFileEmpty();
        createFileNoData();
        createFileBadOperation();
        createFileBadNumber();
        createFileStorage();
    }

    @AfterAll
    static void afterAll() {
        testFile.delete();
        emptyFile.delete();
        noDataFile.delete();
        badOperationFile.delete();
        badNumberFile.delete();
        storageFile.delete();
    }

    @Test
    void readFromFile_pathExists_NotOK() {
        Assert.assertTrue("Folder for transaction file not found: "
                + PATH_TEST, Files.exists(Path.of(PATH_TEST)));
    }

    @AfterEach
    void afterEachTest() {
        Storage.fruitTransactions.clear();
    }

    @Test
    void readFromFile_test_OK() {
        List<FruitTransaction> expected = createFruits_OK();
        List<FruitTransaction> result = readerService.readFromFile(
                PATH_TEST + FILE_NAME);
        Assert.assertEquals("File " + PATH_TEST + FILE_NAME
                + " not read correctly must be: " + expected + "\n", expected, result);
    }

    @Test
    void readFromFile_empty_NotOK() {
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> result = readerService.readFromFile(
                PATH_TEST + FILE_EMPTY);
        Assert.assertEquals("File " + PATH_TEST + FILE_EMPTY
                + " not read correctly must be: " + expected + "\n", expected, result);
    }

    @Test
    void readFromFile_noData_NotOK() {
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> result = readerService.readFromFile(
                PATH_TEST + FILE_NO_DATA);
        Assert.assertEquals("File " + PATH_TEST + FILE_NO_DATA
                + " not read correctly must be: " + expected + "\n", expected, result);
    }

    @Test
    void readFromFile_badOperation_NotOK() throws RuntimeException {
        Throwable thrown = Assert.assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(PATH_TEST + FILE_BAD_OPERATION);
        });
        Assert.assertNotNull(thrown.getMessage());
    }

    @Test
    void readFromFile_badNumber_NotOK() throws RuntimeException {
        Throwable thrown = Assert.assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(PATH_TEST + FILE_BAD_NUMBER);
        });
        Assert.assertNotNull(thrown.getMessage());
    }

    @Test
    void readFromFile_addInStorage_OK() {
        List<FruitTransaction> expected = readerService
                .readFromFile(PATH_TEST + FILE_STORAGE);
        List<FruitTransaction> result = Storage.fruitTransactions.stream()
                .collect(Collectors.toList());
        Assert.assertEquals("Wrong data written to storage must be: "
                + expected + "\n", expected, result);
    }

    public static void createFileTest() {
        try (FileWriter writer = new FileWriter(testFile, false)) {
            writer.write(FIRST_LINE);
            writer.write(NEW_LINE);
            writer.write("b,apple,100");
            writer.write(NEW_LINE);
            writer.write("s,apple,10");
            writer.write(NEW_LINE);
            writer.write("p,apple,20");
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createFileEmpty() {
        try (FileWriter writer = new FileWriter(emptyFile, false)) {
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createFileNoData() {
        try (FileWriter writer = new FileWriter(noDataFile, false)) {
            writer.write(FIRST_LINE);
            writer.write(NEW_LINE);
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createFileBadOperation() {
        try (FileWriter writer = new FileWriter(badOperationFile, false)) {
            writer.write(FIRST_LINE);
            writer.write(NEW_LINE);
            writer.write("b,apple,100");
            writer.write(NEW_LINE);
            writer.write("f,apple,10");
            writer.write(NEW_LINE);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createFileBadNumber() {
        try (FileWriter writer = new FileWriter(badNumberFile, false)) {
            writer.write(FIRST_LINE);
            writer.write(NEW_LINE);
            writer.write("b,apple,100");
            writer.write(NEW_LINE);
            writer.write("s,apple,-10");
            writer.write(NEW_LINE);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<FruitTransaction> createFruits_OK() {
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("apple");
        fruitTransaction1.setQuantity(100);
        List<FruitTransaction> fruitsList = new ArrayList<>();
        fruitsList.add(fruitTransaction1);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(10);
        fruitsList.add(fruitTransaction2);
        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction3.setFruit("apple");
        fruitTransaction3.setQuantity(20);
        fruitsList.add(fruitTransaction3);
        return fruitsList;
    }

    public static void createFileStorage() {
        try (FileWriter writer = new FileWriter(storageFile, false)) {
            writer.write(FIRST_LINE);
            writer.write(NEW_LINE);
            writer.write("b,apple,90");
            writer.write(NEW_LINE);
            writer.write("s,apple,10");
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
