package com.zgb.ticket;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter{

	private int tabsize = 3;
	private Fragment nowFragment;
	public TabFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		
		switch (arg0) {
		case 0:
			nowFragment = new InquiryFragment();
			break;
		case 1:
			nowFragment = new OrderFragment();
			break;
		case 2:
			nowFragment = new LoginFragment();
			break;
		default:
			nowFragment = new InquiryFragment();
			break;
		}
		return nowFragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tabsize;
	}
	
}
