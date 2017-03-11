package com.classichu.classichu.basic.tool;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by louisgeek on 2016/11/10.
 */
@Deprecated
public class TimerTool {
    private Timer mTimer = new Timer();

    public TimerTool(TimerTask timerTask) {
        mTimerTask = timerTask;
    }

    //=======
    private TimerTask mTimerTask;

    public void startTimer() {
        //启动定时器  延迟0    1分钟执行一次
        mTimer.schedule(mTimerTask, 0, 1 * 60 * 1000);
    }

    public void startTimer(long delay, long period) {
        //启动定时器  延迟delay  period执行一次
        mTimer.schedule(mTimerTask, 0, 1 * 60 * 1000);
    }

    public void endTimer() {
        //停止定时器
        mTimer.cancel();
    }
    //=======
}
