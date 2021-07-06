package template.model;

import java.util.Collections;

public class LHSAncestorPath extends LHS {

    String tag;
    int level;

    public LHSAncestorPath(String tag, int levelAbove) {
        this.tag = tag;
        this.level = levelAbove;
    }

    public int getLevel() {
        return level;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        if (level == 0)
            return "./" + tag;
        return String.join("/", Collections.nCopies(level, "..")) + "/" + tag;
    }
}
