package io.github.lucasduete.pdm.ormLite_crud;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import io.github.lucasduete.pdm.ormLite_crud.dao.DatabaseHelper;
import io.github.lucasduete.pdm.ormLite_crud.entities.User;

public class EditActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private int idUser = -1;
    private RuntimeExceptionDao<User, Integer> userDao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        this.userDao = getHelper().getSimpleDataDao();

        TextView nome = findViewById(R.id.editActivity_editTextNome);
        TextView email = findViewById(R.id.editActivity_editTextEmail);
        this.idUser = getIntent().getIntExtra("idUser" , -1);

        settarCampos(nome, email);

        Button buttonAtualizar = findViewById(R.id.buttonAtualizar);
        buttonAtualizar.setOnClickListener(event ->
                atualizar(nome.getText().toString(), email.getText().toString()));

        Button buttonCancelar = findViewById(R.id.buttonCancelar);
        buttonCancelar.setOnClickListener(event -> finish());
    }

    private void atualizar(String nome, String email) {

        if (nome == null || nome.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha o campo do nome", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha o campo do email", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(this.idUser, nome, email);

        this.userDao.update(user);

        finish();
    }

    private void settarCampos(TextView nome, TextView email) {
        User user = this.userDao.queryForId(this.idUser);

        nome.setText(user.getNome());
        email.setText(user.getEmail());
    }
}
