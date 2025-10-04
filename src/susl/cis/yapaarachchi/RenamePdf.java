package susl.cis.yapaarachchi;


import susl.cis.yapaarachchi.db.TodoDbAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RenamePdf  extends Activity{
	
	
	private EditText existName;
	private EditText newName;
	private TodoDbAdapter dbHelper;
	private Button renameButton;
	private Button cancelButton;
	private Long mRowId;
	private Long A;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.rename);
		 
		 dbHelper = new TodoDbAdapter(this);
		 dbHelper.open();
		 
	     Bundle extras = getIntent().getExtras();
	     mRowId = null;
	     
		     if(extras !=null)
		        {
		        	Bundle b = getIntent().getExtras();
 
		        	mRowId = b.getLong("ID", 0);
		        	A = b.getLong("A", 0);
				
		        }  
		        
		      
		  Existname();
		        
		       
		        
		  renameButton = (Button)findViewById(R.id.rename); 
		  cancelButton = (Button)findViewById(R.id.cancel);
		  newName = (EditText)findViewById(R.id.newname);
		  
		        
		        renameButton.setOnClickListener(new OnClickListener() {
				    @Override
				    public void onClick(View v) {
				      
				    	String text1 = newName.getText().toString();
				    	
				    	if(text1.equalsIgnoreCase(""))
				    	{
				    		Toast.makeText(RenamePdf.this,"You should provide new name for PDF." ,Toast.LENGTH_LONG).show();
				    	
				    	
				    	}
				    	else
				    	{
				    		dbHelper.reNamePdf(mRowId,text1);
				    		Toast.makeText(RenamePdf.this,"Changed name successfully." ,Toast.LENGTH_LONG).show();
				    		
				    		if(A==1)
				    		{
				    			Intent i=new Intent(RenamePdf.this, LoadFile.class);
					    		startActivity(i);
				    		}
				    		else if(A==2)
				    		{
				    			Intent i=new Intent(RenamePdf.this, LoadFileShowPdf.class);
					    		startActivity(i);
				    		}
				    		else
				    		{
				    			Intent i=new Intent(RenamePdf.this, LoadFileList.class);
					    		startActivity(i);
				    		}
				    		
				    	}
				    	
				    }
				  });
		        
		        
		        cancelButton.setOnClickListener(new OnClickListener() {
				    @Override
				    public void onClick(View v) {
				      
				    	
				    	if(A==1)
			    		{
			    			Intent i=new Intent(RenamePdf.this, LoadFile.class);
				    		startActivity(i);
			    		}
			    		else if(A==2)
			    		{
			    			Intent i=new Intent(RenamePdf.this, LoadFileShowPdf.class);
				    		startActivity(i);
			    		}
			    		else
			    		{
			    			Intent i=new Intent(RenamePdf.this, LoadFileList.class);
				    		startActivity(i);
			    		}
				    	
				    	
				    }
				  });
	        	
	        	
	        
	        
	}
	
	
	private void Existname ()
	{
		
		if (mRowId != null) 
		{
		
			
		Cursor cursor = dbHelper.fetchTodo(mRowId);
		startManagingCursor(cursor);

		String dbName = cursor.getString(cursor.getColumnIndexOrThrow(TodoDbAdapter.KEY_NAME));

		existName = (EditText)findViewById(R.id.name);
		existName.setText(dbName, EditText.BufferType.EDITABLE);
		existName.setEnabled(false);
		
		}
	
	}
	
	


}
