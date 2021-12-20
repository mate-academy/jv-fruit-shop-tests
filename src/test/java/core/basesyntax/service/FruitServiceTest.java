package core.basesyntax.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceTest {
    private static FruitDao fruitDao = new FruitDaoImpl();
    private static FruitService fruitService = new FruitServiceImpl(fruitDao);
    private static final Map<String, Fruit.Type> mapOfFruitTypes = new HashMap<>();
    private static final String FILE_NAME = "src/test/resources/listOfFruit.csv";
    private static final String WRONG_FILE_NAME = "src/test/resources.listOfFruit.csv";
    private static final String EMPTY_FILE_NAME = "src/test/resources/emptyFile.csv";

    @BeforeClass
    public static void setUp() throws Exception {
        mapOfFruitTypes.put("b", Fruit.Type.BALANCE);
        mapOfFruitTypes.put("p", Fruit.Type.PURCHASE);
        mapOfFruitTypes.put("s", Fruit.Type.SUPPLY);
        mapOfFruitTypes.put("r", Fruit.Type.RETURN);
    }

    @Test
    public void fillMapOfFruitTypesTest_Ok() {
        Map<String, Fruit.Type> actual;
        try {
            Method method = FruitServiceImpl.class.getDeclaredMethod("fillMapOfFruitTypes");
            method.setAccessible(true);
            actual = (Map<String, Fruit.Type>)method.invoke(FruitServiceImpl.class);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("There is a problem with this method: ", e);
        }
        Map<String, Fruit.Type> expected = new HashMap<>(mapOfFruitTypes);
        Set<String> keysForTest = mapOfFruitTypes.keySet();
        for (String str : keysForTest) {
            if (expected.get(str) == actual.get(str)) {
                expected.remove(str);
                actual.remove(str);
            }
        }
        assertTrue(expected.isEmpty() == actual.isEmpty());
    }

    @Test
    public void getFromCsvRowTest_Ok() {
        Fruit expectedFruit = new Fruit();
        expectedFruit.setName("banana");
        expectedFruit.setAmount(20);
        expectedFruit.setType(Fruit.Type.BALANCE);
        Fruit actualFruit;
        try {
            Method method = FruitServiceImpl.class
                    .getDeclaredMethod("getFromCsvRow", String.class);
            method.setAccessible(true);
            actualFruit = (Fruit) method.invoke(fruitService, "  b,banana,20 ");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("There is a problem with this method: ", e);
        }
        assertEquals(expectedFruit, actualFruit);
    }

    @Test(expected = RuntimeException.class)
    public void getFromCsvRowTest_WrongIncomingData() {
        Fruit actualFruit;
        try {
            Method method = FruitServiceImpl.class
                    .getDeclaredMethod("getFromCsvRow", String.class);
            method.setAccessible(true);
            actualFruit = (Fruit) method.invoke(fruitService, "  bbanana20 ");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("There is a problem with this method: ", e);
        }
    }

    @Test
    public void createNewFruitTest_Ok() {
        Fruit expectedFruit = new Fruit();
        expectedFruit.setName("banana");
        expectedFruit.setAmount(20);
        Fruit actualFruit = fruitService.createNewFruit("banana", 20);
        assertEquals(expectedFruit, actualFruit);
    }

    @Test
    public void getAllFruitFromFileTest_Ok() {
        Fruit fruit1 = new Fruit();
        fruit1.setName("banana");
        fruit1.setAmount(20);
        fruit1.setType(Fruit.Type.BALANCE);
        Fruit fruit2 = new Fruit();
        fruit2.setName("apple");
        fruit2.setAmount(100);
        fruit2.setType(Fruit.Type.BALANCE);
        Fruit fruit3 = new Fruit();
        fruit3.setName("banana");
        fruit3.setAmount(100);
        fruit3.setType(Fruit.Type.SUPPLY);
        Fruit fruit4 = new Fruit();
        fruit4.setName("banana");
        fruit4.setAmount(13);
        fruit4.setType(Fruit.Type.PURCHASE);
        Fruit fruit5 = new Fruit();
        fruit5.setName("apple");
        fruit5.setAmount(10);
        fruit5.setType(Fruit.Type.RETURN);
        Fruit fruit6 = new Fruit();
        fruit6.setName("apple");
        fruit6.setAmount(20);
        fruit6.setType(Fruit.Type.PURCHASE);
        Fruit fruit7 = new Fruit();
        fruit7.setName("banana");
        fruit7.setAmount(5);
        fruit7.setType(Fruit.Type.PURCHASE);
        Fruit fruit8 = new Fruit();
        fruit8.setName("banana");
        fruit8.setAmount(50);
        fruit8.setType(Fruit.Type.SUPPLY);
        List<Fruit> expectedListOfFruitFromFile = new ArrayList<>();
        Collections.addAll(expectedListOfFruitFromFile, fruit1, fruit2, fruit3, fruit4,
                fruit5, fruit6, fruit7, fruit8);
        List<Fruit> actualListOfFruitFromFile = fruitService.getAllFruitFromFile(FILE_NAME);
        assertArrayEquals(expectedListOfFruitFromFile.toArray(),
                actualListOfFruitFromFile.toArray());
    }

    @Test(expected = RuntimeException.class)
    public void getAllFruitFromFileTest_WrongFilePath() {
        List<Fruit> actualListOfFruitFromFile = fruitService.getAllFruitFromFile(WRONG_FILE_NAME);
    }

    @Test(expected = NullPointerException.class)
    public void getAllFruitFromFileTest_NullParameter() {
        List<Fruit> actualListOfFruitFromFile = fruitService.getAllFruitFromFile(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getAllFruitFromFileTest_EmptyFile() {
        List<Fruit> actualListOfFruitFromFile = fruitService.getAllFruitFromFile(EMPTY_FILE_NAME);
    }
}
