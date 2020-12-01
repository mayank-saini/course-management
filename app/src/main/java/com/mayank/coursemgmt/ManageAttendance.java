package com.mayank.coursemgmt;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManageAttendance extends AppCompatActivity {
    String studentID, studentName;
    TextView tvstudentName, tvstudentID, tvsempercentage;
    Spinner ssemester;
    DatabaseReference studentDB;
    DatabaseReference attendanceDB;
    BasicStudentInfo bsinfo = new BasicStudentInfo();
    List<Attendance> attendanceList;
    AttendanceAdapter attendanceAdapter;
    RecyclerView rvsubjects;
    int semattendance = 0, semattended = 0;
    float sempercentage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_attendance);
        attendanceList = new ArrayList<>();

        tvstudentName = findViewById(R.id.textView28);
        tvstudentID = findViewById(R.id.textView29);
        ssemester = findViewById(R.id.spinner4);
        tvsempercentage = findViewById(R.id.textView13);

        studentID = getIntent().getExtras().getString("studentID");
        studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo").child(studentID);
        //Displaying Name and StudentID of the respective Student
        studentDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentName = dataSnapshot.child("fname").getValue().toString() +
                        " " + dataSnapshot.child("lname").getValue().toString();
                tvstudentName.setText(studentName);
                tvstudentID.setText(studentID);
                bsinfo = new BasicStudentInfo(dataSnapshot.child("fname").getValue().toString(),
                        dataSnapshot.child("lname").getValue().toString(),
                        Integer.parseInt(dataSnapshot.child("age").getValue().toString()),
                        dataSnapshot.child("branch").getValue().toString(),
                        dataSnapshot.child("batch").getValue().toString(),
                        studentID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //Changing subjects according to the selected Semester
        ssemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                attendanceDB = FirebaseDatabase.getInstance().getReference("Attendance").child(bsinfo.Branch).child(studentID).child(ssemester.getSelectedItem().toString());
                rvsubjects = findViewById(R.id.subjectsRecyclerView);
                rvsubjects.setHasFixedSize(true);
                rvsubjects.setLayoutManager(new LinearLayoutManager(view.getContext()));
                attendanceAdapter = new AttendanceAdapter(view.getContext(), attendanceList, bsinfo.Branch, studentID, ssemester.getSelectedItem().toString());
                rvsubjects.setAdapter(attendanceAdapter);
                semattended = 0;
                semattendance = 0;
                attendanceDB.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                        {
                            if (dataSnapshot.getValue() instanceof Map) {
                                Map<String, Object> map = (Map) dataSnapshot.getValue();
                                if (map.containsKey("subjectName")) {
                                    attendanceList.add(new Attendance((String) map.get("subjectName"),
                                            ((Long) map.get("studentAttendance")).intValue(),
                                            ((Long) map.get("totalAttendanceTaken")).intValue()));
                                    attendanceAdapter.notifyDataSetChanged();
                                    semattended = semattended + ((Long) map.get("studentAttendance")).intValue();
                                    semattendance = semattendance + ((Long) map.get("totalAttendanceTaken")).intValue();
                                }
                            }

                            attendanceDB.child("SemAttended").setValue(semattended);
                            attendanceDB.child("SemAttendance").setValue(semattendance);
                            attendanceDB.child("SemPercentage").setValue(sempercentage);
                            if (semattendance == 0) {
                                sempercentage = 0;
                            } else {
                                sempercentage = (float) semattended / semattendance * 100;
                            }
                            if (sempercentage >= 75) {
                                tvsempercentage.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            } else {
                                tvsempercentage.setTextColor(getResources().getColor(R.color.incorrect));
                            }
                            tvsempercentage.setText(String.valueOf(sempercentage + "%"));
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
