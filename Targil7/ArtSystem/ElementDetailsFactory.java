 

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//A utility class converting element description to the corresponding element instance
public class ElementDetailsFactory {
    public static void main(String[] args) {
        Element details = ElementDetailsFactory.getPaintingElement("island name: Madagascar, diameter: 8");
    }
//    island length: 20, width: 10
//    lake length: 30, width: 30
//    lake/boat material: Timber, length: 12, width: 15
//    lake/flag Color: Red, length: 12, width: 15
//    lake/island/tree leaves: 2500, length: 12, width: 15
//    lake/island/kid birth year: 2013, hair color: Black, length: 12, width: 15

    private final static String PATH_CAPTURE_PATTERN = "^(?:([^ ]*)/)?([^ ]*) (.*)";
    private final static String KITE_CAPTURE_PATTERN = "color: (\\w+), width: (\\d+), height: (\\d+)";
    private final static String BOAT_CAPTURE_PATTERN = "material: (\\w+), width: (\\d+), length: (\\d+)";
    private final static String TREE_CAPTURE_PATTERN = "leaves: (\\d+), width: (\\d+), height: (\\d+)";
    private final static String FLAG_CAPTURE_PATTERN = "Color: (\\w+), poleHeight: (\\d+), width: (\\d+), length: (\\d+)";
    private final static String KID_CAPTURE_PATTERN = "birth year: (\\d+), hair color: (\\w+), width: (\\d+), height: (\\d+)";
    private final static String ISLAND_CAPTURE_PATTERN = "name: (\\w+), diameter: (\\d+)";
    private final static String LAKE_CAPTURE_PATTERN = "name: (\\w+), diameter: (\\d+)";
    public static Element getPaintingElement(String description){
        Matcher pathMatcher = getMatcher(PATH_CAPTURE_PATTERN, description);
        String elementPath = pathMatcher.group(1);
        String elementType = pathMatcher.group(2);
        String elementDescription = pathMatcher.group(3);
        Matcher matcher;
        switch (elementType){

            case "island":
                matcher=getMatcher(ISLAND_CAPTURE_PATTERN, elementDescription);
                return new Island(matcher.group(1),Double.parseDouble(matcher.group(2)),elementPath);
            case "lake":
                matcher=getMatcher(LAKE_CAPTURE_PATTERN, elementDescription);
                return new Lake(matcher.group(1),Double.parseDouble(matcher.group(2)),elementPath);
            case "tree":

                matcher=getMatcher(TREE_CAPTURE_PATTERN, elementDescription);
                return new Tree(Double.parseDouble(matcher.group(2)),
                        Double.parseDouble(matcher.group(3)), Integer.parseInt(matcher.group(1)), elementPath);

            case "boat":
                matcher=getMatcher(BOAT_CAPTURE_PATTERN, elementDescription);

                return new Boat(Double.parseDouble(matcher.group(2)),
                        Double.parseDouble(matcher.group(3)), Material.valueOf(matcher.group(1)), elementPath);
            case "flag":
                matcher=getMatcher(FLAG_CAPTURE_PATTERN, elementDescription);

                return new Flag(Double.parseDouble(matcher.group(3)),
                        Double.parseDouble(matcher.group(4)),
                        Color.valueOf(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        elementPath
                    );
            case "kid":
                matcher=getMatcher(KID_CAPTURE_PATTERN, elementDescription);
                return new Kid(Double.parseDouble(matcher.group(3)),
                        Double.parseDouble(matcher.group(4)), Integer.parseInt(matcher.group(1)),
                        Color.valueOf(matcher.group(2)),elementPath);
            case "kite":matcher=getMatcher(KITE_CAPTURE_PATTERN, elementDescription);
                        return new Kite(Double.parseDouble(matcher.group(2)),
                        Double.parseDouble(matcher.group(3)), Color.valueOf(matcher.group(1)) ,elementPath);
        }
        throw new RuntimeException("wrong file type");
    }
    public static Matcher getMatcher(String regex, String toMatch){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(toMatch);
        matcher.find();
        return matcher;
    }
}
