package com.example.lakshmi.additempage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Firebase
//    private FireBase fireBase;

    //Photo upload
    ImageView ivCamera, ivGallery, ivImage;
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    final int CAMERA_REQUEST = 1924;
    final int GALLERY_REQUEST = 22139;
    private final String TAG = this.getClass().getName();

    //addItem page Fields
    private EditText Quantity;
    private EditText Comments;
    private EditText Price;
    private Spinner mySpinner1;
    private Spinner mySpinner2;
    private Spinner mySpinner3;
    private Spinner mySpinner4;
    private Spinner mySpinner5;


    private Button Reset;
    private Button Submit;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;
    private Spinner spinner5;

//Field level Validation
    private void addItem(){
        String itemName = mySpinner1.getSelectedItem().toString();
        String category = mySpinner2.getSelectedItem().toString();
        String subCategory = mySpinner3.getSelectedItem().toString();
        String quantity = Quantity.getText().toString().trim();
        String organic = mySpinner4.getSelectedItem().toString();
        String price = Price.getText().toString().trim();
        String comments = Comments.getText().toString().trim();
        String unit = mySpinner5.getSelectedItem().toString();

        if(itemName.equals("Choose Item Name")){
            //Item Name is not selected
            Toast.makeText(this,"Please Select Item Name",Toast.LENGTH_SHORT).show();
            //Stopping the function
            return;
        }
        if (category.equals("Choose Category")) {
            //Category is not selected
            Toast.makeText(this,"Please Select Category",Toast.LENGTH_SHORT).show();
            //Stopping the function
            return;
        }
        if (subCategory.equals("Choose Sub-Category")) {
            //Sub-Category is not selected
            Toast.makeText(this,"Please Select Sub-Category",Toast.LENGTH_SHORT).show();
            //Stopping the function
            return;
        }
        if (organic.equals("Is Organic")) {
            //Organic is not selected
            Toast.makeText(this,"Please Select Yes/No",Toast.LENGTH_SHORT).show();
            //Stopping the function
            return;
        }
        if (unit.equals("Unit")) {
            //Unit is not selected
            Toast.makeText(this,"Please Select Unit",Toast.LENGTH_SHORT).show();
            //Stopping the function
            return;
        }
        if(TextUtils.isEmpty(quantity)){
            //Quantity is Empty
            Toast.makeText(this,"Please Enter Quantity",Toast.LENGTH_SHORT).show();
            //Stopping the function
            return;
        }
        if(TextUtils.isEmpty(price)){
            //Price is Empty
            Toast.makeText(this,"Please Enter Price",Toast.LENGTH_SHORT).show();
            //Stopping the function
            return;
        }
//Comments field is optional
//        if(TextUtils.isEmpty(comments)){
//            //Comments is Empty
//            Toast.makeText(this,"Please Enter Quantity",Toast.LENGTH_SHORT).show();
//            //Stopping the function
//            return;
//        }

//Next page
        else{
            Intent intent = new Intent(MainActivity.this,ViewActivity.class);
            startActivity(intent);
        }
    }


//Gallery Image,Camera Image Implementation
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(resultCode == RESULT_OK){
           if(requestCode == CAMERA_REQUEST){
              String photoPath =  cameraPhoto.getPhotoPath();
               try {
                   Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(130,40).getBitmap();
                   ivImage.setImageBitmap(bitmap);
               } catch (FileNotFoundException e) {
                   Toast.makeText(getApplicationContext(),"Something Wrong While loading Photos",Toast.LENGTH_SHORT).show();
               }

           }
           else if(requestCode == GALLERY_REQUEST){
               Uri uri = data.getData();
               galleryPhoto.setPhotoUri(uri);
               String photoPath = galleryPhoto.getPath();
               try {
                   Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(100,100).getBitmap();
                   ivImage.setImageBitmap(bitmap);
               } catch (FileNotFoundException e) {
                   Toast.makeText(getApplicationContext(),"Something Wrong While selecting Photos",Toast.LENGTH_SHORT).show();
               }
           }
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySpinner1 = (Spinner) findViewById(R.id.nSpinner1);
        mySpinner2 = (Spinner) findViewById(R.id.nSpinner2);
        mySpinner3 = (Spinner) findViewById(R.id.nSpinner3);
        mySpinner4 = (Spinner) findViewById(R.id.nSpinner4);
        mySpinner5 = (Spinner) findViewById(R.id.nSpinner5);
        Quantity = (EditText) findViewById(R.id.etQuan1);
        Price = (EditText)findViewById(R.id.etPrice);
        Comments = (EditText)findViewById(R.id.etComments1);
        Submit = (Button) findViewById(R.id.btnSubmit1);
        Reset = (Button) findViewById(R.id.btnReset1);

        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        ivImage = (ImageView) findViewById(R.id.ivImage);
        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivGallery = (ImageView) findViewById(R.id.ivGallery);

//Camera Image onCLick
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivityForResult(cameraPhoto.takePhotoIntent(),CAMERA_REQUEST);
                    cameraPhoto.addToGallery();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Something Wrong While Taking Photos",Toast.LENGTH_SHORT).show();
                }
            }
        });

//Gallery Image onClick
        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryPhoto.openGalleryIntent(),GALLERY_REQUEST);
            }
        });

//Submit Button Onclick
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

//Reset Button onClick
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quantity.getText().clear();
                Price.getText().clear();
                Comments.getText().clear();
                mySpinner1.setSelection(0);
                mySpinner2.setSelection(0);
                mySpinner3.setSelection(0);
                mySpinner4.setSelection(0);
                mySpinner5.setSelection(0);


            }
        });

//Item Name Spinner onSelect
        spinner1 = findViewById(R.id.nSpinner1);
        List<String> ItemsName = new ArrayList<>();
        ItemsName.add(0,"Choose Item Name");
        ItemsName.add("Rice");
        ItemsName.add("Millet");
        ItemsName.add("Sugar");

        ArrayAdapter<String> myApdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,ItemsName);
        myApdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(myApdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Choose Item Name")){
                    //do Nothing
                }
                else{
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected:" +item,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//Category spinner onSelect
        spinner2 = findViewById(R.id.nSpinner2);
        List<String> categories = new ArrayList<>();
        categories.add(0,"Choose Category");
        categories.add("Rice");
        categories.add("Millet");
        categories.add("Sugar");

        ArrayAdapter<String> myApdapter2 = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,categories);
        myApdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(myApdapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Choose Category")){
                    //do Nothing
                }
                else{
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected:" +item,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//sub-category spinner onSelect
        spinner3 = findViewById(R.id.nSpinner3);
        List<String> subCategories = new ArrayList<>();
        subCategories.add(0,"Choose Sub-Category");
        subCategories.add("Rice");
        subCategories.add("Millet");
        subCategories.add("Sugar");

        ArrayAdapter<String> myApdapter3 = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,subCategories);
        myApdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(myApdapter3);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Choose Sub-Category")){
                    //do Nothing
                }
                else{
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected:" +item,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//Organic Spinner onSelect
        spinner4 = findViewById(R.id.nSpinner4);
        List<String> Organic = new ArrayList<>();
        Organic.add(0,"Is Organic");
        Organic.add("Yes");
        Organic.add("No");

        ArrayAdapter<String> myApdapter4 = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,Organic);
        myApdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(myApdapter4);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Is Organic")){
                    //do Nothing
                }
                else{
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected:" +item,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//Unit spinner onSelect
        spinner5 = findViewById(R.id.nSpinner5);
        List<String> priceUnit = new ArrayList<>();
        priceUnit.add(0,"Unit");
        priceUnit.add("Kg");
        priceUnit.add("Lt");
        priceUnit.add("gms");

        ArrayAdapter<String> myApdapter5 = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,priceUnit);
        myApdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(myApdapter5);

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Unit")){
                    //do Nothing
                }
                else{
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected:" +item,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}

