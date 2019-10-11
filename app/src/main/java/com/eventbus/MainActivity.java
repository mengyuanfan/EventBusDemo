package com.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eventbus.eventbus.EventBusUtils;
import com.eventbus.eventbus.eventbean.MainBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBusUtils.register(this);
        sendNewsContent();
    }

    private void sendNewsContent(){
        EventBusUtils.postSticky(new MainBean());
    }

    /** POSTING：默认，表示事件处理函数的线程跟发布事件的线程在同一个线程。
     *  MAIN：表示事件处理函数的线程在主线程(UI)线程，因此在这里不能进行耗时操作。
     *  BACKGROUND：表示事件处理函数的线程在后台线程，因此不能进行UI操作。如果发布事件的线程是主线程(UI线程)，那么事件处理函数将会开启一个后台线程，如果果发布事件的线程是在后台线程，那么事件处理函数就使用该线程。
     *  ASYNC：表示无论事件发布的线程是哪一个，事件处理函数始终会新建一个子线程运行，同样不能进行UI操作
     *  priority:它用来指定订阅方法的优先级，是一个整数类型的值，默认是0，值越大表示优先级越大。
     *  在某个事件被发布出来的时候，优先级较高的订阅方法会首先接受到事件
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 0)
    public void receiveEventBusMessage(MainBean message) {
    }

    // 订阅方法，需要与上面的方法的threadMode一致，并且优先级略高
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void receiveEventBusStickyMessage(MainBean message){
        if (message == null)return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 终止事件的继续分发
        EventBusUtils.cancelEventDelivery(MainBean.class);
        EventBusUtils.unregister(this);
    }

}
