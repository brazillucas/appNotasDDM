package com.example.notas_ddm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class visualizarNotas extends AppCompatActivity {

    private DatabaseReference referenciaDBV = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference minhasNotas = referenciaDBV.child("notas");

    private ListView listaNotas;
    private ArrayList<String> anotacoes;

    Query notasQuery = minhasNotas.orderByKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_notas);

        anotacoes = new ArrayList<>();

        listaNotas = findViewById(R.id.listaNotas);

        notasQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dados: snapshot.getChildren()){

                    Notas n = snapshot.getValue(Notas.class);
                    Notas notes = dados.getValue(Notas.class);
                    anotacoes.add(notes.getTexto());
                }

                //ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_activated_2,android.R.id.text1,anotacoes);
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(), R.layout.row,android.R.id.text1,anotacoes);
                listaNotas.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Erro ao listar notas" + error, Toast.LENGTH_SHORT).show();
            }
        });

    }
}