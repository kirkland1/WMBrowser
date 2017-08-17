package com.walmart.tools.wmproductsapp.datanet;

import com.walmart.tools.wmproductsapp.model.Product;
import com.walmart.tools.wmproductsapp.model.ProductContent;
import com.walmart.tools.wmproductsapp.utils.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Selvan Rajan on 8/15/17.
 */

public class ProcessProducts {

    // contacts JSONArray
    public static JSONArray items = null;
    
    public ArrayList<Product> getProducts() {

        ArrayList<Product> itemsArrayList = new ArrayList<Product>();
        String url = ProductContent.url;
        if (ProductContent.nextPageUrl!=null && ProductContent.nextPageUrl.length()!=0)
            url = ProductContent.nextPageUrl;

        // Creating JSON Parser instance
        JsonParser jParser = new JsonParser();

        // getting JSON string from URL
        JSONObject json = jParser.getJSONFromUrl(url);

        try {
            try {
                String nextUrl = json.getString(ProductContent.TAG_NEXT_PAGE_URL);
                ProductContent.nextPageUrl = "http://api.walmartlabs.com"+nextUrl;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Getting Array of Contacts
            items = json.getJSONArray(ProductContent.TAG_ITEMS);
            System.out.println("Total Items "+items.length());
            // looping through All Contacts
            for (int i = 0; i < items.length(); i++) {
                JSONObject c = items.getJSONObject(i);
                Product product = new Product();
                // Storing each json item in variable
                product.setItemId(c.getString(ProductContent.TAG_ITEM_ID));

                try {
                    product.setParentItemId(c.getString(ProductContent.TAG_ITEM_ID));
                } catch (JSONException e) {

                }
                try {
                    product.setName(c.getString(ProductContent.TAG_NAME));
                } catch (JSONException e) {

                }
                try {
                    product.setShortDescription(c.getString(ProductContent.TAG_SHORT_DESCRIPTION));
                } catch (JSONException e) {

                }
                try {
                    product.setLongDescription(c.getString(ProductContent.TAG_LONG_DESCRIPTION));
                } catch (JSONException e) {

                }
                try {
                    product.setThumbnailImage(c.getString(ProductContent.TAG_THUMBNAIL));
                } catch (JSONException e) {

                }
                try {
                    product.setLargeImage(c.getString(ProductContent.TAG_LARGE_IMAGE));
                } catch (JSONException e) {

                }
                try {
                    product.setGender(c.getString(ProductContent.TAG_GENDER));
                } catch (JSONException e) {

                }
                try {
                    product.setSalePrice(c.getString(ProductContent.TAG_SALE_PRICE));
                } catch (JSONException e) {

                }
                try {
                    product.setMsrp(c.getString(ProductContent.TAG_MSRP));
                } catch (JSONException e) {

                }

                itemsArrayList.add(product);
                ProductContent.ITEM_MAP.put (product.getItemId(), product);
                System.out.println("Item ID: "+product.getItemId());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    return itemsArrayList;
    }

}
