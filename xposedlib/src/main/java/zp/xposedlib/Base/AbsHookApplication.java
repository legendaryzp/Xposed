package zp.xposedlib.Base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

/**
 * Created by zhoupeng on 2016/11/10 0010.
 */
public abstract class AbsHookApplication implements IExecuteHook{
  protected   String packageName;
  protected Context systemContext;
  protected PackageInfo packageInfo;
    protected List<BaseMethod> methods=new ArrayList<BaseMethod>();
    public AbsHookApplication(String packageName) {
        this.packageName = packageName;
    }
  public  void execute(XC_LoadPackage.LoadPackageParam loadPackageParam){
      initHook(loadPackageParam);
      setSystemContext(loadPackageParam);
      if (!methods.isEmpty()) {
          for (BaseMethod method: methods){
              method.execute(loadPackageParam);
          }
      }
  };

    /**
     * 建议添加方法
     * @param loadPackageParam
     */
    public  abstract void  initHook(XC_LoadPackage.LoadPackageParam loadPackageParam);
    /**
     * 便于进行设置sharepreferences
     * @param loadPackageParam
     */
  public void setSystemContext(XC_LoadPackage.LoadPackageParam loadPackageParam){
    systemContext  = (Context) callMethod(callStaticMethod(findClass("android.app.ActivityThread", null), "currentActivityThread", new Object[0]), "getSystemContext", new Object[0]);
      try {
          packageInfo = systemContext.getPackageManager().getPackageInfo(loadPackageParam.packageName, 0);
      } catch (PackageManager.NameNotFoundException e) {
          e.printStackTrace();
      }
  }
    public  void addMethod(BaseMethod method){
        methods.add(method);
    };
    public String getPackageName() {
        return packageName;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }
}
