package com.example.controlefinanceiro2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> listaGastos;
    private ArrayAdapter<String> adapter;
    private double totalGastos = 0.0;
    private String categoriaSelecionada = "Outros"; // Categoria padrão

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Conectar os elementos do layout
        EditText editTextValor = findViewById(R.id.editTextValor);
        Button buttonAdicionar = findViewById(R.id.buttonAdicionar);
        ListView listViewGastos = findViewById(R.id.listViewGastos);
        TextView textViewTotal = findViewById(R.id.textViewTotal);
        Spinner spinnerCategoria = findViewById(R.id.spinnerCategoria);

        // Configurar o Spinner com categorias
        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("Alimentação");
        categorias.add("Transporte");
        categorias.add("Lazer");
        categorias.add("Educação");
        categorias.add("Saúde");
        categorias.add("Outros");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categorias);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(spinnerAdapter);

        // Capturar a categoria selecionada
        spinnerCategoria.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                categoriaSelecionada = categorias.get(position); // Atualiza a categoria
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                categoriaSelecionada = "Outros"; // Categoria padrão
            }
        });

        // Inicializar a lista de gastos e o adaptador
        listaGastos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaGastos);
        listViewGastos.setAdapter(adapter);

        // Ação ao clicar no botão "Adicionar"
        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorStr = editTextValor.getText().toString().trim();
                if (!valorStr.isEmpty()) {
                    try {
                        double valor = Double.parseDouble(valorStr);
                        totalGastos += valor;

                        // Adiciona o valor e a categoria à lista
                        listaGastos.add(categoriaSelecionada + ": R$ " + String.format("%.2f", valor));
                        adapter.notifyDataSetChanged();

                        // Atualiza o total exibido
                        textViewTotal.setText("Total: R$ " + String.format("%.2f", totalGastos));

                        // Limpa o campo de entrada
                        editTextValor.setText("");
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Por favor, insira um valor válido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, insira um valor", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
