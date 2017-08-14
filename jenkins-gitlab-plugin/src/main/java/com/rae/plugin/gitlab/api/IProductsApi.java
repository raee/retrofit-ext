/*
 * Copyright (c) 2017.
 */

package com.rae.plugin.gitlab.api;

import com.rae.plugin.gitlab.model.Commit;
import com.rae.plugin.gitlab.model.Product;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IProductsApi {

    @GET("projects")
    Observable<List<Product>> products();

    @GET("projects/{id}/repository/commits")
    Observable<List<Commit>> commits(@Path("id") String projectId, @Query("ref_name") String branch);
}
