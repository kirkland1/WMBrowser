package com.walmart.tools.wmproductsapp;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.webkit.WebView;

import com.squareup.picasso.Picasso;
import com.walmart.tools.wmproductsapp.model.Product;
import com.walmart.tools.wmproductsapp.model.ProductContent;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * A fragment representing a single Product detail screen.
 * This fragment is either contained in a {@link ProductListActivity}
 * in two-pane mode (on tablets) or a {@link ProductDetailActivity}
 * on handsets.
 */
public class ProductDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Product mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = ProductContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            try {
                ((TextView) rootView.findViewById(R.id.product_detail)).setText(URLDecoder.decode(mItem.getLongDescription(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {

            }
            Log.d("DetailFragment", "Sales price: "+mItem.getSalePrice());
            ((TextView) rootView.findViewById(R.id.price)).setText("Sale price: "+mItem.getSalePrice());
            ((TextView) rootView.findViewById(R.id.msrp)).setText("Reg price: "+mItem.getMsrp());
            ImageView iv = ((ImageView) rootView.findViewById(R.id.largeImageView));
            Picasso.with(this.getActivity())
                    .load(mItem.getLargeImage())
                    .into(iv);
            try {
                ((WebView) rootView.findViewById(R.id.webview)).loadData("<html><body>"+URLDecoder.decode(mItem.getLongDescription()+"</body></html>", "UTF-8"), "text/html", null);
            } catch (UnsupportedEncodingException e) {

            }

        }

        return rootView;
    }
}
