package com.example.tarearvcv_journal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText txtJournalID;
    Button btnEjecutar;
    private RequestQueue rq;
    String color="#52734d";
    List<Journal> datos= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtJournalID = findViewById(R.id.txtJournalID);
        btnEjecutar = findViewById(R.id.btVolley);
        rq= Volley.newRequestQueue(this);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
        btnEjecutar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mostrarRevistas();
        }
        });

        mostrarRevistas();

    }
    public void llamarAdapter()
    {
        Adapter adapter = new  Adapter( this, datos);
        System.out.println("Tamaño:" +datos.size());
        RecyclerView recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void mostrarRevistas(){

        String dir="https://revistas.uteq.edu.ec/ws/issues.php?j_id="+txtJournalID.getText().toString();

        JsonArrayRequest requerimiento=new JsonArrayRequest
                (Request.Method.GET,dir,null,
                        new com.android.volley.Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                //Journal journal= new Journal();

                                List<String> journalList =new ArrayList<String>();
                                datos.clear();
                                for(int f=0;f<response.length();f++)
                                {
                                    try {
                                        JSONObject journalObject=new JSONObject(response.get(f).toString());
                                    datos.add(new Journal(  journalObject.get("title").toString(),
                                            journalObject.get("issue_id").toString(),
                                            journalObject.get("doi").toString(),
                                            journalObject.get("date_published").toString(),
                                            journalObject.get("cover").toString()));


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                llamarAdapter();
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
        rq.add(requerimiento);
    }
}