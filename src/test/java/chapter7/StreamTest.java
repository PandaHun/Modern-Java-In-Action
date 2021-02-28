package chapter7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class StreamTest {

    private static final long N = 10_000_000L;

    @DisplayName(value = "순차 스트림 1부터 n까지의 합")
    @Test
    public long sequentialSum() {
        return Stream.iterate(1L, i -> i + 1)
                .limit(N)
                .reduce(0L, Long::sum);
    }

    @DisplayName(value = "명령형")
    @Test
    public long iterativeSum() {
        long result = 0;
        for (long i = 1L; i <= N ; i++) {
            result += i;
        }
        return result;
    }

    @DisplayName(value = "순차 스트림을 병렬 스트림으로")
    @Test
    public long parallelSum() {
        return Stream.iterate(1L, i -> i + 1)
                .limit(N)
                .parallel()
                .reduce(0L, Long::sum);
    }

}
