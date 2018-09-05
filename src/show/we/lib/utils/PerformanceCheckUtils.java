package show.we.lib.utils;

/**
 * Created by CG on 13-10-18.
 *
 * @author ll
 * @version 1.0.0
 */
public class PerformanceCheckUtils {

    private static long mPreviousTime;

    /**
     * init
     */
    public static void init() {
        mPreviousTime = System.currentTimeMillis();
    }

    /**
     * printDuration
     *
     * @param title title
     */
    public static void printDuration(String title) {
        long curTime = System.currentTimeMillis();
        mPreviousTime = curTime;
    }
}
