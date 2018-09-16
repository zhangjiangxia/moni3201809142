package bwie.com.moni320180914.di;

import okhttp3.FormBody;

public interface IContract {
interface IView{
  void showData(String msg);
}
interface IPersenter<IView>{
    void AttData(IView iView);
    void DeleteData(IView iView);
    void infoData();
    void infoPost(String keywords);
}
interface IModel{
    interface onCallBacklisenter{
      void stringMsg(String Msg);
    }
    void requestData(onCallBacklisenter onCallBacklisenter);
    void requestDataPost(String keywords, onCallBacklisenter onCallBacklisenter);

    }

}
