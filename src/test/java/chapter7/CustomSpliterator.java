package chapter7;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CustomSpliterator {

    private final String SENTENCE = "nel mezzo del cammin di nostra vita mi ritrovai per una selva oscura che la diritta via era smarrita";
    private final int counter;
    private final boolean lastSpace;

    public CustomSpliterator(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    public int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) {
                    counter++;
                }
                lastSpace = false;
            }
        }
        return counter;
    }

    public CustomSpliterator accumulate(Character c) {
        if (Character.isWhitespace(c)) {
            return lastSpace ? this : new CustomSpliterator(counter, true);
        } else {
            return lastSpace ? new CustomSpliterator(counter + 1, false) : this;
        }
    }

    public CustomSpliterator combine(CustomSpliterator customSpliterator) {
        return new CustomSpliterator(counter + customSpliterator.counter, customSpliterator.lastSpace);
    }

    public int getCounter() {
        return counter;
    }

    private int countWords(Stream<Character> stream) {
        CustomSpliterator customSpliterator = stream.reduce(new CustomSpliterator(0, true),
                CustomSpliterator::accumulate,
                CustomSpliterator::combine);
        return customSpliterator.getCounter();
    }

    @Test
    void testIteratively() {
        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");
    }


    @Test
    void testParallel() {
        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
                .mapToObj(SENTENCE::charAt);
        System.out.println("Found " + countWords(stream) + " words");
    }
}
