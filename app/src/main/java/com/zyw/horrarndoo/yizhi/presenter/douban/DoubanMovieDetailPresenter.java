package com.zyw.horrarndoo.yizhi.presenter.douban;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zyw.horrarndoo.yizhi.constant.BundleKeyConstant;
import com.zyw.horrarndoo.yizhi.contract.douban.DoubanMovieDetailContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.MovieDetailBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.PersonBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.SubjectsBean;
import com.zyw.horrarndoo.yizhi.model.douban.DoubanMovieDetailModel;
import com.zyw.horrarndoo.yizhi.ui.activity.detail.DoubanMoiveMoreDetailActivity;

import io.reactivex.functions.Consumer;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public class DoubanMovieDetailPresenter extends DoubanMovieDetailContract
        .DoubanMovieDetailPresenter {

    @NonNull
    public static DoubanMovieDetailPresenter newInstance() {
        return new DoubanMovieDetailPresenter();
    }

    @Override
    public void loadMovieDetail(String id) {
        if (mIView == null || mIModel == null)
            return;

        mRxManager.register(mIModel.getMovieDetail(id).subscribe(new Consumer<MovieDetailBean>() {
            @Override
            public void accept(MovieDetailBean bean) throws Exception {
                if (mIView == null)
                    return;

                mIView.showMovieDetail(bean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView == null)
                    return;
                mIView.showToast("Network error.");
                mIView.showNetworkError();
            }
        }));
    }

    @Override
    public void onItemClick(int position, PersonBean item) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeyConstant.ARG_KEY_DOUBAN_MORE_DETAIL_TITLE, item.getName());
        bundle.putString(BundleKeyConstant.ARG_KEY_DOUBAN_MORE_DETAIL_URL, item.getAlt());
        mIView.startNewActivity(DoubanMoiveMoreDetailActivity.class, bundle);
    }

    @Override
    public void onHeaderClick(SubjectsBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeyConstant.ARG_KEY_DOUBAN_MORE_DETAIL_TITLE, bean.getTitle());
        bundle.putString(BundleKeyConstant.ARG_KEY_DOUBAN_MORE_DETAIL_URL, bean.getAlt());
        mIView.startNewActivity(DoubanMoiveMoreDetailActivity.class, bundle);
    }

    @Override
    public DoubanMovieDetailModel getModel() {
        return DoubanMovieDetailModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}