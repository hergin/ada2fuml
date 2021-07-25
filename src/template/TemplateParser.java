package template;

import template.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParser {

    public static Template parseTemplateFromString(List<String> lines) {

        HashMap<Integer, TemplateItem> lastItemsForLevel = new HashMap<>();

        Template result = new Template();

        RHS previousRhs = null;
        int previousLevel = -1;

        for (String line : lines) {
            if(line.trim().startsWith("#")) continue; // comment
            String[] tokens = line.split("--");
            String lhs = tokens[0].trim();
            String rhs = tokens[1].trim();
            int level = (int) lhs.chars().filter(ch -> ch == '>').count();
            String lhsWithoutLevel = lhs.replace(">", "").trim();

            LHS lhsOfTheItem = null;
            if (lhsWithoutLevel.startsWith("../") || lhsWithoutLevel.startsWith("./")) { // ancestor paths
                Matcher dotMatcher = Pattern.compile("[.][.]").matcher(lhsWithoutLevel);
                int ancestorLevel = 0;
                while (dotMatcher.find()) {
                    ancestorLevel++;
                }
                String tag = lhsWithoutLevel.substring(lhsWithoutLevel.lastIndexOf("/")+1);
                lhsOfTheItem = new LHSAncestorPath(tag,ancestorLevel);
            } else if (lhsWithoutLevel.startsWith("[")) { // literal
                String valueOfTheLiteral = lhsWithoutLevel.replace("[", "").replace("]", "");
                lhsOfTheItem = new LHSLiteral(valueOfTheLiteral);
            } else if (lhsWithoutLevel.contains("@")) { // attribute
                if (lhsWithoutLevel.contains("/")) { // there is a path before the attribute name
                    String[] pathTokens = lhsWithoutLevel.split("@");
                    String path = pathTokens[0].trim();
                    String attributeName = pathTokens[1].trim();
                    lhsOfTheItem = new LHSAttributeWithPath(attributeName, path);
                } else {
                    String attributeName = lhsWithoutLevel.replace("@", "").trim();
                    lhsOfTheItem = new LHSAttribute(attributeName);
                }
            } else if (!lhsWithoutLevel.contains("@")) { // tag
                lhsOfTheItem = new LHSTag(lhsWithoutLevel);
            }

            RHS rhsOfTheItem = null;
            if (rhs.contains(" in ")) {
                tokens = rhs.split(" in ");
                rhsOfTheItem = new RHSAttributeInClass(tokens[0].trim(), tokens[1].trim());
            } else if (Character.isUpperCase(rhs.charAt(0))) {
                rhsOfTheItem = new RHSClass(rhs);
            } else if (Character.isLowerCase(rhs.charAt(0))) {
                rhsOfTheItem = new RHSAttribute(rhs);
            }

            if(previousRhs!=null && previousRhs.equals(rhsOfTheItem) && previousLevel==level) { // rhs equals to previous means that it has an alternate lhs
                lastItemsForLevel.get(level).addAlternateLhs(lhsOfTheItem);
            } else {
                TemplateItem current = new TemplateItem(lhsOfTheItem, rhsOfTheItem);
                lastItemsForLevel.put(level, current);
                if (level > 0) {
                    lastItemsForLevel.get(level - 1).addSubItem(current);
                } else if (level == 0) {
                    result.addItem(current);
                }
            }

            previousLevel = level;
            previousRhs = rhsOfTheItem;
        }
        return result;
    }

    public static Template parseTemplateFromString(String lines) {
        return parseTemplateFromString(Arrays.asList(lines.replace("\r", "").split("\n")));
    }

}
