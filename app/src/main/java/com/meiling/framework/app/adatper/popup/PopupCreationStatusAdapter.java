package com.meiling.framework.app.adatper.popup;


import com.chaychan.adapter.MultipleItemRvAdapter;
import com.meiling.framework.app.adatper.IAdapterItemCallback;

import java.util.List;

import androidx.annotation.Nullable;

public class PopupCreationStatusAdapter extends MultipleItemRvAdapter<CreationStatusEnum> {

    private IAdapterItemCallback<CreationStatusEnum> callback;
    private PopupCreationStatusProvider mProvider;

    public PopupCreationStatusAdapter(@Nullable List<CreationStatusEnum> data, IAdapterItemCallback<CreationStatusEnum> callback) {
        super(data);
        this.callback = callback;
        mProvider = new PopupCreationStatusProvider(callback);
        finishInitialize();
    }

    @Override
    protected int getViewType(CreationStatusEnum payableListBean) {
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(mProvider);
    }
}
