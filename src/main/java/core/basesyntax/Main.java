package core.basesyntax;

import core.basesyntax.servise.ShopService;
import core.basesyntax.servise.impl.ShopServiceImpl;

public class Main {

    public static void main(String[] args) {
        ShopService shopService = new ShopServiceImpl();
        shopService.run();
    }
}
