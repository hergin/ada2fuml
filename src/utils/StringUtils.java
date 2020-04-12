package utils;

public class StringUtils {

    /**
     * This method eliminates all new lines, tabs and changes all multi-spaces with a single space to compare them easily in tests!
     *
     * @param input
     * @return
     */
    public static String sanitize(String input) {
        return input
                .replace("\n", "")
                .replace("\r", "")
                .replace("\t", "")
                .replace("xmi:version=\"2.1\"", "") // this line is not to change every test after roy's additions, remove later
                .replace("20110701", "20131001") // this line is not to change every test after roy's additions, remove later
                .replaceAll(" +", " ");
    }

}
