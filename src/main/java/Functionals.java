import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Functionals {
    public static void main(String[] args) {
        List<String> stringList = Arrays.asList(args);
        List<String> containsHeat = stringList.stream()
                .filter(x -> x.contains("heat"))
                .collect(Collectors.toList());

        List<Boolean> containsAeou = stringList.stream()
                .map(x -> x.contains("aeou"))
                .collect(Collectors.toList());

        System.out.println(Arrays.toString(containsHeat.toArray()));
        System.out.println(Arrays.toString(containsAeou.toArray()));
    }
}
