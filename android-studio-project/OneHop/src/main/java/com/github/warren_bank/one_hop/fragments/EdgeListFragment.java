package com.github.warren_bank.one_hop.fragments;

import com.github.warren_bank.one_hop.R;
import com.github.warren_bank.one_hop.MainActivity;

import java.util.List;

public class EdgeListFragment extends BaseListFragment {

  public EdgeListFragment(List<String> data) {
    super(data);
  }

  @Override
  protected void onItemClick(int position, String value) {
    MainActivity main = (MainActivity) getActivity();

    main.showFragment(MainActivity.Fragments.EDGE_CHAT, value);
  }

  @Override
  protected void onItemLongClick(int position, String value) {
    MainActivity main = (MainActivity) getActivity();

    main.showFragment(MainActivity.Fragments.EDGE_LOG, value);
  }

  @Override
  protected String getEmptyText() {
    return getString(R.string.empty_listview_edges);
  }

}
