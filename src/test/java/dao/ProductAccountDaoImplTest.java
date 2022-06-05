package dao;

import db.Storage;
import model.ProductAccount;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductAccountDaoImplTest {

    private Storage memdb;
    private ProductAccountDaoImpl dao;
    private ProductAccount product0;
    private ProductAccount product1;
    private ProductAccount product2;

    @BeforeEach
    public void setup() {

        memdb = new Storage();
        memdb.products.clear();
        dao = new ProductAccountDaoImpl(memdb);
        product0 = new ProductAccount("OriginalProduct0").setAmount(13);
        product1 = new ProductAccount("OriginalProduct1").setAmount(26);
        product2 = new ProductAccount("OriginalProduct2").setAmount(17);
        dao.add(product0);
        dao.add(product1);
        dao.add(product2);
    }

    @Test
    void add() {

        Assert.assertEquals("Product is equal",product0,memdb.products.get(0));
        Assert.assertEquals("Product is equal",product1,memdb.products.get(1));
        Assert.assertEquals("Product is equal",product2,memdb.products.get(2));
    }

    @Test
    void get() {

        Assert.assertEquals("Product is equal",product0,dao.get(product0.getName()).get());
        Assert.assertEquals("Product is equal",product1,dao.get(product1.getName()).get());
        Assert.assertEquals("Product is equal",product2,dao.get(product2.getName()).get());

    }

    @Test
    void update() {

        dao.update(product0.setAmount(18));
        dao.update(product1.setAmount(36));
        dao.update(product2.setAmount(72));
        Assert.assertEquals("",Integer.valueOf(18),dao.get(product0.getName()).get().getAmount());
        Assert.assertEquals("",Integer.valueOf(36),dao.get(product1.getName()).get().getAmount());
        Assert.assertEquals("",Integer.valueOf(72),dao.get(product2.getName()).get().getAmount());

    }

    @Test
    void getBalance() {
        Assert.assertEquals("",memdb.products,dao.getBalance());

    }
}


