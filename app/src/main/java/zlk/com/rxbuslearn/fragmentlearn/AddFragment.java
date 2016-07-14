package zlk.com.rxbuslearn.fragmentlearn;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zlk.com.rxbuslearn.R;

/**
 * Created by kun on 2016/7/14.
 */
public class AddFragment extends Fragment {

    private static final String TAG = AddFragment.class.getSimpleName();
    @BindView(R.id.add_tv)
    TextView mAddTv;
    private String mArg;

    public static AddFragment newInstance(String arg) {
        AddFragment fragment = new AddFragment();
        Bundle bundle = new Bundle();
        bundle.putString("arg", arg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: mArg = " + mArg);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArg = getArguments().getString("arg");
        Log.i(TAG, "onCreate: mArg = " + mArg);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: mArg = " + mArg);
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: arg = " + mArg);
        mAddTv.setText("This is add " + mArg + " fragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: mArg = " + mArg);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: mArg = " + mArg);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: mArg = " + mArg);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: mArg = " + mArg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: mArg = " + mArg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: mArg = " + mArg);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: mArg = " + mArg);
    }
}
