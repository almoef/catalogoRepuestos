package com.example.cagalogorepuestos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.cagalogorepuestos.Adapter.Adapter;
import com.example.cagalogorepuestos.Model.Model;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
  RecyclerView mRecycler;
  Adapter mAdapter;
  FirebaseFirestore mFirestore;
  SearchView searchView;
  Query query;
  EditText edt_srch;
  ImageButton srchButton;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //searchView = findViewById(R.id.searchRep);
    edt_srch = findViewById(R.id.edt_srch);
    srchButton = findViewById(R.id.searchButton);


    edt_srch.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
          // Aquí puedes realizar la acción que desees
          textSearch(edt_srch.getText().toString());
          //OCULTA EL TECLADO
          InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
          inputMethodManager.hideSoftInputFromWindow(edt_srch.getWindowToken(), 0);
          return true; // Indicar que el evento ha sido manejado
        }
        return false; // Permitir que otros manejadores de eventos procesen la pulsación de tecla
      }
    });
  }

  public void search_view_button(View view) {

    textSearch(edt_srch.getText().toString());
    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(edt_srch.getWindowToken(), 0);
  }

/*
  private void search_view() {
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String s) {
        textSearch(s);
        return false;
      }

      @Override
      public boolean onQueryTextChange(String s) {
        return false;
      }
    });
  }*/

  public void textSearch(String s){
    mFirestore = FirebaseFirestore.getInstance();
    mRecycler = findViewById(R.id.recyclerView);
    mRecycler.setLayoutManager(new LinearLayoutManager(this));
    //query = mFirestore.collection("catalogo_repuestos");
    query = mFirestore.collection("CatalogRep").whereEqualTo("material", s);
/*
    FirestoreRecyclerOptions<Model> firestoreRecyclerOptions =
            new FirestoreRecyclerOptions.Builder<Model>()
                    .setQuery(query.orderBy("material")
                            .startAt(s).endAt(s + "~"),Model.class).build();*/
    FirestoreRecyclerOptions<Model> firestoreRecyclerOptions =
            new FirestoreRecyclerOptions.Builder<Model>().setQuery(query,Model.class).build();
    mAdapter = new Adapter(firestoreRecyclerOptions);
    mAdapter.startListening();
    mRecycler.setAdapter(mAdapter);

    query.orderBy("material")
            .startAt(s).endAt(s + "~")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
              @Override
              public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                  // Manejar cualquier error aquí
                  return;
                }
                // Verificar si no se encontraron resultados
                if (queryDocumentSnapshots != null && queryDocumentSnapshots.isEmpty()) {
                  showToast("No se encontró el código digitado.");
                }
              }
            });

  }
  private void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
  @Override
  protected void onStart() {
    super.onStart();
    //mAdapter.startListening();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mAdapter.stopListening();
  }
}