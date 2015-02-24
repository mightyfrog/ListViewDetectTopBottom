package org.mightyfrog.android.listviewdetectscrolltotopbottom;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A sample code to detect if the ListView has "fully" scrolled to the top/bottom.
 *
 * @author Shigehiro Soejima
 */
public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mScrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mScrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                final int visibleChildCount = view.getChildCount();
                if (visibleChildCount == 0) {
                    return;
                }

                if (mScrollState != SCROLL_STATE_IDLE) {
                    if (view.getFirstVisiblePosition() == 0) {
                        if (view.getChildAt(0).getTop() == view.getTop()) {
                            android.util.Log.d(TAG, "TOP REACHED!");
                            view.setBackgroundColor(0xff0000ff);
                        } else {
                            android.util.Log.d(TAG, "FIRST ITEM VISIBLE");
                            view.setBackgroundColor(0xff00ff00);
                        }
                    } else if (view.getLastVisiblePosition() == view.getAdapter().getCount() - 1) {
                        if (view.getChildAt(visibleChildCount - 1).getBottom() == view.getBottom()) {
                            android.util.Log.d(TAG, "BOTTOM REACHED!");
                            view.setBackgroundColor(0xff0000ff);
                        } else {
                            android.util.Log.d(TAG, "LAST ITEM VISIBLE");
                            view.setBackgroundColor(0xff00ff00);
                        }
                    } else {
                        view.setBackgroundColor(0xffffffff);
                    }
                }
            }
        });

        // test data, adjust the item count if you're using a tall screen device
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 50;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv =
                        (TextView) getLayoutInflater().inflate(R.layout.list_item, parent, false);
                tv.setText(String.valueOf(getItem(position)));

                return tv;
            }
        });
    }
}
