package zp.xposedlib;

import android.content.pm.ApplicationInfo;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import zp.xposedlib.Base.AbsHookApplication;

/**
 * Created by zhoupeng on 2016/11/12 0012.
 */
public class Main implements IXposedHookLoadPackage {
    private static final String ZP_PACKAGENAME = "com.android.reverse";
    List<AbsHookApplication> hookpkgs;
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        // TODO Auto-generated method stub
        if(lpparam.appInfo == null ||
                (lpparam.appInfo.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) !=0){
            return;
        }else if(lpparam.isFirstApplication && !ZP_PACKAGENAME.equals(lpparam.packageName)){
        init();
        for (AbsHookApplication hookApplication : hookpkgs) {
            if(lpparam.packageName==hookApplication.getPackageName()){
               hookApplication.execute(lpparam);

            }
        }
        }
    }

    public  void  init(){
        hookpkgs=new ArrayList<>();
    }
}
