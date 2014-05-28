package com.zgb.ticket.other;
import com.zgb.ticket.ui.InquiryFragment;
import com.zgb.ticket.ui.LoginFragment;
import com.zgb.ticket.ui.OrderFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter{

	private int tabsize = 3;
	private Fragment nowFragment;
	private InquiryFragment inquiry;
	private OrderFragment order;
	private LoginFragment login;
	public TabFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		inquiry = new InquiryFragment();
		order = new OrderFragment();
		login = new LoginFragment();
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		
		switch (arg0) {
		case 0:
			nowFragment = inquiry;
			break;
		case 1:
			nowFragment = order;
			break;
		case 2:
			nowFragment = login;
			break;
		default:
			nowFragment = inquiry;
			break;
		}
		nowFragment.setRetainInstance(true);
		return nowFragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tabsize;
	}
	
}
