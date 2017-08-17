package com.walmart.tools.wmproductsapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.walmart.tools.wmproductsapp.datanet.ProcessProducts;
import com.walmart.tools.wmproductsapp.model.Product;
import com.walmart.tools.wmproductsapp.model.ProductContent;
import com.walmart.tools.wmproductsapp.utils.ProductSink;
import com.walmart.tools.wmproductsapp.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Products. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ProductDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ProductListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RecyclerView mRecyclerView;
    private ArrayList<Product> mProducts = new ArrayList<>();
    private ProductAdapter mProductAdapter;
    boolean loadingDone = false;
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        try{
            mProducts = ProductSink.products;
        } catch(NullPointerException e) {}
        if(mProducts==null) {
            DownloadProductsTask bgProductTask = new DownloadProductsTask();
            bgProductTask.execute();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.product_list);
        assert mRecyclerView != null;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProductAdapter = new ProductAdapter();
        mRecyclerView.setAdapter(mProductAdapter);
        mProductAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!loadingDone)
                    return;
                Log.e("haint", "Load More");
                mProducts.add(null);
                mRecyclerView.post(new Runnable() {
                    public void run() {
                        mProductAdapter.notifyItemInserted(mProducts.size() - 1);
                    }
                });


                //Load more data for reyclerview
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");

                        //Remove loading item
                        mProducts.remove(mProducts.size() - 1);
                        mProductAdapter.notifyItemRemoved(mProducts.size());

                        //Load data
                        /*int index = mProducts.size();
                        int end = index + 20;
                        for (int i = index; i < end; i++) {
                            Product product = new Product();
                            product.setItemId("Name " + i);
                            product.setName("alibaba" + i + "@gmail.com");
                            mProducts.add(product);
                        }
                        mProductAdapter.notifyDataSetChanged();
                        mProductAdapter.setLoaded();
                        */
                        DownloadProductsTask bgProductTask = new DownloadProductsTask();
                        bgProductTask.execute();
                    }
                }, 1000);
            }
        });

        if (findViewById(R.id.product_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    public void notifyProductAdapter() {
        loadingDone=true;
        mProductAdapter.notifyDataSetChanged();
        mProductAdapter.setLoaded();
    }
    static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView itemId;
        public TextView name;
        public ImageView thumbnail;
        public final View mView;
        public Product mItem=null;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            itemId = (TextView) itemView.findViewById(R.id.itemId);

            name = (TextView) itemView.findViewById(R.id.name);

            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;

        private OnLoadMoreListener mOnLoadMoreListener;

        private boolean isLoading;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;

        public ProductAdapter() {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            });
        }

        public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
            this.mOnLoadMoreListener = mOnLoadMoreListener;
        }

        @Override
        public int getItemViewType(int position) {
            return mProducts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(ProductListActivity.this).inflate(R.layout.layout_product_item, parent, false);
                return new ProductViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(ProductListActivity.this).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ProductViewHolder) {
                Product product = mProducts.get(position);
                final ProductViewHolder productViewHolder = (ProductViewHolder) holder;
                productViewHolder.itemId.setText(product.getItemId());
                productViewHolder.name.setText(product.getName());
                Picasso.with(context)
                        .load(product.getThumbnailImage())
                        .into(productViewHolder.thumbnail);
                productViewHolder.mItem = product;
                productViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putString(ProductDetailFragment.ARG_ITEM_ID, productViewHolder.mItem.getItemId());
                            ProductDetailFragment fragment = new ProductDetailFragment();
                            fragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.product_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ProductDetailActivity.class);
                            intent.putExtra(ProductDetailFragment.ARG_ITEM_ID, productViewHolder.mItem.getItemId());

                            context.startActivity(intent);
                        }
                    }
                });
            } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }

        }

        @Override
        public int getItemCount() {
            return mProducts == null ? 0 : mProducts.size();
        }

        public void setLoaded() {
            isLoading = false;
        }
    }

    private class DownloadProductsTask extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... args0) {
            //Long count = (Long)urls.length;
            List<Product> mP = (new ProductContent()).getProducts();
            if (mProducts==null){
                mProducts = new ArrayList<Product>(100);
            }
            if (null!=mP) {
                for (Product p : mP)
                    mProducts.add(p);
            }
            ProductSink.products = mProducts;
            System.out.println("Product list size "+mProducts.size());
            return mProducts.size()+"";
        }

        protected void onProgressUpdate(Integer... progress) {
            System.out.println("Progress "+progress.toString());
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //showDialog("Downloaded " + result + " products");
            //setupRecyclerView((RecyclerView) recyclerView);
            notifyProductAdapter();

        }
    }
}
