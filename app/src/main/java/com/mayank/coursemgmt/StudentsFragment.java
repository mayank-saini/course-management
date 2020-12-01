package com.mayank.coursemgmt;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentsFragment extends Fragment {
    Button addstudent;
    Spinner sbranch, sbatch;
    DatabaseReference studentDB = FirebaseDatabase.getInstance().getReference("StudentInfo");
    RecyclerView recyclerView;
    BasicStudentInfoAdapter adapter;
    List<BasicStudentInfo> studentlist;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return rootView = inflater.inflate(R.layout.fragment_students, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addstudent = rootView.findViewById(R.id.button11);
        sbranch = rootView.findViewById(R.id.spinner5);
        sbatch = rootView.findViewById(R.id.spinner6);

        sbranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studentDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        displayStudents(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sbatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studentDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        displayStudents(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Displaying students
        studentDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayStudents(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Adding Students
        addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddStudent.class);
                startActivity(intent);
            }
        });
    }

    public void displayStudents(DataSnapshot dataSnapshot) {
        recyclerView = rootView.findViewById(R.id.rcview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        studentlist = new ArrayList<>();
        sbranch = rootView.findViewById(R.id.spinner5);
        sbatch = rootView.findViewById(R.id.spinner6);
        for (DataSnapshot childNode : dataSnapshot.getChildren()) {
            StudentInfo studentInfo = childNode.getValue(StudentInfo.class);
            if (studentInfo.getBranch().equals(sbranch.getSelectedItem().toString()) &&
                    studentInfo.getBatch().equals(sbatch.getSelectedItem().toString())) {
                studentlist.add(new BasicStudentInfo(
                        studentInfo.FName,
                        studentInfo.LName,
                        studentInfo.age,
                        studentInfo.Branch,
                        studentInfo.Batch,
                        studentInfo.StudentID
                ));
            }
        }
        adapter = new BasicStudentInfoAdapter(getContext(), studentlist);
        recyclerView.setAdapter(adapter);
    }
}
