package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.ValidatorServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorServiceTest {
    private static final String PATH = "src/test/resources/test.csv";
    private static ValidatorService validatorService;

    @BeforeClass
    public static void init() {
        validatorService = new ValidatorServiceImpl();
    }

    @After
    public void clearFile() {
        try {
            Files.write(Path.of(PATH), Collections.EMPTY_LIST);
        } catch (IOException e) {
            throw new RuntimeException("Can't clear file", e);
        }
    }

    private List<String> readFromFile(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file in test", e);
        }
    }

    private void writeToFile(String path, List<String> data) {
        try {
            Files.write(Path.of(PATH), data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file in test", e);
        }
    }

    @Test
    public void isValidCorrectFile_Ok() {
        List<String> dataList = new ArrayList<>();
        dataList.add("type,fruit,quantity");
        dataList.add("b,orange,30");
        dataList.add("b,kiwi,50");
        dataList.add("r,orange,15");

        writeToFile(PATH, dataList);
        List<String> fromFileData = readFromFile(PATH);
        boolean actual = validatorService.isValid(fromFileData);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValidIncorrectHeadersFile_NotOk() {
        List<String> dataList = new ArrayList<>();
        dataList.add("operation,fruit,amount");
        dataList.add("b,orange,30");
        dataList.add("b,kiwi,50");
        dataList.add("r,orange,15");

        writeToFile(PATH, dataList);
        List<String> fromFileData = readFromFile(PATH);
        boolean actual = validatorService.isValid(fromFileData);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValidIncorrectDataFile_NotOk() {
        List<String> dataList = new ArrayList<>();
        dataList.add("type,fruit,quantity");
        dataList.add("b,banana,200");
        dataList.add("p,banana,42");
        dataList.add("supply,orange,line");

        writeToFile(PATH, dataList);
        List<String> fromFileData = readFromFile(PATH);
        boolean actual = validatorService.isValid(fromFileData);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValidEmptyFile_NotOk() {
        writeToFile(PATH, Collections.EMPTY_LIST);
        List<String> fromFileData = readFromFile(PATH);
        boolean actual = validatorService.isValid(fromFileData);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValidNullInput_NotOk() {
        validatorService.isValid(null);
    }
}
