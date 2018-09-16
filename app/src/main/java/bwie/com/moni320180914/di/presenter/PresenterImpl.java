package bwie.com.moni320180914.di.presenter;

import java.lang.ref.WeakReference;

import bwie.com.moni320180914.di.IContract;
import bwie.com.moni320180914.di.model.ModelImpl;

public class PresenterImpl implements IContract.IPersenter<IContract.IView> {
    IContract.IView iView;
    private IContract.IModel iModel;
    private WeakReference<IContract.IView> iViewWeakReference;
    private WeakReference<IContract.IModel> iModelWeakReference;

    @Override
    public void AttData(IContract.IView iView) {
        this.iView = iView;
        iModel = new ModelImpl();
        iViewWeakReference = new WeakReference<>(iView);
        iModelWeakReference = new WeakReference<>(iModel);
    }

    @Override
    public void DeleteData(IContract.IView iView) {
        iViewWeakReference.clear();
        iModelWeakReference.clear();
    }

    @Override
    public void infoData() {
        iModel.requestData(new IContract.IModel.onCallBacklisenter() {
            @Override
            public void stringMsg(String Msg) {
                iView.showData(Msg);
            }
        });
    }

    @Override
    public void infoPost(String keywords) {
        iModel.requestDataPost(keywords,new IContract.IModel.onCallBacklisenter() {
            @Override
            public void stringMsg(String Msg) {
                iView.showData(Msg);
            }
        });
    }
}
