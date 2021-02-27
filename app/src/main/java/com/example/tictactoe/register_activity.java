package com.example.tictactoe;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.content.Context;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class register_activity extends AppCompatActivity {
    EditText fullname,phoneNo,email,password;
    Button registerButton;
    TextView loginButton;
    FirebaseAuth fAuth;
    ProgressBar progress;
    person person;
   public DatabaseReference ref;
    long id=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        getWindow().setStatusBarColor(this.getResources().getColor(R.color.black));//set status bar color BLACK
        fullname=findViewById(R.id.fullname);
        phoneNo=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        registerButton=findViewById(R.id.register);
        loginButton=findViewById(R.id.loginHere);
        progress=findViewById(R.id.progress);
        fAuth=FirebaseAuth.getInstance();
        person=new person();
        ref= FirebaseDatabase.getInstance().getReference().child("Person");


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=fullname.getText().toString().trim();
                String Email=email.getText().toString().trim();
                String Password=password.getText().toString().trim();
                String Phone=phoneNo.getText().toString().trim();
                person.setFullName(Name);
                person.setEmail(Email);
                person.setPhoneNo(Phone);
                person.setPassword(Password);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        {
                             id=(snapshot.getChildrenCount());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                 ref.child(String.valueOf(id+1)).setValue(person);

              /*  if (TextUtils.isEmpty(Name))
                {
                    fullname.setError("Enter your Name");
                    Toast.makeText(register_activity.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Email))
                {
                    email.setError("Email is Required");
                    Toast.makeText(register_activity.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Phone.length()!=10)
                {
                    phoneNo.setError("Enter 10 Digit Phone NO");
                    Toast.makeText(register_activity.this, "Please Enter Your 10 Digit Phone NO", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Password)||Password.length()<6)
                {
                    password.setError("Enter atleast 6 digit character");
                    Toast.makeText(register_activity.this, "Enter atleast 6 digit Password", Toast.LENGTH_SHORT).show();
                    return;
                }*/
              /*   progress.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }

                    }
                });

               */




            }
        });

    }
}