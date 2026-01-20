package robotx.stx_libraries;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XRobotContext {

    public enum ModuleType {
        ACTIVE,
        INACTIVE,
        AUTONOMOUS
    }

    private final Map<Class<?>, XModule> modules = new HashMap<>();
    /**
     * List of XModules which runs throughout OpMode.
     */
    public final ArrayList<XModule> activeModules = new ArrayList<>();
    /**
     * Modules which run only in the initialization process (i.e. modules which do not call start(), loop(), or stop())
     */
    public final ArrayList<XModule> inactiveModules = new ArrayList<>();
    public final ArrayList<XModule> autonomousModules = new ArrayList<>();

    public final OpMode op;

    public XRobotContext(OpMode op){
        this.op = op;
    }

    public void registerModule(XModule module, ModuleType type){
        modules.put(module.getClass(), module);
        switch(type){
            case ACTIVE:
                activeModules.add(module);
                break;
            case INACTIVE:
                inactiveModules.add(module);
                break;
            case AUTONOMOUS:
                autonomousModules.add(module);
                break;
        }
    }

    public XModule getModule(Class<?> type){
        return modules.get(type);
    }
}
