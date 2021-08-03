package core.basesyntax;

import dao.ShopOperationsDao;
import dao.ShopOperationsDaoCsvImpl;
import org.junit.Assert;
import org.junit.Test;
import util.FileAdministrating;

public class ShopOperationsDaoCsvImplTest {
    private static final String INPUT_FILE_NAME = "fruit-shop.csv";
    private static final String OUTPUT_FILE_NAME = "fruit-shop-report.csv";
    private static final ShopOperationsDao shopOperationsDao
            = new ShopOperationsDaoCsvImpl(INPUT_FILE_NAME, OUTPUT_FILE_NAME);

    @Test
    public void validate_Ok() {
        FileAdministrating.writeDataToFile(INPUT_FILE_NAME, "type,fruit,quantity\n"
                + "b,grape,50\n"
                + "b,melon,30");
        Assert.assertTrue(shopOperationsDao.validate());

        FileAdministrating.writeDataToFile(INPUT_FILE_NAME, "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10");
        Assert.assertTrue(shopOperationsDao.validate());
        FileAdministrating.renewInputFile(INPUT_FILE_NAME);
    }

    @Test
    public void validate_NotOk() {
        FileAdministrating.writeDataToFile(INPUT_FILE_NAME, "type,fruit,quantity\n"
                + "b,grape,-50\n"
                + "b,melon,30");
        Assert.assertFalse(shopOperationsDao.validate());

        FileAdministrating.writeDataToFile(INPUT_FILE_NAME, "type,fruit,quantity\n"
                + "b,grape,50\n"
                + "b,,30");
        Assert.assertFalse(shopOperationsDao.validate());

        FileAdministrating.writeDataToFile(INPUT_FILE_NAME, "type,fruit,quantity\n"
                + "i,grape,50\n"
                + "b,melon,30");
        Assert.assertFalse(shopOperationsDao.validate());

        FileAdministrating.writeDataToFile(INPUT_FILE_NAME, "type,fruit,quantity\n"
                + "i,50\n"
                + "b,melon,30");
        Assert.assertFalse(shopOperationsDao.validate());
        FileAdministrating.renewInputFile(INPUT_FILE_NAME);
    }

    @Test
    public void generateReport_Ok() {
        FileAdministrating.writeDataToFile(INPUT_FILE_NAME, "type,fruit,quantity\n"
                + "b,grape,50\n"
                + "b,melon,30\n"
                + "p,grape,2\n"
                + "p,melon,30\n"
                + "r,melon,5\n"
                + "p,melon,5\n"
                + "p,grape,5\n"
                + "s,melon,20\n"
                + "p,grape,13\n"
                + "p,grape,6\n"
                + "p,melon,7\n"
                + "r,grape,1");
        shopOperationsDao.generateReport();
        String expected = "fruit,quantity\n"
                + "grape,25\n"
                + "melon,13";
        String actual = FileAdministrating.getDataFromFile(OUTPUT_FILE_NAME);
        Assert.assertEquals(expected, actual);
        FileAdministrating.renewInputFile(INPUT_FILE_NAME);
    }
}
