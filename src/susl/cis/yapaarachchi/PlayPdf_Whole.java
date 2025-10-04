package susl.cis.yapaarachchi;




import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;


import java.io.File;
import java.util.Arrays;

import susl.cis.yapaarachchi.db.TodoDbAdapter;
import android.R.array;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;


import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;



public class PlayPdf_Whole extends Activity implements OnInitListener {
    private TodoDbAdapter dbHelper;
	
	private int MY_DATA_CHECK_CODE = 0;
	private TextToSpeech tts;
	private String str;
	private String text;
	private String pdfPath;
	private String[] page;
	private int n;
	private static final int HELLO_ID = 1;
	private ProgressDialog progressDialog;
	private String show="";
	private SeekBar seekbar;
	private AudioManager am;
	private TextView volume_label;
	int volume;
	private EditText PdfText;
	private int pageNumber;
	private String pdfName;
	/** Called when the activity is first created. */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.playpdf_whole);
        
        
        Bundle extras = getIntent().getExtras();
		
	     
    	if(extras !=null)
        {
	        	Bundle b = getIntent().getExtras();

	        	pdfPath = b.getString("PATH");
	        	pdfName=b.getString("NAME");
	        	pageNumber=b.getInt("P_NUMBER",0);
        }
    	
        
				        
			
		       
		        final ToggleButton speakButton = (ToggleButton) findViewById(R.id.speak_button);
		        PdfText=(EditText) findViewById(R.id.pdfText);
		        PdfText.setEnabled(false);
		       
		        TextView name = (TextView) findViewById(R.id.pdfName);
		        TextView pages = (TextView) findViewById(R.id.page);
		        
		        name.setText(String.valueOf(pdfName+".pdf"));
		        pages.setText(String.valueOf(pageNumber));
		        
		       
		        runDialog();
		        PdfText.setText("Press play button to see content", EditText.BufferType.EDITABLE);
		        final TextView playing=(TextView) findViewById(R.id.playing);
		        
					speakButton.setOnClickListener(new OnClickListener() {			
						@Override
						public void onClick(View v) {
		        
							//Display Notification
							//displayNotification();
							
							if (speakButton.isChecked()) { // Checked -> Pause icon visible
			        			
								PdfText.setText(Arrays.toString(page), EditText.BufferType.EDITABLE);
								playing.setText(String.valueOf("Playing..."));
			        			start();
								
			        		} else { // Unchecked -> Play icon visible
			        			
			        			
			        			playing.setText(String.valueOf(""));
			        			if (tts != null) {
			        	            tts.stop();
			        	            
			        	        }
			        		}
							
		        
				
						}
					});
		        Intent checkIntent = new Intent();
				checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
				startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
 
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// success, create the TTS instance
				tts = new TextToSpeech(this, this);
			} 
			else {
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
		
		
		seekbar = (SeekBar) findViewById(R.id.seek);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volume_label = (TextView) findViewById(R.id.volume);
        // Assign Curent Volume to Volume variable
        volume = am.getStreamVolume(3);
        
        volume_label.setText(String.valueOf(volume));
        
        // Set the SeekBar to the have a proper Maximum volume.
        seekbar.setMax(am.getStreamMaxVolume(3));
        
        // Set the seekbar's volume and create a Listener to auto 
        // update the media volume as the slider is changed
        seekbar.setProgress(volume);
        seekbar.setOnSeekBarChangeListener(VolumeChange);


	}
	private OnSeekBarChangeListener VolumeChange = new OnSeekBarChangeListener(){

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
		    int index = seekbar.getProgress();
			am.setStreamVolume(3, index, 1);
			volume_label.setText(String.valueOf(index));
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	};


	

	@Override
	public void onInit(int status) {		
		if (status == TextToSpeech.SUCCESS) {
			//Toast.makeText(PlayPdf.this,"Text-To-Speech engine is initialized", Toast.LENGTH_LONG).show();
		}
		else if (status == TextToSpeech.ERROR) {
			Toast.makeText(PlayPdf_Whole.this, 
					"Error occurred while initializing Text-To-Speech engine", Toast.LENGTH_LONG).show();
		}
	}
		
	public void displayNotification()
	{
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		
		int icon = R.drawable.icon;
		CharSequence tickerText = "Hello";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		
		Context context = getApplicationContext();
		CharSequence contentTitle = "My notification";
		CharSequence contentText = "Hello World!";
		Intent notificationIntent = new Intent(this, PlayPdf.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		
		

		mNotificationManager.notify(HELLO_ID, notification);

	}
	
	public void start()
	{
		try
        {
			
			for(int j=0;j<n;j++)
			{
				int p=j+1;
			String text = page[j];
		if (text!=null && text.length()>0) {
			
			//Toast.makeText(PlayPdf.this, "" + text.length(), Toast.LENGTH_LONG).show();
			//Toast.makeText(PlayPdf.this, "Saying: " + j, Toast.LENGTH_LONG).show();
			tts.speak("Content of page "+ p+".    "+text, TextToSpeech.QUEUE_ADD, null);
			
		}
		else
		{
			tts.speak("Sorry, No text in this page to read ", TextToSpeech.QUEUE_ADD, null);
		}
			
		}
		
		
        }
        catch(Exception e)
        {
        	Toast.makeText(PlayPdf_Whole.this,"Error"+ e,Toast.LENGTH_LONG).show();
        	
        	
        }
        
	}
	

	
	
	
	 public void PdfToText(){
		 
		 try
	        {
				
				PdfReader reader = new PdfReader(pdfPath);
				n = reader.getNumberOfPages();

				page=new String[n];
				
				for (int i=1;i<=n;i++)
				{
				str=PdfTextExtractor.getTextFromPage(reader, i); //Extracting the content from a particular page.
				
				 String pageContent = str;
				 page[i-1]=pageContent;
				 
				}
				
	        }
	        catch(Exception e)
	        {
	        	Toast.makeText(PlayPdf_Whole.this,"Error"+ e,Toast.LENGTH_LONG).show();
	        	
	        	
	        }
		 
	 }
	 
	 private void runDialog()
		{
		    	progressDialog = ProgressDialog.show(this, "Please wait ...!", "Extracting the Text from PDF ...");

		    	new Thread(new Runnable(){
		    		public void run(){
		    			try {
		    				PdfToText();
		    				
							progressDialog.dismiss();
						} catch (Exception e) {
							e.printStackTrace();
						}
		    		}
		    	}).start();
		}
	 
	 
 public void ShowText(){
		 
		 try
	        {
				
				
				
				
			        PdfText.setText(page[1], EditText.BufferType.EDITABLE);
			        
			
				
				
	        }
	        catch(Exception e)
	        {
	        	Toast.makeText(PlayPdf_Whole.this,"Error"+ e,Toast.LENGTH_LONG).show();
	        	
	        	
	        }
		 
	 }

	

}
