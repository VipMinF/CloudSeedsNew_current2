package com.sunstar.cloudseeds.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.classichu.classichu.app.CLog;
import com.classichu.classichu.classic.ClassicFragment;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.search.OnRecentbuttonClickListener;
import com.sunstar.cloudseeds.logic.search.SearchRecentAdapter;
import com.sunstar.cloudseeds.logic.search.SearchRecentHelper;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.ui.YZTZListFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends ClassicFragment implements OnRecentbuttonClickListener{

    private SearchView searchView;
    private String mQueryText;
    private RecyclerView recyclerView;
    private SearchRecentAdapter searchrecentAdapter;
    private Fragment searchResultFragment;
    private int row_size=4;

    public SearchFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int setupLayoutResId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View view) {

        initSearchView();
        initRecentSearchView();
    }

    @Override
    protected void initListener() {

    }


    private void initSearchView() {
        searchView = findById(R.id.id_search_view_searchfragment);
        //设置搜索图标是否显示在搜索框内
        searchView.setIconifiedByDefault(false);//The default value is true   ，设置为false直接展开显示 左侧有放大镜  右侧无叉叉   有输入内容后有叉叉
        //!!! searchView.setIconified(false);//true value will collapse the SearchView to an icon, while a false will expand it. 左侧无放大镜 右侧直接有叉叉
        //  searchView.onActionViewExpanded();//直接展开显示 左侧无放大镜 右侧无叉叉 有输入内容后有叉叉 内部调用了setIconified(false);
        //searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setQueryHint("请输入关键字");//设置查询提示字符串
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               /* mQueryText = query;
                mPresenter.querySSQData();*/
                CLog.d("onQueryTextSubmit:" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {

                RxSearchView.queryTextChanges(searchView)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        //对用户输入的关键字进行过滤
                        /*.filter(new Func1<CharSequence, Boolean>() {
                            @Override
                            public Boolean call(CharSequence charSequence) {
                                return charSequence.toString().trim().length() > 0;
                            }
                        })*/
                        .subscribe(new Consumer<CharSequence>() {
                            @Override
                            public void accept(@NonNull CharSequence charSequence) throws Exception {
                                mQueryText = charSequence.toString();
                                if (mQueryText.trim().length() > 0) {

                                    initSearchResultView("",mQueryText);
                                } else {

                                    initRecentSearchView ();
                                }
                            }

                        });

                return false;
            }
        });
    }

    private void  initRecentSearchView (){

        LinearLayout linearLayout_searchrecent=findById(R.id.id_linearlayout_searchrecent);
        linearLayout_searchrecent.setVisibility(View.VISIBLE);

        if (recyclerView == null){
            recyclerView = findById(R.id.id_recylerview_search);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(row_size, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(searchrecentAdapter = new SearchRecentAdapter());
            searchrecentAdapter.setOnItemClickListener(this);
        }
        searchrecentAdapter.replaceAll(getSearchRecentData());
        removeSearchResultView();
    }

    public ArrayList<String> getSearchRecentData() {

        ArrayList<String> list=(ArrayList<String>)SearchRecentHelper.getRecentSearchList_FromAcache(getContext());
        return list;
    }

    private void initSearchResultView(String primary_id ,String search_keyword){

        LinearLayout linearLayout_searchrecent=findById(R.id.id_linearlayout_searchrecent);
        linearLayout_searchrecent.setVisibility(View.GONE);

        searchResultFragment=YZTZListFragment.newInstance(primary_id,search_keyword,"");
        getChildFragmentManager().beginTransaction()
                .replace(R.id.id_framlayout_searchrecent,searchResultFragment)
                .commit();
    }


    private void removeSearchResultView(){
        if (searchResultFragment !=null){
            getChildFragmentManager().beginTransaction()
                    .remove(searchResultFragment)
                    .commit();
        }
    }

    @Override
    public void onRecentButtonClick(View v){
        TextView textV=(TextView) v;
        searchView.setQuery(textV.getText().toString(),true);
    }


}
