package com.bocop.zyt.jx.baseUtil.net;


import com.bocop.zyt.jx.base.BaseActivity;
import com.bocop.zyt.jx.base.BaseApplication;
import com.bocop.zyt.jx.base.BaseFragment;
import com.bocop.zyt.jx.baseUtil.asynchttpclient.RequestParams;

/**
 * Created by hwt on 14/11/4.
 * 请求响应的结果数据处理
 */
public abstract class Action {
    protected UiObject uiObject;
    protected BaseApplication application;

    public Action() {
    }

    public Action init(UiObject uiObject, BaseApplication baseApplication) {
        this.uiObject = uiObject;
        this.application = baseApplication;
        return this;
    }

    public Action(UiObject uiObject, BaseApplication baseApplication) {
        this.uiObject = uiObject;
        this.application = baseApplication;
    }

    public abstract Http excute(String requestUrl, RequestParams params);

    public Object getUiObject() {
        return uiObject;
    }

    /**
     * 判断当前操作的 UI 对象是否可用
     *
     * @return
     */
    public boolean isNative() {
        if (uiObject != null) {
            if (uiObject instanceof BaseActivity) {
                BaseActivity activity = ((BaseActivity) uiObject);
                return !activity.isFinishing();
            } else if (uiObject instanceof BaseFragment) {
                BaseFragment fragment = ((BaseFragment) uiObject);
                return !fragment.isRemoving();
            }
            return true;
        }
        return false;
    }

    public void reset(){
        uiObject = null;
        application = null;
    }

}
