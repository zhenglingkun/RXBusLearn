package zlk.com.rxbuslearn.fragmentlearn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zlk.com.rxbuslearn.R;

/**
 * Created by kun on 2016/7/14.
 */
public class LifecycleFragmentActivity extends AppCompatActivity {

    @BindView(R.id.add_bt)
    Button mAddBt;
    @BindView(R.id.replace_bt)
    Button mReplaceBt;
    @BindView(R.id.add1_bt)
    Button mAdd1Bt;
    @BindView(R.id.replace1_bt)
    Button mReplace1Bt;

    private AddFragment mAddFragment;
    private AddFragment mAddFragment1;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_lifecycle);
        ButterKnife.bind(this);
        mAddFragment = AddFragment.newInstance("First");
        mAddFragment1 = AddFragment.newInstance("Second");
    }

    @OnClick({R.id.add_bt, R.id.replace_bt, R.id.add1_bt, R.id.replace1_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_bt:

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container_fragment, mAddFragment)
                        .commit();

//                addFragment(mCurrentFragment, mAddFragment);
//                mCurrentFragment = mAddFragment;
                break;
            case R.id.add1_bt:

                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mAddFragment)
                        .add(R.id.container_fragment, mAddFragment1)
                        .addToBackStack("fragment1")
                        .commit();

//                addFragment(mCurrentFragment, mAddFragment1);
//                mCurrentFragment = mAddFragment1;
                break;
            case R.id.replace_bt:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.container_fragment, new ReplaceFragment())
//                        .commit();
                break;
            case R.id.replace1_bt:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.container_fragment, new AddFragment())
//                        .commit();
                break;
        }
    }

    private void addFragment(Fragment currentFragment, Fragment toFragment) {

        if (currentFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_fragment, toFragment)
                    .commit();
        } else {
            if (currentFragment.equals(toFragment)) {
                return;
            }

            if (currentFragment.isAdded() && toFragment.isAdded()) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(currentFragment)
                        .show(toFragment)
                        .commit();
            } else {
                if (currentFragment.isAdded()) {
                    if (!toFragment.isAdded()) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .hide(currentFragment)
                                .add(R.id.container_fragment, toFragment)
                                .commit();
                    }
                } else {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.container_fragment, currentFragment)
                            .commit();
                }
            }
        }

    }
}
