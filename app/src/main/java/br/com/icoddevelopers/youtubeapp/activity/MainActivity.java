package br.com.icoddevelopers.youtubeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import br.com.icoddevelopers.youtubeapp.R;
import br.com.icoddevelopers.youtubeapp.adapter.AdapterVideo;
import br.com.icoddevelopers.youtubeapp.api.YoutubeService;
import br.com.icoddevelopers.youtubeapp.helper.RetrofitConfig;
import br.com.icoddevelopers.youtubeapp.helper.YoutubeConfig;
import br.com.icoddevelopers.youtubeapp.listener.RecyclerItemClickListener;
import br.com.icoddevelopers.youtubeapp.model.Item;
import br.com.icoddevelopers.youtubeapp.model.Resultado;
import br.com.icoddevelopers.youtubeapp.model.Video;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity{

    private static final String GOOGLE_API_KEY = "AIzaSyD13TCLU53Khh4q44sl_8lqICwFRFVNgU4";

    private MaterialSearchView searchView;
    private RecyclerView recyclerVideos;
    private List<Item> videos = new ArrayList<>();
    private Resultado resultado;
    private AdapterVideo adapterVideo;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Iniciar Componentes
        recyclerVideos = findViewById(R.id.recyclerVideos);
        searchView = findViewById(R.id.searchView);

        // Configuracoes Iniciais
        retrofit = RetrofitConfig.getRetrofit();

        Toolbar toolbar = findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        recuperarVideos("");

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                recuperarVideos(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                recuperarVideos("");
            }
        });


    }

    private void recuperarVideos(String p){

        String q = p.replaceAll(" ", "+");

        YoutubeService youtubeService = retrofit.create(YoutubeService.class);

        youtubeService.recuperarVideos(
                "snippet",
                "date",
                "20",
                YoutubeConfig.CHAVE_YOUTUBE_API,
                YoutubeConfig.CANAL_ID,
                q
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                if(response.isSuccessful()){

                    resultado = response.body();
                    videos = resultado.items;

                    configurarRecyclerView();

                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });

    }

    public void configurarRecyclerView(){
        adapterVideo = new AdapterVideo(videos, this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adapterVideo);

        // Evento Clique
        recyclerVideos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerVideos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Item video = videos.get(position);
                                String idVideo = video.id.videoId;

                                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                                intent.putExtra("idVideo", idVideo);
                                startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);
        return true;
    }
}
