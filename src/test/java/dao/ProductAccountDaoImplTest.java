package dao;

import db.Storage;
import model.ProductAccount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductAccountDaoImplTest {

    private Storage memdb;
    private ProductAccountDaoImpl dao;
    private ProductAccount firstProduct;
    private ProductAccount secondPoduct;
    private ProductAccount thirdProduct;

    @Before
    public void setup() {

        memdb = new Storage();
        memdb.products.clear();
        dao = new ProductAccountDaoImpl(memdb);
        firstProduct = new ProductAccount("OriginalProduct0").setAmount(13);
        secondPoduct = new ProductAccount("OriginalProduct1").setAmount(26);
        thirdProduct = new ProductAccount("OriginalProduct2").setAmount(17);
        dao.add(firstProduct);
        dao.add(secondPoduct);
        dao.add(thirdProduct);
    }

    @Test
    public void testAdd() {

        Assert.assertEquals("firstProduct is equal to stored in db",
                firstProduct,memdb.products.get(0));
        Assert.assertEquals("secondPoduct is equal to stored in db",
                secondPoduct,memdb.products.get(1));
        Assert.assertEquals("thirdProduct is equal to stored in db",
                thirdProduct,memdb.products.get(2));
    }

    @Test
    public void testGet() {

        Assert.assertEquals("firstProduct is equal to stored in db",
                firstProduct,dao.get(firstProduct.getName()).get());
        Assert.assertEquals("secondPoduct is equal to stored in db",
                secondPoduct,dao.get(secondPoduct.getName()).get());
        Assert.assertEquals("thirdProduct is equal to stored in db",
                thirdProduct,dao.get(thirdProduct.getName()).get());

    }

    @Test
    public void testUpdate() {

        dao.update(firstProduct.setAmount(18));
        dao.update(secondPoduct.setAmount(36));
        dao.update(thirdProduct.setAmount(72));
        Assert.assertEquals("firstProduct is equal to stored in db",
                Integer.valueOf(18),dao.get(firstProduct.getName()).get().getAmount());
        Assert.assertEquals("secondPoduct is equal to stored in db",
                Integer.valueOf(36),dao.get(secondPoduct.getName()).get().getAmount());
        Assert.assertEquals("thirdProduct is equal to stored in db",
                Integer.valueOf(72),dao.get(thirdProduct.getName()).get().getAmount());

    }

}


