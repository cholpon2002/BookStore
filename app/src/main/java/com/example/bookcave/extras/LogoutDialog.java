package com.example.bookcave.extras;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.bookcave.CustomerLogin;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

//Диалоговое окно для выхода из аккаунта

public class LogoutDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Выйти")
                .setMessage("Вы уверены в том что хотите выйти?")
                //сетится кнопка для дальнейших действий
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(getActivity(), CustomerLogin.class);
                        startActivity(intent);
                        requireActivity().finish();

                    }
                })
                //сетится негативная кнопка для отмены действия
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}

