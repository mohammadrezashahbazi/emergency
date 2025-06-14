package com.emergencyapp.deafmute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SetupActivity extends AppCompatActivity {

private EditText nameEditText, addressEditText, personalPhoneEditText;
private CheckBox deafCheckBox, muteCheckBox;
private Button saveButton, skipButton;
private SharedPreferences sharedPreferences;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_setup);

initializeViews();
setupSharedPreferences();
loadExistingData();
setupClickListeners();
}

private void initializeViews() {
nameEditText = findViewById(R.id.name_edit_text);
addressEditText = findViewById(R.id.address_edit_text);
personalPhoneEditText = findViewById(R.id.personal_phone_edit_text);
deafCheckBox = findViewById(R.id.deaf_checkbox);
muteCheckBox = findViewById(R.id.mute_checkbox);
saveButton = findViewById(R.id.save_button);
skipButton = findViewById(R.id.skip_button);
}

private void setupSharedPreferences() {
sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
}

private void loadExistingData() {
nameEditText.setText(sharedPreferences.getString("user_name", ""));
addressEditText.setText(sharedPreferences.getString("user_address", ""));
personalPhoneEditText.setText(sharedPreferences.getString("personal_phone", ""));
deafCheckBox.setChecked(sharedPreferences.getBoolean("is_deaf", false));
muteCheckBox.setChecked(sharedPreferences.getBoolean("is_mute", false));
}

private void setupClickListeners() {
saveButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
saveUserData();
}
});

skipButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
goToMainActivity();
}
});
}

private void saveUserData() {
String name = nameEditText.getText().toString().trim();
String address = addressEditText.getText().toString().trim();
String personalPhone = personalPhoneEditText.getText().toString().trim();

if (TextUtils.isEmpty(name)) {
Toast.makeText(this, "لطفاً نام خود را وارد کنید", Toast.LENGTH_SHORT).show();
return;
}

if (TextUtils.isEmpty(address)) {
Toast.makeText(this, "لطفاً آدرس خود را وارد کنید", Toast.LENGTH_SHORT).show();
return;
}

if (!deafCheckBox.isChecked() && !muteCheckBox.isChecked()) {
Toast.makeText(this, "لطفاً نوع ناتوانی خود را مشخص کنید", Toast.LENGTH_SHORT).show();
return;
}

// ذخیره اطلاعات
SharedPreferences.Editor editor = sharedPreferences.edit();
editor.putString("user_name", name);
editor.putString("user_address", address);
editor.putString("personal_phone", personalPhone);
editor.putBoolean("is_deaf", deafCheckBox.isChecked());
editor.putBoolean("is_mute", muteCheckBox.isChecked());
editor.putBoolean("first_time", false);
editor.apply();

Toast.makeText(this, "اطلاعات شما ذخیره شد", Toast.LENGTH_SHORT).show();
goToMainActivity();
}

private void goToMainActivity() {
Intent mainIntent = new Intent(this, MainActivity.class);
startActivity(mainIntent);
finish();
}
}