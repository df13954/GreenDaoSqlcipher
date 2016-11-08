package com.ldrong.greendaosqlcipher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.ldrong.greendaosqlcipher.base.AppContext;
import com.ldrong.greendaosqlcipher.base.BaseActivity;
import com.ldrong.greendaosqlcipher.db.DaoSession;
import com.ldrong.greendaosqlcipher.db.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.b1)
    Button b1;
    @BindView(R.id.b2)
    Button b2;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.content)
    TextView content;
    private DaoSession ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ss = AppContext.getGreenDaoSessino();

    }

    @OnClick({R.id.b1, R.id.b2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b1:
                try {
                    User user = new User(null, "test" + System.currentTimeMillis(), 5345564);
                    ss.getUserDao().insert(user);

                } catch (Exception e) {
                    Log.e(TAG, "error: ", e);
                }

                break;
            case R.id.b2:
                try {
                    List<User> kk = ss.getUserDao().loadAll();
                    LogUtils.e(kk);
                } catch (Exception e) {
                    Log.e(TAG, "error: ", e);
                }
                break;
        }
    }
}
