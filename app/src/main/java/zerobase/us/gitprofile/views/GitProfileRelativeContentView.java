package zerobase.us.gitprofile.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;

/**
 * Created by arvindchellapondy on 3/11/17.
 */

public class GitProfileRelativeContentView extends RelativeLayout {

    private static final String LOG_TAG = GitProfileRelativeContentView.class.getSimpleName();

    public GitProfileRelativeContentView(Context context)
    {
        this(context, null, 0);
    }

    public GitProfileRelativeContentView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public GitProfileRelativeContentView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GitProfileRelativeContentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
    }
}
