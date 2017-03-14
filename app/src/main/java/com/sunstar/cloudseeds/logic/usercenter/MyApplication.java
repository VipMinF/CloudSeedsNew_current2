package com.sunstar.cloudseeds.logic.usercenter;

import android.app.Application;
import android.content.Context;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

public class MyApplication extends Application {

	public static boolean  isHasUpdate=false;
	public static String versionStr="";
	public static MyApplication instance;
	public static Context mContext;
	@Override
	public void onCreate() {
		super.onCreate();
		loadCheckUpdate();
	}

	private void loadCheckUpdate() {

		UmengUpdateAgent.setUpdateAutoPopup(false);//更新提示的开关
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
		    @Override
		    public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
		    	
//		    	LogUtil.i("louis==app=updateStatus==2="+updateStatus);
//		    	LogUtil.i("louis==app=updateInfo.version==2="+updateInfo.version);
		    	
		        switch (updateStatus) {
		        case UpdateStatus.Yes: // has update
		            //UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
		        	MyApplication.isHasUpdate=true;
		        	versionStr=updateInfo.version;
		        	 //	tv_new.setVisibility(View.VISIBLE);
		            break;
		        case UpdateStatus.No: // has no update
		          //  Toast.makeText(mContext, "没有更新", Toast.LENGTH_SHORT).show();
		        	 //	tv_new.setVisibility(View.INVISIBLE);
		        	MyApplication.isHasUpdate=false;
		            break;
		        case UpdateStatus.NoneWifi: // none wifi
		          //  Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
		        	MyApplication.isHasUpdate=false;
		        	break;
		        case UpdateStatus.Timeout: // time out
		          //  Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT).show();
		        	MyApplication.isHasUpdate=false;
		        	break;
		        }
		    }
		});
		 UmengUpdateAgent.update(this);//更新
		}

}

