package com.sunstar.cloudseeds.logic.scan.contract;

import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.BasicContract;

/**
 * Created by Administrator on 2017/3/17.
 */

public class ScanQrCodeContract {
    public interface View<D> extends BasicContract.View<D> {
    }

    public interface Presenter {
        void gainData();
    }

    public interface Model<D> extends BasicContract.Model<D>{
        void loadData(String url,String qrcode_value , String secondary_or_tertiary_id ,BasicCallBack<D> basicCallBack);
    }

}
