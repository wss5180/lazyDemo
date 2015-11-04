package com.itheima13.ukudemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class UkuView extends RelativeLayout {
	private RelativeLayout rl_layer1;
	private RelativeLayout rl_layer2;
	private RelativeLayout rl_layer3;
	private ImageView iv_home;
	private ImageView iv_menu;

	private boolean layer3IsOpen = true;
	private boolean layer2IsOpen = true;
	private boolean layer1IsOpen = true;
	private RotateAnimation raClose;
	private RotateAnimation raOpen;

	private int animationCount = 0;
	private View rootView;

	public UkuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();

		// initAnimation();

		initEvent();

	}

	public UkuView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	private void initAnimation() {
		// TODO Auto-generated method stub
		raOpen = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 1);
		raOpen.setDuration(500);
		raOpen.setFillAfter(true);// 停留在动画结束的位置

		raClose = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 1);
		raClose.setDuration(500);
		raClose.setFillAfter(true);// 停留在动画结束的位置
	}

	private void initEvent() {

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.iv_menu:
					// menu的事件
					menuClick();
					break;
				case R.id.iv_home:
					homeClick();
				default:
					break;
				}

			}
		};
		// 给nume添加事件
		iv_menu.setOnClickListener(listener);
		iv_home.setOnClickListener(listener);

	}

	private void setEnable(RelativeLayout rl_layer, boolean isEnable) {
		rl_layer.setEnabled(isEnable);

		for (int i = 0; i < rl_layer.getChildCount(); i++) {
			rl_layer.getChildAt(i).setEnabled(isEnable);
		}
	}

	private void animationEvent() {
		AnimationListener aListener = new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				animationCount++;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				animationCount--;
			}
		};
		raOpen.setAnimationListener(aListener);
		raClose.setAnimationListener(aListener);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			menuKeyClick();
		}
		return true;
	}

	// menu键点击
	private void menuKeyClick() {
		// 有打开的都关闭
		if (layer1IsOpen && layer2IsOpen && layer3IsOpen) {
			closeLayer(rl_layer1, 200);
			closeLayer(rl_layer2, 100);
			closeLayer(rl_layer3, 0);

			// 记录状态
			layer1IsOpen = false;
			layer2IsOpen = false;
			layer3IsOpen = false;

		} else if (layer1IsOpen && layer2IsOpen) {
			closeLayer(rl_layer1, 100);
			closeLayer(rl_layer2, 0);

			// 记录状态
			layer1IsOpen = false;
			layer2IsOpen = false;

		} else if (layer1IsOpen) {
			closeLayer(rl_layer1, 0);
			// 记录状态
			layer1IsOpen = false;
		} else {

			// 都关闭的 都打开

			openLayer(rl_layer1, 0);
			openLayer(rl_layer2, 100);
			openLayer(rl_layer3, 200);
			// 记录状态
			layer1IsOpen = true;
			layer2IsOpen = true;
			layer3IsOpen = true;

		}

	}

	protected void homeClick() {
		// 两个都显示 -》两个都关闭
		if (layer2IsOpen && layer3IsOpen) {
			closeLayer(rl_layer2, 100);
			closeLayer(rl_layer3, 0);
			// 记录状态
			// layer2IsOpen = false;
			// layer3IsOpen = false;
		}

		// layer2显示 -》 layer2关闭

		else if (layer2IsOpen) {
			// 关闭layer2
			closeLayer(rl_layer2, 0);
			// layer2IsOpen = false;
		}

		// 都关闭 -》只让layer2显示
		else {
			openLayer(rl_layer2, 0);
			// layer2IsOpen = true;
			
		}

	}

	protected void menuClick() {
		// menu的点击事件
		// layer3的显示和隐藏 动画效果
		// 显示 --》 隐藏 隐藏---》显示
		if (layer3IsOpen) {
			closeLayer(rl_layer3, 0);
			//layer3IsOpen = false;// 记录状态
		} else {
			openLayer(rl_layer3, 0);
			//layer3IsOpen = true;
		}
	}

	// 关闭菜单的动画startOffset 延迟的时间
	private void closeLayer(RelativeLayout rl_layer, long startOffset) {

		// 旋转动画
		// 补间动画
		initAnimation();
		animationEvent();
		if (animationCount != 0) {
			// 有动画
			return;
		}
		// 设置该组件不可用
		setEnable(rl_layer, false);
		if (rl_layer.getId() == R.id.rl_layer1) {
			layer1IsOpen = false;
		} else if (rl_layer.getId() == R.id.rl_layer2) {
			layer2IsOpen = false;
		} else if (rl_layer.getId() == R.id.rl_layer3) {
			layer3IsOpen = false;
		}

		raClose.setStartOffset(startOffset);

		rl_layer.startAnimation(raClose);
	}

	// 打开菜单的动画
	private void openLayer(RelativeLayout rl_layer, long startOffset) {

		// 旋转动画
		// 补间动画

		initAnimation();
		animationEvent();
		if (animationCount != 0) {
			// 有动画
			return;
		}
		
		// 设置该组件可用
		setEnable(rl_layer, true);

		if (rl_layer.getId() == R.id.rl_layer1) {
			layer1IsOpen = true;
		} else if (rl_layer.getId() == R.id.rl_layer2) {
			layer2IsOpen = true;
		} else if (rl_layer.getId() == R.id.rl_layer3) {
			layer3IsOpen = true;
		}

		raOpen.setStartOffset(startOffset);// 设置动画开始的延迟时间

		rl_layer.startAnimation(raOpen);
	}

	private void initView() {
		setFocusableInTouchMode(true);// 设置焦点
		rootView = View.inflate(getContext(), R.layout.uku_layout, this);
		rl_layer1 = (RelativeLayout) rootView.findViewById(R.id.rl_layer1);
		rl_layer2 = (RelativeLayout) rootView.findViewById(R.id.rl_layer2);
		rl_layer3 = (RelativeLayout) rootView.findViewById(R.id.rl_layer3);

		iv_home = (ImageView) rootView.findViewById(R.id.iv_home);
		iv_menu = (ImageView) rootView.findViewById(R.id.iv_menu);
	}

}
