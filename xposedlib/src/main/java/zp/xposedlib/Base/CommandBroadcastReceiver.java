package zp.xposedlib.Base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zhoupeng on 2017/1/5 0005.
 */

public class CommandBroadcastReceiver extends BroadcastReceiver {
    public static  String  ACTION_COMMAND="zp.xposed.command";
    public static String TARGET_KEY = "target";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!ACTION_COMMAND.equals(intent.getAction())) return;
        try {
            int pid = intent.getIntExtra(TARGET_KEY, 0);
            if (pid!= android.os.Process.myPid()) {
                return;
            }







        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
