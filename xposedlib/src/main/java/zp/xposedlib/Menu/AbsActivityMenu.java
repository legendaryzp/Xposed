package zp.xposedlib.Menu;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import zp.xposedutils.AbsHookMethod;
import zp.xposedutils.IHookMethod;

/**
 * Created by zhoupeng on 2016/11/18 0018.
 */

public abstract class AbsActivityMenu implements IHookMethod {
    AbsHookMethod creatMenu,dealMenuItem;
    MenuItem.OnMenuItemClickListener menuItemClickListener;
    public AbsActivityMenu(String className) {
        menuItemClickListener=setOnMenuItemClickedListener();
        creatMenu=new AbsHookMethod(className, "onCreateOptionsMenu", new XC_MethodHook() {
            boolean isadd=false;
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            //该方法本身只使用一次
                    Activity activity= (Activity) param.thisObject;
                    addSharedPreferences(activity);
                Menu menu = (Menu) param.args[0];
                addMenu(menu);
            }
        }) {
            @Override
            public void hookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam) {
                XposedHelpers.findAndHookMethod(className,loadPackageParam.classLoader,methodName,Menu.class,xc_methodHook);
            }
        };
        dealMenuItem=new AbsHookMethod(className, "onOptionsItemSelected", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                MenuItem menuItem=(MenuItem)param.args[0];
                if (menuItemClickListener==null)return;
                if (menuItemClickListener.onMenuItemClick(menuItem)) {
                    param.setResult(true);
                }
            }
        }) {
            @Override
            public void hookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam) {
                XposedHelpers.findAndHookMethod(className,loadPackageParam.classLoader,methodName,Menu.class,xc_methodHook);
            }
        };

    }

    @Override
    public void hookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        creatMenu.hookMethod(loadPackageParam);
        dealMenuItem.hookMethod(loadPackageParam);
    }
    public abstract  void addMenu(Menu menu);
    public abstract  MenuItem.OnMenuItemClickListener setOnMenuItemClickedListener();
    public abstract void addSharedPreferences(Activity activity);
}
