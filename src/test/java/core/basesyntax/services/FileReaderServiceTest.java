package core.basesyntax.services;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exceptions.FileReadException;
import core.basesyntax.model.Activity;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import core.basesyntax.services.impl.ReaderServiceImpl;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String RESOURCES_PATH = "src" + File.separator
            + "main" + File.separator
            + "resources" + File.separator;
    private static final String CSV_WITH_ORIGINAL_VALUES = RESOURCES_PATH
            + "CSV_WITH_ORIGINAL_VALUES.csv";
    private static FileReaderService fileReaderService;
    private static List<Activity> expectedActivities;
    private static final String CSV_WITH_WRONG_PATH = RESOURCES_PATH
            + "CSV_WITH_WRONG_PATH.csv";

    @BeforeClass
    public static void setup() {
        expectedActivities = new ArrayList<>();
        fileReaderService = new ReaderServiceImpl();
    }

    @Test
    public void readFile_WithOriginalValues_OK() {
        expectedActivities.add(new Activity
                .Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.BANANA)
                .setQuantity(20)
                .build());
        expectedActivities.add(new Activity
                .Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.APPLE)
                .setQuantity(100)
                .build());
        expectedActivities.add(new Activity
                .Builder()
                .setActivityType(ActivityType.SUPPLY)
                .setFruit(Fruit.BANANA)
                .setQuantity(100)
                .build());
        expectedActivities.add(new Activity
                .Builder()
                .setActivityType(ActivityType.PURCHASE)
                .setFruit(Fruit.BANANA)
                .setQuantity(13)
                .build());
        expectedActivities.add(new Activity
                .Builder()
                .setActivityType(ActivityType.RETURN)
                .setFruit(Fruit.APPLE)
                .setQuantity(10)
                .build());
        expectedActivities.add(new Activity.Builder()
                .setActivityType(ActivityType.PURCHASE)
                .setFruit(Fruit.APPLE)
                .setQuantity(20)
                .build());
        expectedActivities.add(new Activity.Builder()
                .setActivityType(ActivityType.PURCHASE)
                .setFruit(Fruit.BANANA)
                .setQuantity(5)
                .build());
        expectedActivities.add(new Activity.Builder()
                .setActivityType(ActivityType.SUPPLY)
                .setFruit(Fruit.BANANA)
                .setQuantity(50)
                .build());
        List<Activity> actualActivities = fileReaderService
                .readFile(Path.of(CSV_WITH_ORIGINAL_VALUES));
        assertTrue(expectedActivities.equals(actualActivities));
    }

    @Test(expected = FileReadException.class)
    public void readFile_WithWrongPath_Not_OK() {
        fileReaderService.readFile(Path.of(CSV_WITH_WRONG_PATH));
    }
}
