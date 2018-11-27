package net.digitalbeans.yelpsearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Search {

    @Headers({"Authorization: Bearer ZCC0xk83KLrsPYDWkWPN2FSncoxQr1bHvb6Vo-lXe2TjX60-n3V7NWPZwsItysHniolw5Q850NuQ-a5t0GKxRz54g7H4QpJoIXf2Z2Q04RYJ1ZmLDrE40AfDMgblW3Yx",
            "Accept: application/json"})

    @GET("v3/businesses/search")
    Call<Businesses> getBusinesses(@Query("term") String term, @Query("offset") Integer offset, @Query("latitude") Double latitude, @Query("longitude") Double longitude, @Query("sort_by") String sortBy, @Query("categories") String category);
}
