package io.github.lucasduete.pdm.ormLite_crud;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import io.github.lucasduete.pdm.ormLite_crud.adapters.UserAdapter;
import io.github.lucasduete.pdm.ormLite_crud.dao.DatabaseHelper;
import io.github.lucasduete.pdm.ormLite_crud.entities.User;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private final String LOG_TAG = "ORM-Lite-Crud";
    private RuntimeExceptionDao<User, Integer> userDao = null;

    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.userDao = getHelper().getSimpleDataDao();


        TextView textViewNome = findViewById(R.id.editTextNome);
        TextView textViewEmail = findViewById(R.id.editTextEmail);

        Button buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonSalvar.setOnClickListener(event ->
                salvar(textViewNome.getText().toString(), textViewEmail.getText().toString()));

        Button buttonRemoverTudo = findViewById(R.id.buttonRemoverTudo);
        buttonRemoverTudo.setOnClickListener(event -> removerTudo());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        exibir();
    }

    public void salvar(String nome, String email) {

        if (nome == null || nome.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha o campo do nome", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha o campo do email", Toast.LENGTH_SHORT).show();
            return;
        }

        userDao.create(new User(nome, email));
        exibir();
    }

    public void exibir() {
        List<User> userList = this.userDao.queryForAll();

        ListView listView = findViewById(R.id.listViewLista);
        this.userAdapter = new UserAdapter(userList, this);

        listView.setAdapter(this.userAdapter);
    }

    public void remover(int idUser) {
        this.userDao.deleteById(idUser);
        exibir();
    }

    public void removerTudo() {
        List<User> userList = userDao.queryForAll();
        userDao.delete(userList);
        exibir();
    }
}
