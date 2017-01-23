package zp.xposedlib.Method;

import android.content.Context;
import android.content.IntentFilter;

import de.robv.android.xposed.XC_MethodHook;
import zp.xposedlib.Base.BaseMethod;
import zp.xposedlib.Base.CommandBroadcastReceiver;

/**
 * Created by zhoupeng on 2017/1/5 0005.
 */

public class AppEntrance extends BaseMethod {
//   private Context context;
    public AppEntrance() {
        super("android.app.Application","onCreate",new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Context context = (Context) param.thisObject;
                IntentFilter filter = new IntentFilter();
                filter.addAction(CommandBroadcastReceiver.ACTION_COMMAND);
                context.registerReceiver(new CommandBroadcastReceiver(), filter);
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                return;
            }
        });
    }

}
