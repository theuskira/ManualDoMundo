package br.com.icoddevelopers.youtubeapp.api;

import br.com.icoddevelopers.youtubeapp.model.Resultado;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeService {

    /*
        https://www.googleapis.com/youtube/v3/
        search?part=snippet
        &order=date
        &maxResults=20
        &key=AIzaSyD13TCLU53Khh4q44sl_8lqICwFRFVNgU4
        &channelId=UCVHFbqXqoYvEWM1Ddxl0QDg

        https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&maxResults=20&key=AIzaSyBf--eTt0ULaJ3NBvxAGWTYC_gooRBZBkA&channelId=UC4MWxaLxXPclwJg13y4f72g


    */

    @GET("search")
    Call<Resultado> recuperarVideos(
                    @Query("part") String part,
                    @Query("order") String order,
                    @Query("maxResults") String maxResults,
                    @Query("key") String key,
                    @Query("channelId") String channelId,
                    @Query("q") String q
    );

}
