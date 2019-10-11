# EventBusDemo
EventBus 
      
      public class EventBusUtils {

          /**
           * 注册EventBus
           */
          public static void register(Object subscriber) {
              if (!EventBus.getDefault().isRegistered(subscriber)) {
                  EventBus.getDefault().register(subscriber);
              }
          }

          /**
           * 取消注册EventBus
           */
          public static void unregister(Object subscriber) {
              EventBus.getDefault().unregister(subscriber);
          }

          /**
           * 发布订阅事件
           */
          public static void post(Object event) {
              EventBus.getDefault().post(event);
          }

          /**
           * 发布粘性订阅事件
           * 使用postSticky发送事件，那么可以不需要先注册，也能接受到事件，也就是一个延迟注册的过程。
           */
          public static void postSticky(Object event) {
              EventBus.getDefault().postSticky(event);
          }

          /**
           * 移除指定的粘性订阅事件
           */
          public static <T> void removeStickyEvent(Class<T> eventType) {
              T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
              if (stickyEvent != null) {
                  EventBus.getDefault().removeStickyEvent(stickyEvent);
              }
          }

          /**
           * 取消事件传送
           */
          public static void cancelEventDelivery(Object event) {
              EventBus.getDefault().cancelEventDelivery(event);
        }

        /**
         * 移除所有的粘性订阅事件
         */
        public static void removeAllStickyEvents() {
            EventBus.getDefault().removeAllStickyEvents();
        }

    }
    
    
  EventBus的使用 ， MainBean 为消息发送接收的bean实体类
    
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

build.gradle 文件导入

    compile 'org.greenrobot:eventbus:3.1.1'
