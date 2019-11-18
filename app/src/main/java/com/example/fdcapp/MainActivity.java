package com.example.fdcapp;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static android.graphics.Color.GRAY;

public class MainActivity extends AppCompatActivity {

    ImageButton power, btnremote, btnblue, btnone, btntwo;
    Button btnauto;
    TextView tvRecive, tvRecive2;


    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    BluetoothAdapter mBluetoothAdapter2;
    Set<BluetoothDevice> mPairedDevices2;
    List<String> mListPairedDevices2;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    Handler mBluetoothHandler2;
    ConnectedBluetoothThread2 mThreadConnectedBluetooth2;
    BluetoothDevice mBluetoothDevice2;
    BluetoothSocket mBluetoothSocket2;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;

    final static int BT_MESSAGE_READ2 = 5;
    final static int BT_CONNECTING_STATUS2 = 6;
    //스마트폰 ~ 아두이노
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        power = findViewById(R.id.power);
        btnone = findViewById(R.id.btnone);
        btntwo = findViewById(R.id.btntwo);
        btnauto = findViewById(R.id.btnauto);
        btnremote = findViewById(R.id.btnremote);
        btnblue = findViewById(R.id.btnblue);
        tvRecive = findViewById(R.id.tvRecive);
        tvRecive2 = findViewById(R.id.tvRecive2);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter2 = BluetoothAdapter.getDefaultAdapter();

        final int[] i = {1};
        power.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i[0] % 2 == 1) {
                    power.setImageResource(R.drawable.power);
                    power.setBackgroundResource(R.drawable.backcircle);
                    i[0]++;
                    if (mThreadConnectedBluetooth != null) {
                        mThreadConnectedBluetooth.write("1");
                    }
                } else {
                    power.setImageResource(R.drawable.poweroff);
                    power.setBackgroundResource(R.drawable.backcircle2);
                    i[0]--;
                    if (mThreadConnectedBluetooth != null) {
                        mThreadConnectedBluetooth.write("2");
                    }
                }
            }
        });
        btnblue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i[0] % 2 == 1) {
                    btnblue.setImageResource(R.drawable.bluetooth);
                    btnblue.setBackgroundResource(R.drawable.backcircle);
                    i[0]++;
                    if (mBluetoothAdapter == null) {
                        Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_LONG).show();
                    } else {
                        if (mBluetoothAdapter.isEnabled()) {
                            Toast.makeText(getApplicationContext(), "블루투스가 이미 활성화 되어 있습니다.", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "블루투스가 활성화 되어 있지 않습니다.", Toast.LENGTH_LONG).show();
                            Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
                        }
                    }
                } else {
                    btnblue.setImageResource(R.drawable.bluetoothoff);
                    btnblue.setBackgroundResource(R.drawable.backcircle2);
                    i[0]--;
                    if (mBluetoothAdapter.isEnabled()) {
                        mBluetoothAdapter.disable();
                        Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되었습니다.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "블루투스가 이미 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listPairedDevices();
            }
        });
        btntwo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listPairedDevices2();
            }
        });

        mBluetoothHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == BT_MESSAGE_READ) {
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                    }
                    tvRecive.setText(readMessage);

                }

            }
        };
        mBluetoothHandler2 = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == BT_MESSAGE_READ2) {
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                    }
                    tvRecive2.setText(readMessage);

                }

            }
        };


        btnremote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RemoteActivity.class);
                startActivity(intent);


            }
        });


        btnauto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i[0] % 2 == 1) {
                    String strColor = "#B7F0B1";
                    btnauto.setTextColor(Color.parseColor(strColor));
                    btnauto.setBackgroundResource(R.drawable.backcircle);
                    i[0]++;
                    if (mThreadConnectedBluetooth != null) {
                        mThreadConnectedBluetooth.write("a");
                    }
                } else {
                    String strColor = "#BDBDBD";
                    btnauto.setTextColor(Color.parseColor(strColor));
                    btnauto.setBackgroundResource(R.drawable.backcircle2);
                    i[0]--;
                    if (mThreadConnectedBluetooth != null) {
                        mThreadConnectedBluetooth.write("s");
                    }
                }
            }
        });


        //액션바

        //getSupportActionBar().setTitle("F D C");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        //액션바 배경색
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BT_REQUEST_ENABLE:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "블루투스 활성화", Toast.LENGTH_LONG).show();

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void listPairedDevices() {
        if (mBluetoothAdapter.isEnabled()) {
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if (mPairedDevices.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 선택");

                mListPairedDevices = new ArrayList<String>();
                for (BluetoothDevice device : mPairedDevices) {
                    mListPairedDevices.add(device.getName());
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }
                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);
                mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice(items[item].toString());
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    void listPairedDevices2() {
        if (mBluetoothAdapter2.isEnabled()) {
            mPairedDevices2 = mBluetoothAdapter2.getBondedDevices();

            if (mPairedDevices2.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 선택");

                mListPairedDevices2 = new ArrayList<String>();
                for (BluetoothDevice device : mPairedDevices2) {
                    mListPairedDevices2.add(device.getName());
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }
                final CharSequence[] items = mListPairedDevices2.toArray(new CharSequence[mListPairedDevices2.size()]);
                mListPairedDevices2.toArray(new CharSequence[mListPairedDevices2.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice2(items[item].toString());
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    void connectSelectedDevice(String selectedDeviceName) {
        for (BluetoothDevice tempDevice : mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.getName())) {
                mBluetoothDevice = tempDevice;
                break;
            }
        }
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();

            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        }
    }

    void connectSelectedDevice2(String selectedDeviceName) {
        for (BluetoothDevice tempDevice : mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.getName())) {
                mBluetoothDevice2 = tempDevice;
                break;
            }
        }


        try {
            mBluetoothSocket2 = mBluetoothDevice2.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket2.connect();
            mThreadConnectedBluetooth2 = new ConnectedBluetoothThread2(mBluetoothSocket2);
            mThreadConnectedBluetooth2.start();
            mBluetoothHandler2.obtainMessage(BT_CONNECTING_STATUS2, 1, -1).sendToTarget();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        }
    }


    private class ConnectedBluetoothThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }

        public void write(String str) {
            byte[] bytes = str.getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ConnectedBluetoothThread2 extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;


        public ConnectedBluetoothThread2(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;


            try {
                tmpIn = socket.getInputStream();

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;

        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler2.obtainMessage(BT_MESSAGE_READ2, bytes, -1, buffer).sendToTarget();
//
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }




    }
}

