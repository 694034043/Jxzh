package com.bocop.zyt.bocop.zyt.gui.fragment.yan;

import com.bocop.zyt.R;
import com.bocop.zyt.bocop.zyt.ILOG;
import com.bocop.zyt.bocop.zyt.broadcast.PlayerMusicBroadCastReciver;
import com.bocop.zyt.fmodule.utils.DisplayUtil;
import com.bocop.zyt.fmodule.utils.ScreenUtil;
import com.bocop.zyt.jx.base.BaseFragment;
import com.bocop.zyt.jx.baseUtil.view.annotation.ViewInject;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class YanMainFragment extends BaseFragment implements OnClickListener{
	
	@ViewInject(R.id.tv_actionbar_title)
	private TextView tv_actionbar_title;
	@ViewInject(R.id.iv_cicle)
	private ImageView iv_cicle;
	protected float iv_cicle_X;
	protected float iv_cicle_Y;
	private int ciccle_pading = 6;
	int banner_position = 0;
	View v;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.activity_yan_main, null);
		return v;
	}
	
	@Override
	protected void initView() {
		IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(PlayerMusicBroadCastReciver.PLAYER_MUSIC_ACTION);
		int[] sc = ScreenUtil.get_screen_size(getActivity());
		int w1 = sc[0];
		int h1 = w1;
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w1, h1);
		p.addRule(RelativeLayout.CENTER_IN_PARENT);
		iv_cicle.setLayoutParams(p);
		int padding = DisplayUtil.dip2px(getActivity(), ciccle_pading);
		iv_cicle.setPadding(padding, padding, padding, padding);
		//FImageloader.load_by_resid(getActivity(), R.drawable.cicle_pic_yzt_yingtan, iv_cicle);
		//iv_cicle.setBac
		iv_cicle.setOnClickListener(this);
		iv_cicle.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				iv_cicle_X = event.getX();
				iv_cicle_Y = event.getY();
				return false;
			}
		});
        tv_actionbar_title.setText(getResources().getString(R.string.yyt_main_title));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_cicle: {
			int[] sc = ScreenUtil.get_screen_size(getActivity());
			int r = (int) (1.0 * sc[0] / 2 - DisplayUtil.dip2px(getActivity(), ciccle_pading) - 130);
			ILOG.log_4_7("x " + iv_cicle.getX() + "  y " + iv_cicle.getY() + " w " + iv_cicle.getWidth());
			ILOG.log_4_7("中心坐标 " + iv_cicle.getWidth() / 2 + "  " + (iv_cicle.getWidth() / 2 + iv_cicle.getY()));
			int c_x = (int) (1.0 * iv_cicle.getWidth() / 2);
			int c_y = c_x;
			ILOG.log_4_7("点击坐标 " + iv_cicle_X + "  " + iv_cicle_Y);
			int rl_x = (int) (iv_cicle_X - c_x);
			int rl_y = (int) (-iv_cicle_Y + c_y);
			ILOG.log_4_7("相对坐标 " + rl_x + "  " + rl_y);
			if (Math.abs(rl_x) * Math.abs(rl_x) + Math.abs(rl_y) * Math.abs(rl_y) > r * r) {
				ILOG.log_4_7(" 区域  外");
				return;
			}
			int area = 0;
			if (rl_y <= 0) {
				if (rl_x >= 0) {
					area = 2;
				} else {
					area = 3;
				}
			} else {
				double angle = 1.0 * Math.abs(rl_y) / Math.abs(rl_x);
				ILOG.log_4_7("angle " + angle);
				if (angle >= 0.57) {
					area = 1;
				} else {
					if (rl_x >= 0) {
						area = 2;
					} else {
						area = 3;
					}
				}
			}
			ILOG.log_4_7("点击 区域 " + area);
			switch (area) {
			case 1:
				YanShopActivity.startAct(getActivity());
				break;
			case 2:
				YanEasyLifeActivity.startAct(getActivity());
				break;
			case 3:
				YanFinanceActivity.startAct(getActivity());
				break;
			}
			break;
		}
		}
	}

}
