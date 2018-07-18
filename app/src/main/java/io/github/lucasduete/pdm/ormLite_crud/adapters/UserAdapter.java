package io.github.lucasduete.pdm.ormLite_crud.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.lucasduete.pdm.ormLite_crud.EditActivity;
import io.github.lucasduete.pdm.ormLite_crud.MainActivity;
import io.github.lucasduete.pdm.ormLite_crud.R;
import io.github.lucasduete.pdm.ormLite_crud.entities.User;

public class UserAdapter extends BaseAdapter {

    private final List<User> userList;
    private final MainActivity activity;

    public UserAdapter(List<User> userList, MainActivity activity) {
        if (userList != null) this.userList = userList;
        else this.userList = new ArrayList<>();

        this.activity = activity;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.users_list, parent, false);
        User user = userList.get(position);

        TextView textView;

        textView = view.findViewById(R.id.userList_textViewName);
        textView.setText(user.getNome());

        textView = view.findViewById(R.id.userList_textViewEmail);
        textView.setText(user.getEmail());

        textView = view.findViewById(R.id.userList_textViewId);
        textView.setText(String.valueOf(user.getId()));

        Button buttonEditar = view.findViewById(R.id.buttonEditar);
        buttonEditar.setOnClickListener(event -> {
            Intent intent = new Intent(view.getContext(), EditActivity.class);
            intent.putExtra("idUser", user.getId());
            view.getContext().startActivity(intent);
        });

        Button buttonRemover = view.findViewById(R.id.buttonRemover);
        buttonRemover.setOnClickListener(event -> activity.remover(user.getId()));

        return view;
    }
}
