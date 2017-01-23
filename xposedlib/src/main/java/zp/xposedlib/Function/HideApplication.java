package zp.xposedlib.Function;

import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import zp.xposedlib.Base.AbsFunction;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by zhoupeng on 2017/1/18 0018.
 */
public class HideApplication extends AbsFunction{
    private static HideApplication mInstance ;

    public static HideApplication getInstance() {
        if (mInstance == null) {
            synchronized (HideApplication.class) {
                if (mInstance == null) {
                    mInstance = new HideApplication();
                }
            }
        }
        return mInstance;
    }

       String[] hidePackageNameKeys;
    String fakePackageNameKey;

    private HideApplication() {
        super("通过多种手段防止程序模块被侦查达到隐藏app的效果");
    }

    public String[] getHidePackageNameKeys() {
        return hidePackageNameKeys;
    }

    public void setHidePackageNameKeys(String[] hidePackageNameKeys) {
        this.hidePackageNameKeys = hidePackageNameKeys;
    }

    public String getFakePackageNameKey() {
        return fakePackageNameKey;
    }

    public void setFakePackageNameKey(String fakePackageNameKey) {
        this.fakePackageNameKey = fakePackageNameKey;
    }

    @Override
    public void execute(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (hidePackageNameKeys==null&&hidePackageNameKeys.length==0&&fakePackageNameKey==null)return;
        hideModuleOrPackage(loadPackageParam,hidePackageNameKeys,fakePackageNameKey);
    }
    private boolean isTarget(String check,String[]target) {
        boolean result=false;
        if (!isExecutable()) {
            return false;
        }

        for (String s : target) {
            if (check.contains(s)) {
                result=true;
                break;
            }
        }
        return result;
    }
    /**
     * 通过多种手段防止被侦查
     *
     * @param loadPackageParam
     * @param hidePackageNameKeys
     * @param fakePackageNameKey
     */
    private void hideModuleOrPackage(XC_LoadPackage.LoadPackageParam loadPackageParam,
                                     final String[] hidePackageNameKeys, final String fakePackageNameKey) {
        findAndHookMethod("android.app.ApplicationPackageManager", loadPackageParam.classLoader, "getInstalledApplications", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                List<ApplicationInfo> applicationList = (List) param.getResult();
                List<ApplicationInfo> resultapplicationList = new ArrayList<>();
                for (ApplicationInfo applicationInfo : applicationList) {
                    String packageName = applicationInfo.packageName;
                    if (isTarget(packageName, hidePackageNameKeys)) {
                        log("Hid package: " + packageName);
                    } else {
                        resultapplicationList.add(applicationInfo);
                    }
                }
                param.setResult(resultapplicationList);
            }
        });
        findAndHookMethod("android.app.ApplicationPackageManager", loadPackageParam.classLoader, "getInstalledPackages", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                List<PackageInfo> packageInfoList = (List) param.getResult();
                List<PackageInfo> resultpackageInfoList = new ArrayList<>();

                for (PackageInfo packageInfo : packageInfoList) {
                    String packageName = packageInfo.packageName;
                    if (isTarget(packageName, hidePackageNameKeys)) {
                        log("Hid package: " + packageName);
                    } else {
                        resultpackageInfoList.add(packageInfo);
                    }
                }
                param.setResult(resultpackageInfoList);
            }
        });
        findAndHookMethod("android.app.ApplicationPackageManager", loadPackageParam.classLoader, "getPackageInfo", String.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String packageName = (String) param.args[0];
                if (isTarget(packageName, hidePackageNameKeys)) {
                    param.args[0] = fakePackageNameKey;
                    log("Fake package: " + packageName + " as " + fakePackageNameKey);
                }
            }
        });
        findAndHookMethod("android.app.ApplicationPackageManager", loadPackageParam.classLoader, "getApplicationInfo", String.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String packageName = (String) param.args[0];
                if (isTarget(packageName, hidePackageNameKeys)) {
                    param.args[0] = fakePackageNameKey;
                    log("Fake package: " + packageName + " as " + fakePackageNameKey);
                }
            }
        });
        findAndHookMethod("android.app.ActivityManager", loadPackageParam.classLoader, "getRunningServices", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                List<ActivityManager.RunningServiceInfo> serviceInfoList = (List) param.getResult();
                List<ActivityManager.RunningServiceInfo> resultList = new ArrayList<>();

                for (ActivityManager.RunningServiceInfo runningServiceInfo : serviceInfoList) {
                    String serviceName = runningServiceInfo.process;
                    if (isTarget(serviceName, hidePackageNameKeys)) {
                        log("Hid service: " + serviceName);
                    } else {
                        resultList.add(runningServiceInfo);
                    }
                }
                param.setResult(resultList);
            }
        });
        findAndHookMethod("android.app.ActivityManager", loadPackageParam.classLoader, "getRunningTasks", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                List<ActivityManager.RunningTaskInfo> serviceInfoList = (List) param.getResult();
                List<ActivityManager.RunningTaskInfo> resultList = new ArrayList<>();

                for (ActivityManager.RunningTaskInfo runningTaskInfo : serviceInfoList) {
                    String taskName = runningTaskInfo.baseActivity.flattenToString();
                    if (isTarget(taskName, hidePackageNameKeys)) {
                        log("Hid task: " + taskName);
                    } else {
                        resultList.add(runningTaskInfo);
                    }
                }
                param.setResult(resultList);
            }
        });
        findAndHookMethod("android.app.ActivityManager", loadPackageParam.classLoader, "getRunningAppProcesses", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = (List) param.getResult();
                List<ActivityManager.RunningAppProcessInfo> resultList = new ArrayList<>();

                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfos) {
                    String processName = runningAppProcessInfo.processName;
                    if (isTarget(processName, hidePackageNameKeys)) {
                        log("Hid process: " + processName);
                    } else {
                        resultList.add(runningAppProcessInfo);
                    }
                }
                param.setResult(resultList);
            }
        });
    }
}
