package zhulei.com.stone.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import zhulei.com.stone.data.model.entity.Message;
import zhulei.com.stone.ui.base.BaseActivity;
import zhulei.com.stone.ui.base.BaseFragment;
import zhulei.com.stone.ui.fragment.CommentFragment;

/**
 * Created by zhulei on 16/6/19.
 */
public class CommentActivity extends BaseActivity {

    public static final String ARG_MESSAGE = "arg_message";
    private Message mMessage;

    @Override
    protected BaseFragment getFirstFragment() {
        return CommentFragment.newInstance(mMessage);
    }

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        Bundle arg = intent.getExtras();
        mMessage = (Message) arg.getSerializable(ARG_MESSAGE);
    }
}
