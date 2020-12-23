package com.meiling.framework.app.adatper.popup;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.chaychan.adapter.ItemProviderTag;
import com.meiling.framework.app.R;
import com.meiling.framework.app.adatper.IAdapterItemCallback;

@ItemProviderTag(
        viewType = 0,
        layout = R.layout.popup_creation_status_item)
public class PopupCreationTypeProvider extends BaseItemProvider<CreationTypeEnum> {

    private IAdapterItemCallback<CreationTypeEnum> callback;

    public PopupCreationTypeProvider(IAdapterItemCallback<CreationTypeEnum> callback) {
        this.callback = callback;
    }

    @Override
    public void convert(BaseViewHolder holder, final CreationTypeEnum bean, final int position) {
        TextView itemName = holder.getView(R.id.itemName);
        itemName.setText(bean.getDesc());
    }

    @Override
    public void onClick(BaseViewHolder holder, CreationTypeEnum bean, int position) {
        if (callback != null) {
            callback.adapterItemClick(bean, R.id.itemName, position, null);//
        }
    }

    @Override
    public boolean onLongClick(BaseViewHolder holder, CreationTypeEnum bean, int position) {
        return false;
    }
}
