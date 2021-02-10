package chapter5;


import chapter5.domain.Trader;
import chapter5.domain.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class TraderAndTransaction {

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2011, 400),
            new Transaction(raoul, 2012, 1000),
            new Transaction(mario, 2012, 700),
            new Transaction(mario, 2012, 710),
            new Transaction(alan, 2012, 950)
    );

    @DisplayName(value = "2011년도 트랜잭션을 찾아 오름차순으로 정렬")
    @Test
    void ascendingTest() {
        List<Transaction> transactionsIn2011 = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(Collectors.toList());

        Assertions.assertEquals(transactionsIn2011.get(0).getTrader().getName(), brian.getName());
    }

    @DisplayName(value = "거래자가 근무하는 모든 도시를 중복 없이 나열")
    @Test
    void getAllCitiesWithoutDuplicate() {
        Set<String> cities = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .collect(Collectors.toSet());
        Assertions.assertEquals(2, cities.size());
    }

    @DisplayName(value = "Cambridge에 거주하는 모든 거래자를 찾아서 이름순으로 정렬")
    @Test
    void getAllTradersNameAscendingInCambridge() {
        List<Trader> traderNames = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(Collectors.toList());
        Assertions.assertEquals(traderNames.size(), 3);
    }

    @DisplayName(value = "모든 거래자의 이름을 알파벳 순으로 정렬")
    @Test
    void getAllTraderNameAscendingAlphabet() {
        String traders = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(traders);
    }

    @DisplayName(value = "Milan에 거주중인 Trader 찾기")
    @Test
    void findTraderInMilan() {
        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t->t.getCity().equals("Milan"))
                .distinct()
                .collect(Collectors.toList());
        Assertions.assertEquals(traders.size(), 1);
    }

    @DisplayName(value = "Cambridge에 거주하는 거래자의 모든 트랙잭션 출력")
    @Test
    void findAllTransactionInCambridge() {
        transactions.stream()
                .filter(t->t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    @DisplayName(value = "전체 트랙잭션 중 최댓값")
    @Test
    void findMaxValueInTransaction() {
        int max = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max).orElse(0);
        Assertions.assertEquals(max, 1000);
    }

    @DisplayName(value = "전체 트랙잭션 중 최솟값")
    @Test
    void findMinValueInTransaction() {
        int min = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min).orElse(0);
        Assertions.assertEquals(min, 300);
    }

}
