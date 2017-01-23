package zp.xposedlib.Base;

/**
 * Created by zhoupeng on 2017/1/17 0017.
 */

public abstract class AbsFunction implements IExecuteHook {
    protected  String description;
    protected boolean executable=false;
    public AbsFunction(String functionDescription) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExecutable() {
        return executable;
    }

    public AbsFunction setExecutable(boolean executable) {
        this.executable = executable;
        return this;
    }
}
