package com.mayank.coursemgmt;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.mayank.coursemgmt.EncryptionDecryptionUtility.decrypt;


public class MainActivity extends AppCompatActivity {
    EditText et1;
    EditText et2;
    Button SignUpButton, LoginButton;
    RadioGroup SSRadioGroup;
    RadioButton SSRadioButton;
    Toolbar toolbar;
    DatabaseReference staffDB = FirebaseDatabase.getInstance().getReference("StaffInfo");
    DatabaseReference studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo");

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Username
        et1 = findViewById(R.id.editText3);
        //Password
        et2 = findViewById(R.id.editText5);

        //Student or Staff RadioGroup
        SSRadioGroup = findViewById(R.id.RadioGroup1);

        //Login Button
        LoginButton = findViewById(R.id.button);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Insufficient Data", Toast.LENGTH_SHORT).show();
                } else {
                    String str = checkSSRadioGroup();
                    if (str.equals("Staff")) {
                        staffDB.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                checkStaffAuthorization(dataSnapshot);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else if (str.equals("Student")) {
                        studentDB.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                checkStudentAuthorization(dataSnapshot);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });

        //SignUp Button
        SignUpButton = (Button) findViewById(R.id.button2);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = checkSSRadioGroup();
                if (str.equals("Staff")) {
                    openSignUpActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "SignUp is only available for STAFF", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Function to Check Student Username & Password
    private void checkStudentAuthorization(DataSnapshot dataSnapshot) {
        String rUname = "";
        String rPass = "";
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            rUname = ds.child("uname").getValue().toString();
            rPass = ds.child("studPass").getValue().toString();
            String decPass = "";

            //DECRYPTING
            try {
                decPass = decrypt(rPass, rUname);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Checking Username & Password
            if (rUname.equals(et1.getText().toString()) && decPass.equals(et2.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                openStudentLoggedInActivity(rUname);
            }
        }
    }

    private void openStudentLoggedInActivity(String studentID) {
        Intent intent = new Intent(this, StudentLoggedIn.class);
        intent.putExtra("StudentID", studentID);
        this.recreate();
        startActivity(intent);
    }

    //Function to Check Staff Username & Password
    public void checkStaffAuthorization(DataSnapshot dataSnapshot) {
        String rUName = "";
        String rPass = "";
        int count = (int) dataSnapshot.getChildrenCount();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            rUName = ds.child("uname").getValue().toString();
            rPass = ds.child("spassword").getValue().toString();
            String decPass = "";

            //DECRYPTING
            try {
                decPass = decrypt(rPass, rUName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Checking Username and Password
            if (rUName.equals(et1.getText().toString()) && decPass.equals(et2.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                openStaffLoggedInActivity(rUName);
                break;
            }
            count--;
        }
        if (count == 0) {
            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    //Checking which checkbox is selected
    public String checkSSRadioGroup() {
        int RadioButtonID = SSRadioGroup.getCheckedRadioButtonId();
        SSRadioButton = findViewById(RadioButtonID);
        return (String) SSRadioButton.getText();
    }

    //Opens the SignUp Activity
    public void openSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        this.recreate();
        startActivity(intent);
    }

    //Opens the StaffLoggedIn Activity
    public void openStaffLoggedInActivity(String username) {
        Intent intent = new Intent(this, StaffLoggedIn.class);
        intent.putExtra("username", username);
        this.recreate();
        startActivity(intent);
    }
}
