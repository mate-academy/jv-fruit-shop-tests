package core.basesyntax.service.calculationservice;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handlerservice.HandlerService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FruitsServiceImpl implements FruitService {
    private HandlerService handlerService;
    private FruitsDao fruitsDao;

    public FruitsServiceImpl(FruitsDao fruitsDao, HandlerService realizeByActivityFruits) {
        this.fruitsDao = fruitsDao;
        this.handlerService = realizeByActivityFruits;
    }

    @Override
    public void processTransactions(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            handlerService.getHandler(transaction.getOperation()).handle(transaction);
        }
    }

    private Map<String, List<FruitTransaction>> mapFruits(List<FruitTransaction> str) {
        return str.stream()
                .collect(Collectors.groupingBy(FruitTransaction::getFruit));
    }
}
