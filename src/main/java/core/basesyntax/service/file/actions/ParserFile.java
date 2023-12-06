package core.basesyntax.service.file.actions;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ParserFile {
    List<FruitTransaction> parseFruitShop(List<String> transactions);
}
