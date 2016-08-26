package example.org.keygen;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Signature;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_validate=(Button)findViewById(R.id.btn_validate);
        final EditText editSig=(EditText)findViewById(R.id.editSig);
        final EditText editMachineId=(EditText)findViewById(R.id.editMachineId);
        final EditText editSerial=(EditText)findViewById(R.id.editSerial);
        btn_validate.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                TelephonyManager tm=(TelephonyManager)getSystemService("phone");
                                                String deviceId=tm.getDeviceId();
                                                String lineNumber=tm.getLine1Number();
                                                String softwareVersion=tm.getDeviceSoftwareVersion();
                                                String serialNumber=tm.getSimSerialNumber();
                                                String subscribeId=tm.getSubscriberId();
                                                PackageManager pkm=getPackageManager();
                                                String machineId;
                                                String serialId;
                                                try{
                                                    String sig=pkm.getPackageInfo("com.lohan.crackme1",PackageManager.GET_SIGNATURES).signatures[0].toCharsString();
                                                    //计算机器码
                                                    machineId=deviceId+lineNumber+softwareVersion+serialNumber+subscribeId+sig;
                                                    //机器码
                                                    editMachineId.setText(machineId);
                                                    //签名
                                                    editSig.setText(sig);
                                                    //注册码
                                                    MessageDigest messageDigest=MessageDigest.getInstance("MD5");
                                                    messageDigest.update(machineId.getBytes(),0,machineId.length());
                                                    BigInteger bm=new BigInteger(1,messageDigest.digest());
                                                    serialId=bm.toString(16);
                                                    editSerial.setText(serialId);


                                                }catch (Exception e){
                                                    editMachineId.setText("没有发现安装CrackMe");
                                                }
                                            }
                                        }

        );





    }
}
