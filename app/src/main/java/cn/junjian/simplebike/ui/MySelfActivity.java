package cn.junjian.simplebike.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import cn.junjian.simplebike.R;
import cn.junjian.simplebike.base.BaseActivity;

/**
 * Created by junjianliu
 * on 17/2/28
 * email:spyhanfeng@qq.com
 */
public class MySelfActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myself);
        initToolBar();
    }

    private void initToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 是否显示返回箭头
        getSupportActionBar().setTitle("");
    }

    private void init(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
