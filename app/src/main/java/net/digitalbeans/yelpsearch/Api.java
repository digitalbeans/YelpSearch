package net.digitalbeans.yelpsearch;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("users?q=rokano")
    Call<UsersList> getUsers();
}
