package space.harbour.java.hw2;

import java.util.HashMap;
import java.util.Map;

public class Slang {

    public static void main(String[] args) {
        String abbvs = fixAbbreviations(args[0]);
        System.out.print(fixSmiles(abbvs));
    }

    public static String fixAbbreviations(String input) {
        Map<String, String> abbvs = new HashMap();
        abbvs.put("PLZ", "please");
        abbvs.put("FYI", "for your information");
        abbvs.put("GTFO", "please, leave me alone");
        abbvs.put("ASAP", "as soon as possible");

        for (Map.Entry<String, String> item : abbvs.entrySet()) {
            input = input.replaceAll(item.getKey(), item.getValue());
        }
        return input;
    }

    public static String fixSmiles(String input) {
        Map<String, String> smiles = new HashMap();
        smiles.put(":\\)", "\\[smiling\\]");
        smiles.put(":\\(", "\\[sad\\]");
        smiles.put("¯\\\\_\\(ツ\\)_/¯", "\\[such is life\\]");

        for (Map.Entry<String, String> item : smiles.entrySet()) {
            input = input.replaceAll(item.getKey(), item.getValue());
        }
        return input;
    }
}