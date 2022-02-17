package com.example.converter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Converter extends AppCompatActivity {
    EditText editText;
    TextView textView;
    Button button;
    Button clear;
    Button copy;
    //CAMERA --- CAMERA --- CAMERA
    Button btnConvert;
    boolean hasCameraFlash = false;
    private static final int CAMERA_REQUEST = 123;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        copy = findViewById(R.id.copy);
        clear = findViewById(R.id.clear);


        //request user to use camera flash//

        btnConvert = findViewById(R.id.btnConvert);
        Switch switchEnableButton = findViewById(R.id.switch_enable);
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {



                ActivityCompat.requestPermissions(Converter.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                if (!editText.getText().toString().isEmpty()) {
                    String convertedText = convert(editText.getText().toString()).replaceAll(" ","");
                    textView.setText(convertedText);
                    System.out.println(convertedText);
                    blinkflash(convertedText);

                }
            }
        });

        switchEnableButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btnConvert.setEnabled(true);
                }else{
                    btnConvert.setEnabled(false);
                }
            }
        });
//             ActivityCompat.requestPermissions(Converter.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
//        camaraHasFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);




//Convert Text Button --- * ---- Convert Text Button --- * ----Convert Text Button --- * ----Convert Text Button --- * ----
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = editText.getText().toString();
                word.toUpperCase();
                for (int i = 0; i < word.length(); i++) {
                    char c = word.charAt(i);
                    if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z' || c >= '0' && c <= '9')) {
                        textView.setText(convert(editText.getText().toString()));
                    } else {
                        textView.setText(convertMorse(editText.getText().toString()));
                    }
                }
            }
        });


        //Clear Text Button
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Text = editText.getText().toString();
                if (Text.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "The text is empty", Toast.LENGTH_SHORT).show();
                } else {
                    editText.setText("");
                    //convert Function
                }
            }


        });

        //Copy Text Button --- * ---- Copy Text Button --- * ----Copy Text Button --- * ----Copy Text Button --- * ----
        copy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String Value = convert(editText.getText().toString());
                if (Value.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter word", Toast.LENGTH_SHORT).show();
                } else {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("data", Value);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }
            }
        });

    } //END OF protected void onCreate(Bundle savedInstanceState)



    //FlashLight --- * ---- FlashLight --- * ----FlashLight --- * ----FlashLight --- * ----FlashLight --- * ----

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void blinkflash(String text) {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        long shortDelay = 1000; //Delay in MiliSeconds == 0.5 Sec
        long longDelay = 3000; //Delay in MiliSeconds == 1   Sec
        //long Delay = 1000;
        String cameraId = null;
       try{  cameraId = cameraManager.getCameraIdList()[0];}
       catch(CameraAccessException e) {

        }
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '.') {
                try {
                    cameraManager.setTorchMode(cameraId, true);
                } catch (CameraAccessException e) {
                }
                try {
                    Thread.sleep(shortDelay);//short
                    cameraManager.setTorchMode(cameraId, false);
                    Thread.sleep(shortDelay);//short
                } catch (InterruptedException e ) {
                    e.printStackTrace();
                }catch (CameraAccessException e) {
                }

            } else if (text.charAt(i) == '-') {
                try {

                    cameraManager.setTorchMode(cameraId, true);
                    System.out.println("Flash long");
                } catch (CameraAccessException e) {
                }
                try {
                    Thread.sleep(longDelay);//long

                    cameraManager.setTorchMode(cameraId, false);
                    Thread.sleep(shortDelay);//short
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch (CameraAccessException e) {
                }
            }
    }
    //in case last character in morse code is "." , we want to stop flash light
        try {
            assert cameraManager != null;

            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
        }
}
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                                        Toast.makeText(Converter.this, "Camera Flashing", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
//Share button - Shared the userInput
    public void buttonShareFile(View view){
        String Text = convert(editText.getText().toString());
        if (Text.isEmpty()) {
            Toast.makeText(getApplicationContext(), "The text is empty", Toast.LENGTH_SHORT).show();
        } else {
            Intent intentShare = new Intent(Intent.ACTION_SEND);
            intentShare.setType("text/plain");
            intentShare.putExtra(Intent.EXTRA_TEXT , Text);

            startActivity(Intent.createChooser(intentShare,"Share the text."));
        }



    }

    //Convert Numbers and Letters into morse code.
    public String convert(String text) {
        String temp = "";
        temp = text.toUpperCase();
        //Numbers from - 0 to 9
        temp = temp.replace("0", " ----- ");
        temp = temp.replace("1", " -.--- ");
        temp = temp.replace("2", " ..--- ");
        temp = temp.replace("3", " ...-- ");
        temp = temp.replace("4", " ....- ");
        temp = temp.replace("5", " ..... ");
        temp = temp.replace("6", " -.... ");
        temp = temp.replace("7", " --... ");
        temp = temp.replace("8", " ---.. ");
        temp = temp.replace("9", " ----. ");
        //English Alphabet -----> ABCDEFGHIJKLMNOPQRSTUVWXZ
        temp = temp.replace("A", " .- ");
        temp = temp.replace("B", " -... ");
        temp = temp.replace("C", " -.-. ");
        temp = temp.replace("D", " -.. ");
        temp = temp.replace("E", " . ");
        temp = temp.replace("F", " ..-. ");
        temp = temp.replace("G", " --. ");
        temp = temp.replace("H", " .... ");
        temp = temp.replace("I", " .. ");
        temp = temp.replace("J", " .--- ");
        temp = temp.replace("K", " -.- ");
        temp = temp.replace("L", " .-.. ");
        temp = temp.replace("M", " -- ");
        temp = temp.replace("N", " -. ");
        temp = temp.replace("O", " --- ");
        temp = temp.replace("P", " .--. ");
        temp = temp.replace("Q", " --.- ");
        temp = temp.replace("R", " .-. ");
        temp = temp.replace("S", " ... ");
        temp = temp.replace("T", " - ");
        temp = temp.replace("U", " ..- ");
        temp = temp.replace("V", " ...- ");
        temp = temp.replace("W", " .-- ");
        temp = temp.replace("X", " -..- ");
        temp = temp.replace("Y", " -.-- ");
        temp = temp.replace("Z", " --.. ");

        String temp_1 = temp.substring(1,temp.length()-1);
        return temp_1;

    }

    //CONVERT MORSE CODE TO ENGLISH

    public String convertMorse(String text) {
        String temp = "";
        StringBuilder sb = new StringBuilder();
        String [] arr = text.split(" ");

        for(int i=0;i<arr.length;i++) {
            temp = arr[i];
            //Numbers from - 0 to 9
            if(temp.equals(".----")){ temp = "0"; }
            else if(temp.equals("..---")){ temp = "1"; }
            else if(temp.equals("...--")){ temp = "2"; }
            else if(temp.equals("....-")){ temp = "3"; }
            else if(temp.equals(".....")){ temp = "4"; }
            else if(temp.equals("-....")){ temp = "5"; }
            else if(temp.equals("--...")){ temp = "6"; }
            else if(temp.equals("---..")){ temp = "7"; }
            else if(temp.equals("----.")){ temp = "8"; }
            else if(temp.equals("-----")){ temp = "9"; }
            //English Alphabet -----> ABCDEFGHIJKLMNOPQRSTUVWXZ

            else if(temp.equals(".-")){ temp = "A";}
            else if(temp.equals("-...")){ temp = "B";}
            else if(temp.equals("-.-.")){ temp = "C";}
            else if(temp.equals("-..")){ temp = "D";}
            else if(temp.equals(".")){ temp = "E";}
            else if(temp.equals("..-.")){ temp = "F";}
            else if(temp.equals("--.")){ temp = "G";}
            else if(temp.equals("....")){ temp = "H";}
            else if (temp.equals("..")){temp =  "I";}
            else if(temp.equals(".---")){ temp = "J";}
            else if(temp.equals(".-..")){ temp = "L";}
            else if(temp.equals("--")){ temp = "M";}
            else if(temp.equals("-.")){ temp = "N";}
            else if(temp.equals("---")){ temp = "O";}
            else if(temp.equals(".--.")){ temp = "P";}
            else if(temp.equals("--.-")){ temp = "Q";}
            else if(temp.equals("...")){ temp = "S";}
            else if(temp.equals(".-.")){ temp = "R";}
            else if(temp.equals("-")){ temp = "T";}
            else if (temp.equals("-..-")){temp =  "X";}
            else if(temp.equals("..-")){ temp = "U";}
            else if(temp.equals("...-")){ temp = "V";}
            else if(temp.equals(".--")){ temp = "W";}
            else if(temp.equals("-..-")){ temp = "X";}
            else if(temp.equals("-.--")){ temp = "Y";}
            else if(temp.equals("--..")){ temp = "Z";}

            sb.append(temp);

        }
        String final_str = sb.toString();
        return final_str;

    }




}//END OF THE PROGRAM.







