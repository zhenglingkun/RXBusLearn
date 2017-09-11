package zlk.com.rxbuslearn.recycler;

import android.os.Build.VERSION;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;
import java.util.Set;

public abstract class SimpleClickListener implements OnItemTouchListener {

    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;
    private Set<Integer> childClickViewIds;
    private Set<Integer> longClickViewIds;
    protected BaseQuickAdapter baseQuickAdapter;
    public static String TAG = "SimpleClickListener";
    private boolean mIsPrepressed = false;
    private boolean mIsShowPress = false;
    private View mPressedView = null;

    public SimpleClickListener() {
    }

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (this.recyclerView == null) {
            this.recyclerView = rv;
            this.baseQuickAdapter = (BaseQuickAdapter) this.recyclerView.getAdapter();
            this.mGestureDetector = new GestureDetectorCompat(this.recyclerView.getContext(), new SimpleClickListener.ItemTouchHelperGestureListener(this.recyclerView));
        }

        if (!this.mGestureDetector.onTouchEvent(e) && e.getActionMasked() == 1 && this.mIsShowPress) {
            if (this.mPressedView != null) {
                BaseViewHolder vh = (BaseViewHolder) this.recyclerView.getChildViewHolder(this.mPressedView);
                if (vh == null || !this.isHeaderOrFooterView(vh.getItemViewType())) {
                    this.mPressedView.setPressed(false);
                }
            }

            this.mIsShowPress = false;
            this.mIsPrepressed = false;
        }

        return false;
    }

    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        this.mGestureDetector.onTouchEvent(e);
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private void setPressViewHotSpot(MotionEvent e, View mPressedView) {
        if (VERSION.SDK_INT >= 21 && mPressedView != null && mPressedView.getBackground() != null) {
            mPressedView.getBackground().setHotspot(e.getRawX(), e.getY() - mPressedView.getY());
        }

    }

    public abstract void onItemClick(BaseQuickAdapter var1, View var2, int var3);

    public abstract void onItemLongClick(BaseQuickAdapter var1, View var2, int var3);

    public abstract void onItemChildClick(BaseQuickAdapter var1, View var2, int var3);

    public abstract void onItemChildLongClick(BaseQuickAdapter var1, View var2, int var3);

    public boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        if (view.getVisibility() != View.VISIBLE) {
            return false;
        } else {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            return ev.getRawX() >= (float) x && ev.getRawX() <= (float) (x + view.getWidth()) && ev.getRawY() >= (float) y && ev.getRawY() <= (float) (y + view.getHeight());
        }
    }

    private boolean isHeaderOrFooterPosition(int position) {
        if (this.baseQuickAdapter == null) {
            if (this.recyclerView == null) {
                return false;
            }

            this.baseQuickAdapter = (BaseQuickAdapter) this.recyclerView.getAdapter();
        }

        int type = this.baseQuickAdapter.getItemViewType(position);
        return type == 1365 || type == 273 || type == 819 || type == 546;
    }

    private boolean isHeaderOrFooterView(int type) {
        return type == 1365 || type == 273 || type == 819 || type == 546;
    }

    private class ItemTouchHelperGestureListener extends SimpleOnGestureListener {
        private RecyclerView recyclerView;

        public boolean onDown(MotionEvent e) {
            SimpleClickListener.this.mIsPrepressed = true;
            SimpleClickListener.this.mPressedView = this.recyclerView.findChildViewUnder(e.getX(), e.getY());
            super.onDown(e);
            return false;
        }

        public void onShowPress(MotionEvent e) {
            if (SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                SimpleClickListener.this.mIsShowPress = true;
            }

            super.onShowPress(e);
        }

        public ItemTouchHelperGestureListener(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            if (SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                if (this.recyclerView.getScrollState() != 0) {
                    return false;
                }

                View pressedView = SimpleClickListener.this.mPressedView;
                BaseViewHolder vh = (BaseViewHolder) this.recyclerView.getChildViewHolder(pressedView);
                if (SimpleClickListener.this.isHeaderOrFooterPosition(vh.getLayoutPosition())) {
                    return false;
                }

                SimpleClickListener.this.childClickViewIds = vh.getChildClickViewIds();
                Iterator it;
                View childView;
                if (SimpleClickListener.this.childClickViewIds != null && SimpleClickListener.this.childClickViewIds.size() > 0) {
                    it = SimpleClickListener.this.childClickViewIds.iterator();

                    while (it.hasNext()) {
                        childView = pressedView.findViewById(((Integer) it.next()).intValue());
                        if (SimpleClickListener.this.inRangeOfView(childView, e) && childView.isEnabled()) {
                            SimpleClickListener.this.setPressViewHotSpot(e, childView);
                            childView.setPressed(true);
                            SimpleClickListener.this.onItemChildClick(SimpleClickListener.this.baseQuickAdapter, childView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                            this.resetPressedView(childView);
                            return true;
                        }

                        childView.setPressed(false);
                    }

                    SimpleClickListener.this.setPressViewHotSpot(e, pressedView);
                    SimpleClickListener.this.mPressedView.setPressed(true);
                    it = SimpleClickListener.this.childClickViewIds.iterator();

                    while (it.hasNext()) {
                        childView = pressedView.findViewById(((Integer) it.next()).intValue());
                        childView.setPressed(false);
                    }

                    SimpleClickListener.this.onItemClick(SimpleClickListener.this.baseQuickAdapter, pressedView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                } else {
                    SimpleClickListener.this.setPressViewHotSpot(e, pressedView);
                    SimpleClickListener.this.mPressedView.setPressed(true);
                    it = SimpleClickListener.this.childClickViewIds.iterator();

                    while (it.hasNext()) {
                        childView = pressedView.findViewById(((Integer) it.next()).intValue());
                        childView.setPressed(false);
                    }

                    SimpleClickListener.this.onItemClick(SimpleClickListener.this.baseQuickAdapter, pressedView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                }

                this.resetPressedView(pressedView);
            }

            return true;
        }

        private void resetPressedView(final View pressedView) {
            if (pressedView != null) {
                pressedView.postDelayed(new Runnable() {
                    public void run() {
                        if (pressedView != null) {
                            pressedView.setPressed(false);
                        }

                    }
                }, 100L);
            }

            SimpleClickListener.this.mIsPrepressed = false;
            SimpleClickListener.this.mPressedView = null;
        }

        public void onLongPress(MotionEvent e) {
            boolean isChildLongClick = false;
            if (this.recyclerView.getScrollState() == 0) {
                if (SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                    SimpleClickListener.this.mPressedView.performHapticFeedback(0);
                    BaseViewHolder vh = (BaseViewHolder) this.recyclerView.getChildViewHolder(SimpleClickListener.this.mPressedView);
                    if (!SimpleClickListener.this.isHeaderOrFooterPosition(vh.getLayoutPosition())) {
                        SimpleClickListener.this.longClickViewIds = vh.getItemChildLongClickViewIds();
                        Iterator it;
                        View childView;
                        if (SimpleClickListener.this.longClickViewIds != null && SimpleClickListener.this.longClickViewIds.size() > 0) {
                            it = SimpleClickListener.this.longClickViewIds.iterator();

                            while (it.hasNext()) {
                                childView = SimpleClickListener.this.mPressedView.findViewById(((Integer) it.next()).intValue());
                                if (SimpleClickListener.this.inRangeOfView(childView, e) && childView.isEnabled()) {
                                    SimpleClickListener.this.setPressViewHotSpot(e, childView);
                                    SimpleClickListener.this.onItemChildLongClick(SimpleClickListener.this.baseQuickAdapter, childView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                                    childView.setPressed(true);
                                    SimpleClickListener.this.mIsShowPress = true;
                                    isChildLongClick = true;
                                    break;
                                }
                            }
                        }

                        if (!isChildLongClick) {
                            SimpleClickListener.this.onItemLongClick(SimpleClickListener.this.baseQuickAdapter, SimpleClickListener.this.mPressedView, vh.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                            SimpleClickListener.this.setPressViewHotSpot(e, SimpleClickListener.this.mPressedView);
                            SimpleClickListener.this.mPressedView.setPressed(true);
                            it = SimpleClickListener.this.longClickViewIds.iterator();

                            while (it.hasNext()) {
                                childView = SimpleClickListener.this.mPressedView.findViewById(((Integer) it.next()).intValue());
                                childView.setPressed(false);
                            }

                            SimpleClickListener.this.mIsShowPress = true;
                        }
                    }
                }

            }
        }
    }
}
