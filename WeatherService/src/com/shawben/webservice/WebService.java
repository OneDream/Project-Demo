package com.shawben.webservice;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
public class WebService extends Activity {
	//ָ�� WebService �������ռ�͵��÷���
	private static final String NAMESPACE = "http://WebXml.com.cn/";
	private static final String METHOD_NAME = "getWeatherbyCityName";
	private static String SOAP_ACTION = "http://WebXml.com.cn/getWeatherbyCityName";
	// WebService��ַ
	private static String URL = "http://www.webxml.com.cn/webservices/weatherwebservice.asmx";
	private SoapObject getInfo;
	private String weatherToday;
	//button
	private Button button;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button = (Button)findViewById(R.id.btn_getinfo);
        button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 String city="����";
				 getWeather(city);
			}
        	
        });
    }

	public void getWeather(String cityName) {
		// TODO Auto-generated method stub
		//��webservice�������ݵĹ���
		SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
		//theCityName ��webService�ṩ�ĺ�������
		request.addProperty("theCityName", cityName);
		//����Ҫ�������ݵ��ŷ�ķ�װ��ʽ
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		 envelope.bodyOut = request;
		 envelope.dotNet = true;
		 envelope.setOutputSoapObject(request);
		//����HttpTransportSE����,ͨ��HttpTransportSE��Ĺ��췽������ָ��WebService��url
		 HttpTransportSE transport = new HttpTransportSE(URL);
		 transport.debug = true;  
		 try {
			//ʹ��cal����webservice�ķ���
			transport.call(SOAP_ACTION,envelope);
			//��÷��������ص���Ϣ
			getInfo =(SoapObject) envelope.getResponse();
			parseInfo(getInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseInfo(SoapObject getInfo2) {
		// TODO Auto-generated method stub
		  String date = getInfo2.getProperty(6).toString();
		  weatherToday = "���죺" + date.split(" ")[0];
		  weatherToday = weatherToday + "\n������" + date.split(" ")[1];
		  weatherToday = weatherToday + "\n���£�"
		    + getInfo2.getProperty(5).toString();
		  weatherToday = weatherToday + "\n������"
		    + getInfo2.getProperty(7).toString() + "\n";
		  System.out.println("weatherToday is " + weatherToday);
		  Toast.makeText(this, weatherToday, Toast.LENGTH_LONG).show();
	}
}