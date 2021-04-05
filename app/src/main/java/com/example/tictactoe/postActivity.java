package com.example.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class postActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST=1;
    private Uri imageUri=null;
    private ImageButton imageButton;
    private EditText title,description;
    private Button postButton;
    private StorageReference myStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        myStorage=FirebaseStorage.getInstance().getReference();//reference to root storage

        mProgress=new ProgressDialog(this);
        imageButton=findViewById(R.id.imageButton);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        postButton=findViewById(R.id.postButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,GALLERY_REQUEST);
            }
        });// image picker
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }

        });
    }
    private void post(){
        mProgress.setMessage("Posting Please Dont Press Back.....");
        mProgress.show();
        String title_value=title.getText().toString().trim();
        String description_value=description.getText().toString().trim();
        if (!TextUtils.isEmpty(title_value)&&!TextUtils.isEmpty(description_value)&&imageUri !=null)//we will only allow if all three parameters are filled
        {
            StorageReference filePath=myStorage.child("blog_images").child(imageUri.getLastPathSegment());//this generates a string which may not be unique
            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                            String LoginUserID=signInAccount.getEmail();//this we should show some were so that user can know with which id he is logged in
                            String UserName=modifiedUserName(LoginUserID);//we remove @gmail.com as firebase wont accept it  as string
                            //String downloadString=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            Uri downloadUri=uri;
                            //Uri downloadUri=Uri.parse(downloadString);
                            String downloadString=uri.toString();
                            mProgress.dismiss();// uploaded successfully
                            mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
                            DatabaseReference mref=mDatabase.push();
                            mref.child("title").setValue(title_value);
                            mref.child("description").setValue(description_value);
                            mref.child("image").setValue(downloadString);
                            mref.child("UserName").setValue(UserName);
                            Intent inten=new Intent(postActivity.this,home.class);
                            startActivity(inten);
                        }
                    });
                }
            });
        }
    }

    private String modifiedUserName(String loginUserID) {
        //userName=kainathussain63@gmail.com,we want to remove from @ part as firebase result in error,length=24,@-com 10,index
        int lastIndex=loginUserID.length();
        String modifiedUserName=loginUserID.substring(0,lastIndex-10);
        return modifiedUserName;//returns kainathussain63
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_REQUEST&&resultCode==RESULT_OK){
            imageUri=data.getData();//image uri is initialised here
            imageButton.setImageURI(imageUri);
        }
    }

}