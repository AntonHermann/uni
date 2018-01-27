import java.io.IOException;
import java.nio.file.*;
import java.net.URI;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.Map;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class HuffmanEncode {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Expected 1 parameter: <file>");
            System.exit(1);
        }

        Path path = Paths.get(args[0]);

        Map<Character, Integer> charCounts = null;

        try (Stream<String> lines = Files.lines(path)) {
            charCounts = genCharQuantities(lines);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (charCounts.size() == 0) {
            System.err.println("Please supply a non-empty file!");
            System.exit(1);
        }

        HuffmanTree ht = new HuffmanTree(charCounts);

        BinaryIOWriter biow = new BinaryIOWriter(path.getFileName().toString() + ".hc");
        biow.writeStr(ht.getCodebookString());
        try (Stream<String> lines = Files.lines(path)) {
            IntStream chars = lines.flatMapToInt(line -> line.chars());
            chars.forEach(c -> {
                char ch = (char) c;
                boolean[] code = ht.getCodeword(ch);
                biow.write(code);
            });
            biow.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println();
    }


    protected static Map<Character, Integer> genCharQuantities(Stream<String> lines) {
        //build map (fast access to specific key/char)
        Map<Character, Integer> charCounts = new Hashtable<>();

        IntStream chars = lines.flatMapToInt(line -> line.chars());
        chars.forEach(c -> {
            char ch = (char) c;
            Integer curr = charCounts.get(ch);
            if (curr == null) {
                charCounts.put(ch, 1);
            } else {
                charCounts.put(ch, curr+1);
            }
        });

        return charCounts;
    }
}
