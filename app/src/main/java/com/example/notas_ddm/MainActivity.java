package com.example.notas_ddm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference referenciaDb = FirebaseDatabase.getInstance().getReference();

    private FirebaseAuth usuario = FirebaseAuth.getInstance();

    private TextInputEditText caixaTextoInicial;
    private Button btnLimpar;
    private Button btnEnviar;
    private Button btnVisualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaração dos objetos dos templates
        //Caixa de texto
        this.caixaTextoInicial = findViewById(R.id.caixaTextoInicial);
        //Botões
        this.btnEnviar = findViewById(R.id.btnsalvar);
        this.btnLimpar = findViewById(R.id.btnlimpar);
        this.btnVisualizar = findViewById(R.id.btnVisualizar);

        //Configura o botão de limpar o campo de texto
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (view.equals(btnLimpar)){
                caixaTextoInicial.setText("");
            }
            }
        });

        //Configura o botão salvar para guardar os dados no DB
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Valida se a caixa de texto está vazia
                //Caso esteja preenchida, enviar os dados para o database
                if (!Objects.requireNonNull(caixaTextoInicial.getText()).toString().equals("")) {
                    Notas n = new Notas();

                    DatabaseReference nota = referenciaDb.child("notas");

                    n.setTexto(String.valueOf(caixaTextoInicial.getText()));

                    nota.push().setValue(n);

                    caixaTextoInicial.setText("");

                    Toast.makeText(getApplicationContext(), "NOTA SALVA COM SUCESSO!", Toast.LENGTH_SHORT).show();
                } else{
                    //Caso o campo de texto esteja vazio, apresenta um Toast para avisar que a nota está fazia
                    Toast.makeText(getApplicationContext(), "DIGITE UMA NOTA PARA SALVAR!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Ao clicar, leva para a tela de visualização das notas já salvas
        btnVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, visualizarNotas.class);
                startActivity(intent);
            }
        });


    }

}