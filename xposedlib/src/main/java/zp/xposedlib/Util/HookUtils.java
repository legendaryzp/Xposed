package zp.xposedlib.Util;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Created by zhoupeng on 2017/1/6 0006.
 */

public class HookUtils {
    /**
     * 获取一个类中的
     * @param param
     * @param name
     * @param <T>
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static <T> T getHookView(XC_MethodHook.MethodHookParam param, String name) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = param.thisObject.getClass();
        // 通过反射获取控件，无论parivate或者public
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        return  (T) field.get(param.thisObject);
    }
}
