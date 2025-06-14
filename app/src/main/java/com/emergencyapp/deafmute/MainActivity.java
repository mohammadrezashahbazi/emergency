package com.emergencyapp.deafmute;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.util.List;

public class MainActivity extends AppCompatActivity {

private SharedPreferences sharedPreferences;
private CardView emergencyCard, fireCard, policeCard, personalCard;
private Button profileButton, setupButton;
private TextView welcomeText;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);

initializeViews();
setupSharedPreferences();
checkFirstTimeSetup();
requestPermissions();
setupClickListeners();
}

private void initializeViews() {
emergencyCard = findViewById(R.id.emergency_card);
fireCard = findViewById(R.id.fire_card);
policeCard = findViewById(R.id.police_card);
personalCard = findViewById(R.id.personal_card);
profileButton = findViewById(R.id.profile_button);
setupButton = findViewById(R.id.setup_button);
welcomeText = findViewById(R.id.welcome_text);
}

private void setupSharedPreferences() {
sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
}

private void checkFirstTimeSetup() {
boolean isFirstTime = sharedPreferences.getBoolean("first_time", true);
if (isFirstTime) {
Intent setupIntent = new Intent(this, SetupActivity.class);
startActivity(setupIntent);
finish();
} else {
String userName = sharedPreferences.getString("user_name", "کاربر");
welcomeText.setText("سلام " + userName + "، آماده کمک‌رسانی هستیم");
}
}

private void requestPermissions() {
Dexter.withContext(this)
.withPermissions(
Manifest.permission.ACCESS_FINE_LOCATION,
Manifest.permission.ACCESS_COARSE_LOCATION,
Manifest.permission.CALL_PHONE,
Manifest.permission.SEND_SMS,
Manifest.permission.RECORD_AUDIO
)
.withListener(new MultiplePermissionsListener() {
@Override
public void onPermissionsChecked(MultiplePermissionsReport report) {
if (report.areAllPermissionsGranted()) {
// همه مجوزها داده شده
}
}

@Override
public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
token.continuePermissionRequest();
}
})
.check();
}

private void setupClickListeners() {
emergencyCard.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
startEmergencyCall("emergency");
}
});

fireCard.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
startEmergencyCall("fire");
}
});

policeCard.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
startEmergencyCall("police");
}
});

personalCard.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
startEmergencyCall("personal");
}
});

profileButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
startActivity(profileIntent);
}
});

setupButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
startActivity(setupIntent);
}
});
}

private void startEmergencyCall(String emergencyType) {
Intent emergencyIntent = new Intent(this, EmergencyActivity.class);
emergencyIntent.putExtra("emergency_type", emergencyType);
startActivity(emergencyIntent);
}

@Override
protected void onResume() {
super.onResume();
// به‌روزرسانی اطلاعات کاربر
String userName = sharedPreferences.getString("user_name", "کاربر");
welcomeText.setText("سلام " + userName + "، آماده کمک‌رسانی هستیم");
}
}