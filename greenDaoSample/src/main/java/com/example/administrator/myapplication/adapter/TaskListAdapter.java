package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Task;

import java.util.List;

public class TaskListAdapter  extends BaseQuickAdapter<Task, BaseViewHolder> {

    public TaskListAdapter(Context context, @Nullable List<Task> data) {
        super(R.layout.item_task, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, Task item) {
        helper.setText(R.id.tv_taskName,item.getTaskName());
        helper.setText(R.id.tv_taskContent,item.getTaskContent());
    }
}
