package zp.xposedlib.Util;

/**
 * Created by zhoupeng on 2017/1/4 0004.
 */
public class SharedPreference {
    private static SharedPreference mInstance = new SharedPreference();

    public static SharedPreference getInstance() {
        if (mInstance == null) {
            synchronized (SharedPreference.class) {
                if (mInstance == null) {
                    mInstance = new SharedPreference();
                }
            }
        }
        return mInstance;
    }

    private SharedPreference() {
    }
}
