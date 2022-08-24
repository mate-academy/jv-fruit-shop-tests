package core.basesyntax;

import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShopWorkingTest {
    private static final ShopService shopService = new ShopServiceImpl();
    private static final String APPLES_ORANGES_FILE = "applesOranges.csv";
    private static final String APPLES_GRAPES_FILE = "applesGrapes.csv";
    private static final String APPLES_BANANAS_FILE = "applesBananas.csv";
    private static final String BANANAS_ORANGES_FILE = "bananasOranges.csv";

    @Before
    public void clearData() {
        DataBase.fruitsAmount.clear();
        DataBase.transitions.clear();
        try {
            Files.deleteIfExists(Path.of(APPLES_ORANGES_FILE));
            Files.deleteIfExists(Path.of(APPLES_GRAPES_FILE));
            Files.deleteIfExists(Path.of(APPLES_BANANAS_FILE));
            Files.deleteIfExists(Path.of(BANANAS_ORANGES_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't clear data before tests", e);
        }
    }

    @Test
    public void getStatisticAboutApplesOranges_Ok() {
        addedInfoAppleOranges();
        shopService.servicing(APPLES_ORANGES_FILE);
        String actualResult = readFromFile(APPLES_ORANGES_FILE).trim();
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "orange,75" + System.lineSeparator()
                + "apple,99";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getStatisticAboutApplesGrapes_Ok() {
        addedInfoAppleGrapes();
        shopService.servicing(APPLES_GRAPES_FILE);
        String actualResult = readFromFile(APPLES_GRAPES_FILE).trim();
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "apple,111" + System.lineSeparator()
                + "grape,14";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getStatisticAboutApplesBananas_Ok() {
        addedInfoAppleBanana();
        shopService.servicing(APPLES_BANANAS_FILE);
        String actualResult = readFromFile(APPLES_BANANAS_FILE).trim();
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,100";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getStatisticAboutBananasOranges_Ok() {
        addedInfoBananaOrange();
        shopService.servicing(BANANAS_ORANGES_FILE);
        String actualResult = readFromFile(BANANAS_ORANGES_FILE).trim();
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,185" + System.lineSeparator()
                + "orange,75";
        Assert.assertEquals(expectedResult, actualResult);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }

    private void addedInfoAppleOranges() {
        String[] strings = new String[10];
        strings[0] = "type,fruit,quantity";
        strings[1] = "b,apple,35";
        strings[2] = "b,orange,42";
        strings[3] = "s,apple,70";
        strings[4] = "p,apple,15";
        strings[5] = "s,orange,55";
        strings[6] = "r,apple,4";
        strings[7] = "p,orange,22";
        strings[8] = "s,apple,30";
        strings[9] = "p,apple,25";
        File file = new File(APPLES_ORANGES_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (String data : strings) {
                writer.write(data + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file", e);
        }
    }

    private void addedInfoAppleGrapes() {
        String[] strings = new String[10];
        strings[0] = "type,fruit,quantity";
        strings[1] = "b,apple,30";
        strings[2] = "b,grape,22";
        strings[3] = "s,apple,66";
        strings[4] = "p,apple,15";
        strings[5] = "s,grape,34";
        strings[6] = "r,apple,10";
        strings[7] = "p,grape,22";
        strings[8] = "s,apple,20";
        strings[9] = "p,grape,20";
        File file = new File(APPLES_GRAPES_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (String data : strings) {
                writer.write(data + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file", e);
        }
    }

    private void addedInfoAppleBanana() {
        String[] strings = new String[10];
        strings[0] = "type,fruit,quantity";
        strings[1] = "b,banana,20";
        strings[2] = "b,apple,100";
        strings[3] = "s,banana,100";
        strings[4] = "p,banana,13";
        strings[5] = "r,apple,10";
        strings[6] = "p,apple,20";
        strings[7] = "p,banana,5";
        strings[8] = "s,banana,50";
        strings[9] = "s,apple,10";
        File file = new File(APPLES_BANANAS_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (String data : strings) {
                writer.write(data + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file", e);
        }
    }

    private void addedInfoBananaOrange() {
        String[] strings = new String[10];
        strings[0] = "type,fruit,quantity";
        strings[1] = "b,banana,50";
        strings[2] = "b,orange,60";
        strings[3] = "s,banana,100";
        strings[4] = "p,orange,30";
        strings[5] = "s,orange,80";
        strings[6] = "r,banana,15";
        strings[7] = "p,orange,22";
        strings[8] = "s,banana,20";
        strings[9] = "p,orange,13";
        File file = new File(BANANAS_ORANGES_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (String data : strings) {
                writer.write(data + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file", e);
        }
    }

}
