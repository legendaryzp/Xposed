package zp.xposedlib.Function;

import android.os.Build;
import android.view.WindowManager;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import zp.xposedlib.Base.AbsFunction;

/**
 * Created by zhoupeng on 2017/1/18 0018.
 */
public class Screenshot extends AbsFunction{
    private static Screenshot mInstance ;

    public static Screenshot getInstance() {
        if (mInstance == null) {
            synchronized (Screenshot.class) {
                if (mInstance == null) {
                    mInstance = new Screenshot();
                }
            }
        }
        return mInstance;
    }
    private Screenshot() {
        super("阻止添加WindowManager.LayoutParams.FLAG_SECURE标志，可用于截图禁止截图的软件。");
    }

    @Override
    public void execute(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod("android.view.Window", lpparam.classLoader, "setFlags", int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                if (!isExecutable()) {
                    return;
                }
                Integer flags = (Integer) param.args[0];
                flags &=~WindowManager.LayoutParams.FLAG_SECURE;
                param.args[0] = flags;
            }
        });
        if (Build.VERSION.SDK_INT >= 17) {
            XposedHelpers.findAndHookMethod("android.view.SurfaceView", lpparam.classLoader, "setSecure", boolean.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if (!isExecutable()) {
                        return;
                    }
                    param.args[0] = false;
                }
            });
        }
    }
}
