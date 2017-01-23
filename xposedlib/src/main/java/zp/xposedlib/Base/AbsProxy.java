package zp.xposedlib.Base;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Created by zhoupeng on 2017/1/6 0006.
 */

public abstract class AbsProxy {
    protected  String className;
    protected  String methodNames[];
    protected XC_MethodHook xc_methodHooks[];

    public AbsProxy(String className, String[] methodNames, XC_MethodHook[] xc_methodHooks) {
        this.className = className;
        this.methodNames = methodNames;
        this.xc_methodHooks = xc_methodHooks;
    }

}
