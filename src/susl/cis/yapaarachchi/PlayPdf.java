package susl.cis.yapaarachchi;






import com.itextpdf.text.pdf.PdfReader;

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

import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;



public class PlayPdf extends Activity {
    private TodoDbAdapter dbHelper;
	private Long mRowId;
	private String pdfPath;
	private String pdfName;
	private Button All;
	private Button Page;
	private Button Pages;
	private EditText pNumber;
	private EditText from;
	private EditText to;
	private int n;
	 
	
	/** Called when the activity is first created. */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.playpdf);
        
        
        dbHelper = new TodoDbAdapter(this);
		dbHelper.open();
	        
	   	
		 	Bundle extras = getIntent().getExtras();
	        mRowId = null;
		        if(extras !=null)
		        {
		        	Bundle b = getIntent().getExtras();

		        	mRowId = b.getLong("ID", 0);
				
		        }  
		        
		        
		        if (mRowId != null) 
				{
				
					
				Cursor cursor = dbHelper.fetchTodo(mRowId);
				startManagingCursor(cursor);

				pdfPath = cursor.getString(cursor.getColumnIndexOrThrow(TodoDbAdapter.KEY_PATH));
				pdfName=cursor.getString(cursor.getColumnIndexOrThrow(TodoDbAdapter.KEY_NAME));
				}
			
		        try
	    		{
	    		PdfReader  reader1 = new PdfReader(pdfPath);
				n = reader1.getNumberOfPages();
				
				TextView t=(TextView) findViewById(R.id.showPages);
				t.setText("PDF has "+ n +" page(s)");
				
				
	    		}
	    		catch(Exception e)
	    		{
	    			
	    		}
	    		
	    		
	    		
				
		        All = (Button) findViewById(R.id.all);
		        All.setOnClickListener(new OnClickListener() {			
					@Override
					public void onClick(View v) {
	        
						Intent i=new Intent(PlayPdf.this, PlayPdf_Whole.class);
						Bundle bi = new Bundle();
						bi.putString("PATH", pdfPath);
					    bi.putString("NAME", pdfName);
					    bi.putInt("P_NUMBER", n);
					   i.putExtras(bi);
					    startActivity(i);
					}
				});
		        
		        
		        
		        Page = (Button) findViewById(R.id.page);
		        pNumber=(EditText) findViewById(R.id.pNumber);
		        
		        Page.setOnClickListener(new OnClickListener() {			
					@Override
					public void onClick(View v) {
						
					
						int pageNumber = 0;
				    	
						
						try
						{
							
							pageNumber =Integer.parseInt(pNumber.getText().toString());
						}
						catch(Exception e)
						{
							
					    	
						}
				    	if(pageNumber>0)
				    	{
				    	
							
							if(n<pageNumber)
							{
								Toast.makeText(PlayPdf.this,"Invalid. PDF only has "+n+" page(s)" ,Toast.LENGTH_LONG).show();
						    	
							}
							else
							{
								Intent i=new Intent(PlayPdf.this, PlayPdf_Page.class);
								Bundle b = new Bundle();
							    b.putString("PATH", pdfPath);
							    b.putString("NAME", pdfName);
							    b.putInt("P_NUMBER", pageNumber);
							    
							    i.putExtras(b);
							    startActivity(i);
							}
				    		
							
				    		
				    		
				    	
				    	}
				    	else
				    	{
				    		Toast.makeText(PlayPdf.this,"You should provide a number for PDF page." ,Toast.LENGTH_LONG).show();
					    	
				    		
				    	}
						
						
					}
				});
		        
		        
		        
		        Pages = (Button) findViewById(R.id.pages);
		        from=(EditText) findViewById(R.id.from);
		        to=(EditText) findViewById(R.id.to);
		        
		        Pages.setOnClickListener(new OnClickListener() {			
					@Override
					public void onClick(View v) {
	        
						int fromP = 0;
						int toP = 0;
						try
						{
						fromP =Integer.parseInt(from.getText().toString());
						 toP =Integer.parseInt(to.getText().toString());
						}
						catch(Exception e)
						{
							
					    	
						}
				    	if(fromP>0 )
				    	{
				    		if(toP>0)
					    	{
				    		if(fromP>=toP)
				    		{
				    			Toast.makeText(PlayPdf.this,"Invalid combinations of page numbers" ,Toast.LENGTH_LONG).show();
				    		}
				    		else
				    		{
				    			if(n<toP)
								{
									Toast.makeText(PlayPdf.this,"Invalid. PDF only has "+n+" page(s)" ,Toast.LENGTH_LONG).show();
							    	
								}
								else
								{
									try{
									
									Intent in=new Intent(PlayPdf.this, PlayPdf_Pages.class);
									Bundle bu = new Bundle();
								    bu.putString("PATH", pdfPath);
								    
								    bu.putString("NAME", pdfName);
								  
								    bu.putInt("FROM", fromP);
								    bu.putInt("TO", toP);
								    in.putExtras(bu);
								    startActivity(in);
							    	
									}
									catch(Exception e)
									{
										Toast.makeText(PlayPdf.this,""+e ,Toast.LENGTH_LONG).show();
								    	
									}
									
								}
					    		
				    		}
					    	}
					    	else
					    	{
					    		Toast.makeText(PlayPdf.this,"You should provide a number for PDF page." ,Toast.LENGTH_LONG).show();
						    	
					    		
					    	}
				    		
				    	
				    	}
				    	else
				    	{
				    		Toast.makeText(PlayPdf.this,"You should provide a number for PDF page." ,Toast.LENGTH_LONG).show();
					    	
				    		
				    	}
	        
					}
				});
		       
		       		        
		        

					
			        			
			        			
			        			
			        	
    }
    
}