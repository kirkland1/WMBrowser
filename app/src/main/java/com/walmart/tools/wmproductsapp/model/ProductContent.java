package com.walmart.tools.wmproductsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.walmart.tools.wmproductsapp.datanet.ProcessProducts;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing product array
 * <p>
 *
 */
public class ProductContent {

    // url to make request
    public static String url = "http://api.walmartlabs.com/v1/paginated/items?format=json&apiKey=t3r89ddypvtu3jmebgg6yqvx";
    public static String nextPageUrl = null;

    // JSON Node names
    public static final String TAG_NEXT_PAGE_URL = "nextPage";
    public static final String TAG_ITEM_ID = "itemId";
    public static final String TAG_PITEM_ID = "parentItemId";
    public static final String TAG_ITEMS = "items";
    public static final String TAG_NAME = "name";
    public static final String TAG_SHORT_DESCRIPTION = "shortDescription";
    public static final String TAG_LONG_DESCRIPTION = "longDescription";

    public static final String TAG_SALE_PRICE = "salePrice";
    public static final String TAG_MSRP = "msrp";

    public static final String TAG_GENDER = "gender";
    public static final String TAG_THUMBNAIL = "thumbnailImage";
    public static final String TAG_LARGE_IMAGE = "largeImage";




    /**
     * An array of sample (Product) items.
     */
    public ArrayList<Product> productItems = new ArrayList<Product>();

    /**
     * A map of sample (Product) items, by ID.
     */
    public static Map<String, Product> ITEM_MAP = new HashMap<String, Product>();

    private static final int COUNT = 25;


    public List<Product> getProducts() {

        productItems = (new ProcessProducts()).getProducts();
        return productItems;

    }



}
