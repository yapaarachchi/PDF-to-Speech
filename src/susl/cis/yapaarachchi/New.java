package susl.cis.yapaarachchi;






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
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;



public class New extends Activity {
   
	private String pdfPath;
	
	 
	
	/** Called when the activity is first created. */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newnew);
        
        
        

					
        try
        {
        	Bundle extras = getIntent().getExtras();
			
		     
        	if(extras !=null)
	        {
		        	Bundle b = getIntent().getExtras();

		        	pdfPath = b.getString("PATH");
		        	Toast.makeText(New.this, 
							pdfPath , Toast.LENGTH_LONG).show();
	        }
        	
		         
        }
        catch(Exception e)
        {
        	Toast.makeText(New.this, 
					"Error"+e, Toast.LENGTH_LONG).show();
        	
        }        			
			        			
			        			
			        	
    }
    
}