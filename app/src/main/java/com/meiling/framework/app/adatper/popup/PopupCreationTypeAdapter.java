package com.meiling.framework.app.adatper.popup;


import com.chaychan.adapter.MultipleItemRvAdapter;
import com.meiling.framework.app.adatper.IAdapterItemCallback;

import java.util.List;

import androidx.annotation.Nullable;

public class PopupCreationTypeAdapter extends MultipleItemRvAdapter<CreationTypeEnum> {

    private IAdapterItemCallback<CreationTypeEnum> callback;
    private PopupCreationTypeProvider mProvider;

    public PopupCreationTypeAdapter(@Nullable List<CreationTypeEnum> data, IAdapterItemCallback<CreationTypeEnum> callback) {
        super(data);
        this.callback = callback;
        mProvider = new PopupCreationTypeProvider(callback);
        finishInitialize();
    }

    @Override
    protected int getViewType(CreationTypeEnum payableListBean) {
        return 0;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(mProvider);
    }
}
