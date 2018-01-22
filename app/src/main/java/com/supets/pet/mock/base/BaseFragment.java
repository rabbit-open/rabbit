package com.supets.pet.mock.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {

    private View mContentView;
    protected boolean mIsLazyLoad = false;
    protected boolean mIsProcessed = false;
    protected boolean mIsOnCreated = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //fragment已经关联到activity,可以通信了
        // 获得activity的传递的值 就可以进行 与activity的通信里，
        // 当然也可以使用getActivity(),前提是这个fragment已经和宿主的activity关联，
        // 并且没有脱离，他只调用一次。
        initProps();
        initCallBack(context);
    }

    protected void initCallBack(Context context) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //系统创建fragment的时候回调他，在他里面实例化一些变量
        //这些个变量主要是：当你 暂停 停止的时候 你想保持的数据
        //如果我们要为fragment启动一个后台线程，可以考虑将代码放于此处。
        //参数是：Bundle savedInstance, 用于保存 Fragment 参数, Fragement 也可以 重写 onSaveInstanceState(BundleoutState) 方法, 保存Fragement状态;
        //可以用于 文件保护
        //他只调用一次。
    }

    protected void initProps() {

    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //第一次使用的时候 fragment会在这上面画一个layout出来，
        // 为了可以画控件 要返回一个 布局的view，也可以返回null。

        //当系统用到fragment的时候 fragment就要返回他的view，
        // 越快越好 ，所以尽量在这里不要做耗时操作，
        // 比如从数据库加载大量数据显示listview，
        //当然线程还是可以的。

        //给当前的fragment绘制ui布局，可以使用线程更新UI，
        // 说白了就是加载fragment的布局的。
        //这里一般都先判断是否为null。
        //这样进行各判断省得每次都要加载，减少资源消耗

        Log.v("onCreateView", "tabhost切换每次执行");

        if (mContentView == null) {
            int layout = getContentLayout();
            mContentView = layout == 0 ? getContentView() : inflater.inflate(layout, container, false);
            if (mContentView == null) {
                throw new RuntimeException("content view is null.");
            }
            findViews(mContentView);
            setListeners();

            if (!mIsLazyLoad) {
                process();
                mIsProcessed = true;
                mIsOnCreated = true;
                Log.v("manualProcess", getClass().getName() + "process");
            }
        }
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当Activity中的onCreate方法执行完后调用。

        //注意了：
        //从这句官方的话可以看出：当执行onActivityCreated()的时候 activity的
        //onCreate才刚完成。
        //所以在onActivityCreated()调用之前 activity的onCreate可能还没有完成，
        //所以不能再onCreateView()中进行 与activity有交互的UI操作，UI交互操作可以在onActivityCreated()里面进行。
        //所以呢：这个方法主要是初始化那些你需要你的父Activity或者Fragment的UI已经被完
        // 整初始化才能初始化的元素。
        //如果在onCreateView里面初始化空间 会慢很多，比如listview等
    }

    @SuppressWarnings("ALL")
    @Override
    public void onResume() {
        super.onResume();

        try {
            if (skipUmengAnalytic()) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ALL")
    @Override
    public void onPause() {
        super.onPause();
        try {
            if (skipUmengAnalytic()) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setLazyLoad(boolean lazyLoad) {
        mIsLazyLoad = lazyLoad;
    }

    private boolean skipUmengAnalytic() {
        return false;
    }

    protected String getStringArgument(String key) {
        Bundle arguments = getArguments();
        return arguments != null ? arguments.getString(key) : null;
    }

    protected View getContentView() {
        return mContentView;
    }

    public abstract int getContentLayout();

    public abstract void findViews(View view);

    public abstract void setListeners();

    public abstract void process();

    public void scrollToTop() {
    }

    public void manualProcess() {
        if (!mIsProcessed && mIsLazyLoad) {
            process();
            Log.v("manualProcess", getClass().getName() + "manualProcess");
            mIsProcessed = true;
        } else if (isOnResumedLoad()) {
            manualProcessSencond();
        }
    }

    @Override
    public void onDestroyView() {
        //Fragment中的布局被移除时调用。
        //表示fragemnt销毁相关联的UI布局， 清除所有跟视图相关的资源。

        //ViewPager+Fragment，由于ViewPager的缓存机制.会出现
        //例如：有四个 fragment 当滑动到第四页的时候 第一页执行onDestroyView(),但没有
        //执行onDestroy。他依然和activity关联。当在滑动到第一页的时候又执行了onCreateView()。

        if (mContentView.getParent() != null) {
            ((ViewGroup) mContentView.getParent()).removeView(mContentView);
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean isOnResumedLoad() {
        //懒加载未处理,不走onresume
        //不是懒加载处理，不走其他情况下都走
        boolean load = (mIsLazyLoad && !mIsProcessed) || (!mIsLazyLoad && mIsOnCreated);
        Log.v("manualProcess", getClass().getName() + "onResume=" + !load);

        if (mIsOnCreated) {
            mIsOnCreated = false;
        }

        return !load;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onResumeEx();
        } else {
            onPauseEx();
        }
    }

    public void onResumeEx() {
    }

    public void onPauseEx() {
    }

    public void manuRefresh() {
    }

    public void manualProcessSencond() {

    }
}
