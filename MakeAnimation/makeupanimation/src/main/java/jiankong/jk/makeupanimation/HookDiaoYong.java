package jiankong.jk.makeupanimation;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class HookDiaoYong {
    public String getIntentValue(XC_MethodHook.MethodHookParam param,String intentVaule){
        return ((Activity) param.thisObject).getIntent().getStringExtra(intentVaule);
    }
    public ClassLoader getClassLoader(XC_MethodHook.MethodHookParam param){
        return param.thisObject.getClass().getClassLoader();
    }
    public Class getClass(XC_MethodHook.MethodHookParam param){
        return param.thisObject.getClass();
    }
    public Class<?> selectClass(String className,ClassLoader appClassLoader){
        return  XposedHelpers.findClass(className, appClassLoader);
    }
    public View getViews(Class c,String viewName,XC_MethodHook.MethodHookParam param,boolean Accessible) throws Exception {
        Field field= c.getDeclaredField(viewName);
        field.setAccessible(Accessible);
        return (View) field.get(param.thisObject);
    }
    public String getValues(XC_MethodHook.MethodHookParam param, String vaule) throws Exception {
        Field consultField = XposedHelpers.findField(param.thisObject.getClass(), vaule);
        return (String) consultField.get(param.thisObject);
    }
    public View getViews(XC_MethodHook.MethodHookParam param,String viewName) throws Exception{
        Field kj = XposedHelpers.findField(param.thisObject.getClass(), viewName);
        return (View) kj.get(param.thisObject);
    }
    public void DiaoYongViewFangFa(String fangfaming,String value,View v){
        XposedHelpers.callMethod(v, fangfaming, value);
    }

}
