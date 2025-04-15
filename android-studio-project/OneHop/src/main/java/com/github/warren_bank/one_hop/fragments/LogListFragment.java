package com.github.warren_bank.one_hop.fragments;

import com.github.warren_bank.one_hop.R;

import java.util.List;

public class LogListFragment extends BaseListFragment {

  public LogListFragment(List<String> data) {
    super(data);
  }

  @Override
  protected String getEmptyText() {
    return getString(R.string.empty_listview_logs);
  }

}
