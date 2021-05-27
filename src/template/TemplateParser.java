package template;

import template.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TemplateParser {

    public static Template parseTemplateFromString(List<String> lines) {

        HashMap<Integer, TemplateItem> lastItemsForLevel = new HashMap<>();

        Template result = new Template();

        for (String line : lines) {
            String[] tokens = line.split("--");
            String lhs = tokens[0].trim();
            String rhs = tokens[1].trim();
            int level = (int) lhs.chars().filter(ch -> ch == '>').count();
            String lhsWithoutLevel = lhs.replace(">", "").trim();

            LHS lhsOfTheItem = null;
            if (lhsWithoutLevel.contains("@")) { // attribute
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

            TemplateItem current = new TemplateItem(lhsOfTheItem, rhsOfTheItem);
            lastItemsForLevel.put(level, current);
            if (level > 0) {
                lastItemsForLevel.get(level - 1).addSubItem(current);
            } else if (level == 0) {
                result.addItem(current);
            }
        }
        return result;
    }

    public static Template parseTemplateFromString(String lines) {
        return parseTemplateFromString(Arrays.asList(lines.replace("\r", "").split("\n")));
    }

}
