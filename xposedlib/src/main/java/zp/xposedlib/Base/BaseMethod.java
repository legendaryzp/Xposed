
package zp.xposedlib.Base;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by zhoupeng on 2016/11/12 0012.
 */
public  class BaseMethod {
  protected   String description;
    protected  String className;
    protected  String methodName;
    protected  XC_MethodHook xc_methodHook;

  public BaseMethod(String className, String methodName, XC_MethodHook xc_methodHook) {
    this.className = className;
    this.methodName = methodName;
    this.xc_methodHook = xc_methodHook;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  public  void execute(XC_LoadPackage.LoadPackageParam loadPackageParam){
    try {
      XposedHelpers.findAndHookMethod(className,loadPackageParam.classLoader,methodName,xc_methodHook);
    }catch (Throwable  e){
      XposedBridge.log(e);
    }
  };
public static void before(XC_MethodHook.MethodHookParam param){
  String a=param.thisObject.getClass().getSimpleName().toString();
  Log.i("****参数所在类****",a);
  XposedBridge.log("****参数所在类****"+a);}
  public static void after(XC_MethodHook.MethodHookParam param){
    if (param.getResult()!=null)
    { String a= param.getResult().getClass().getSimpleName().toString();
    XposedBridge.log("**结果***"+a);
    Log.i("****结果****",a);}
  }
}
