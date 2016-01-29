package vickypatel.ca.volleycache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;

/**
 * Created by VickyPatel on 2016-01-19.
 */
public class RequestHandler extends RequestQueue {

    public RequestHandler(Cache cache, Network network, int threadPoolSize, ResponseDelivery delivery) {
        super(cache, network, threadPoolSize, delivery);
    }
}
