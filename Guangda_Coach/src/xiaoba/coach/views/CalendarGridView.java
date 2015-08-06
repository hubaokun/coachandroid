package xiaoba.coach.views;

import android.content.Context;
import android.widget.GridView;
import android.widget.LinearLayout;

public class CalendarGridView extends GridView {

	private Context mContext;

	public CalendarGridView(Context context) {
		super(context);
		mContext = context;

		setGirdView();
	}
	
	public CalendarGridView(Context context, int column) {
		super(context);
		mContext = context;

		setGirdView(column);
	}

	private void setGirdView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		setLayoutParams(params);
		setPadding(0, 0, 0, 0);
		setNumColumns(7);// 设置每行列数
		setVerticalSpacing(2);// 垂直间隔
		setHorizontalSpacing(2);// 水平间隔
		setStretchMode(STRETCH_COLUMN_WIDTH);
		setSelector(getResources().getDrawable(android.R.color.transparent));
	}
	
	private void setGirdView(int column) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		setLayoutParams(params);
		setPadding(0, 0, 0, 0);
		setNumColumns(7);// 设置每行列数
		setVerticalSpacing(2);// 垂直间隔
		setHorizontalSpacing(2);// 水平间隔
		setStretchMode(STRETCH_COLUMN_WIDTH);
		setSelector(getResources().getDrawable(android.R.color.transparent));
	}
}
