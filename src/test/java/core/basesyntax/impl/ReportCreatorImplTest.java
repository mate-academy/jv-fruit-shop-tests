package core.basesyntax.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    public static final Map<LocalDate, Map<Fruit, Integer>> fruitStorage = new HashMap<>();
    public static final Map<Fruit, Integer> fruitMap = new HashMap<>();
    private static ReportCreator creator;
    private static FruitStorageServiceImpl service;

    @BeforeClass
    public static void initialize() {
        creator = new ReportCreatorImpl();
        service = new FruitStorageServiceImpl();
    }

    @After
    public void clear() {
        fruitMap.clear();
        fruitStorage.clear();
    }

    @Test
    public void createReport_rightInput_Ok() {
        fruitMap.put(Fruit.builder().name("peach").build(), 16);
        fruitMap.put(Fruit.builder().name("banana").build(), 13);
        service.addToStorage(fruitMap);
        List<String> actual = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("Fruit(name=peach),16");
        expected.add("Fruit(name=banana),13");
        String path = "src/test/resources/RightTest";
        creator.createReport(path, LocalDate.now());
        try {
            actual = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            Assert.fail("Can't open file from path: " + path);
        }
        Assert.assertEquals("Expected: " + expected
                + ", but was: " + actual, expected, actual);
    }

    @Test
    public void createReport_nullInput_Ok() {
        String path = "src/test/resources/nullTest";
        creator.createReport(path, LocalDate.now());
        List<String> actual = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        try {
            actual = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            Assert.fail("Cant read file from path: " + path);
        }
        Assert.assertEquals("Expected: " + expected
                + ", but was: " + actual, expected, actual);
    }

    @Test
    public void createReport_EmptyFile_Ok() {
        String path = "src/test/resources/emptyTest";
        service.addToStorage(fruitMap);
        creator.createReport(path, LocalDate.now());
        List<String> actual = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        try {
            actual = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            Assert.fail("Cant read file from path: " + path);
        }
        Assert.assertEquals("Expected: " + expected
                 + ", but was: " + actual, expected, actual);
    }
}
