package zp.xposedlib.Base;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by zhoupeng on 2017/1/17 0017.
 */

public interface IExecuteHook {
    void execute(XC_LoadPackage.LoadPackageParam loadPackageParam);
}
