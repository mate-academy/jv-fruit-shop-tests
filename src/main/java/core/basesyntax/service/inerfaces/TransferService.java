package core.basesyntax.service.inerfaces;

import core.basesyntax.model.FruitTransferDto;
import java.util.List;

public interface TransferService {
    List<FruitTransferDto> parse(List<String> list);
}
