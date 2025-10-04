package susl.cis.yapaarachchi;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;





import susl.cis.yapaarachchi.db.TodoDbAdapter;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;




public class LoadFileList extends ListActivity {
	
	//start File Explorer variables
	ArrayList<String> str = new ArrayList<String>();
	private Boolean firstLvl = true;
	private static final String TAG = "F_PATH";
	private Item[] fileList;
	private File path = new File(Environment.getExternalStorageDirectory() + "");
	private String chosenFile;
	private static final int DIALOG_LOAD_FILE = 1000;
	public ListAdapter adapter;
	//end File Explorer variables
	
	private TodoDbAdapter dbHelper;
	private Cursor cursor;
	private View view;
	private ProgressDialog progressDialog;
	private TextView topic;
	private int choice;
	

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.loadfile);
		 
		 
		 this.getListView().setDividerHeight(2);
		 dbHelper = new TodoDbAdapter(this);
		 dbHelper.open();
		 
		 Bundle extras = getIntent().getExtras();
	       
		        if(extras !=null)
		        {
		        	Bundle b = getIntent().getExtras();

		        	choice = b.getInt("CHOICE", 1);
				
		        }  
		        topic=(TextView)findViewById(R.id.topic);
		        
		      
		        {
		        	topic.setText(String.valueOf("List of PDF"));
		        	
		        }
		        
		        
		 
		        	fillData();
		 
			ListView lv = getListView();
			registerForContextMenu(lv);
		 
		 
		 
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			    @Override
			    public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
			        onListItemClick(v,pos,id);
			    }
			});
			
			Button home = (Button)findViewById(R.id.home);
			 home.setOnClickListener(new OnClickListener() {			
					@Override
					public void onClick(View v) {
		    
						Intent i=new Intent(LoadFileList.this, Start.class);
					    startActivity(i);
					}
				});
	
	
	}
	
	
	/**
     * start click
     * */
	protected void onListItemClick(View v, int pos, long id) {
	    
	    
		   
		    
		   
		
		//Toast.makeText(LoadFile.this,"dfhdfh"+id ,Toast.LENGTH_LONG).show();
		}
	/**
     * end click
     * */

	
	private void fillData() {
		cursor = dbHelper.fetchAllTodos();
		startManagingCursor(cursor);

		String[] from = new String[] { TodoDbAdapter.KEY_NAME };
		int[] to = new int[] { R.id.label };

		// Now create an array adapter and set it to display using our row
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.show, cursor, from, to);
		setListAdapter(notes);
	}
	
	
	/**
     * start MENU
     * */
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.loadfile_menu, menu);
        return true;
    }
    
    /**
     * Event Handling for Individual menu item 
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        
        switch (item.getItemId())
        {
        
        
        case R.id.menu_search:
        	
        	
        	 
        	loadFileList();
        	
        	
       	 

     		showDialog(DIALOG_LOAD_FILE);
     		Log.d(TAG, path.getAbsolutePath());
     		
             return true;
        	
        
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * end MENU
     * */
    
    
    /**
     * start CONTEXT MENU
     * */
    
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	  super.onCreateContextMenu(menu, v, menuInfo);
    	  menu.setHeaderTitle("Select a Option");
    	  MenuInflater inflater = getMenuInflater();
    	  inflater.inflate(R.layout.loadfile_context_menu, menu);
    	}
    
    public boolean onContextItemSelected(MenuItem item) {
    	  
    	final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

    	
    	  switch(item.getItemId()) {
    	  case R.id.rename:
    		  
    		Intent i=new Intent(LoadFileList.this, RenamePdf.class);
  		    
  		    Bundle b = new Bundle();

  		    b.putLong("ID", info.id);
  		    b.putLong("A", 3);
  		    i.putExtras(b);
  		    startActivity(i);
  		    
    	        return true;
    	        
    	  case R.id.delete:

    		  AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		  builder.setMessage("Are you sure want to delete?")
    		         .setCancelable(false)
    		         .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    		             public void onClick(DialogInterface dialog, int id) {
    		            	 dbHelper.deleteTodo(info.id);
    		            	 Intent i=new Intent(LoadFileList.this, LoadFileList.class);
							   startActivity(i);
    		            	 Toast.makeText(LoadFileList.this,"Deleted the PDF successfully from the list." ,Toast.LENGTH_LONG).show();
    		            	 
    		            	 //adapter.notifyDataSetChanged();
    		             }
    		         })
    		         .setNegativeButton("No", new DialogInterface.OnClickListener() {
    		             public void onClick(DialogInterface dialog, int id) {
    		                  dialog.cancel();
    		             }
    		         });
    		  AlertDialog alert = builder.create();
    		  alert.setTitle("Delete PDF from list");
    		// Icon for AlertDialog
    		alert.setIcon(R.drawable.icon);
    		  alert.show();
    		  
    		 return true;
  	        
    	  case R.id.show:
    		  
    		  Cursor cursor = dbHelper.fetchTodo(info.id);
    		  startManagingCursor(cursor);

    		  String path = cursor.getString(cursor.getColumnIndexOrThrow(TodoDbAdapter.KEY_PATH));
    		  
    		  	Intent intent = new Intent(this, ShowPdf.class);
 		     	intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);
 		     	startActivity(intent);
    		     

  	        return true;
  	        
    	  case R.id.speech:

    		  Intent in=new Intent(LoadFileList.this, PlayPdf.class);
  		    
  		    Bundle bu = new Bundle();

  		    bu.putLong("ID", info.id);
  		    in.putExtras(bu);
  		    startActivity(in);
  		    
  	        return true;
    	 
    	  default:
    	        return super.onContextItemSelected(item);
    	  }
    }
    
    
    /**
     * end CONTEXT MENU
     * */
    
    
    
    /*
    **
    * start File Explorer
    * */
    
    private void loadFileList() {
		try {
			path.mkdirs();
		} catch (SecurityException e) {
			Log.e(TAG, "unable to write on the sd card ");
		}

		// Checks whether path exists
		if (path.exists()) {
			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String filename) {
					File sel = new File(dir, filename);
					// Filters based on whether the file is hidden or not
					return (sel.isFile() || sel.isDirectory())
							&& !sel.isHidden();

				}
			};

			String[] fList = path.list(filter);
			fileList = new Item[fList.length];
			for (int i = 0; i < fList.length; i++) {
				fileList[i] = new Item(fList[i], R.drawable.file_icon);

				// Convert into file path
				File sel = new File(path, fList[i]);

				// Set drawables
				if (sel.isDirectory()) {
					fileList[i].icon = R.drawable.directory_icon;
					Log.d("DIRECTORY", fileList[i].file);
				} else {
					Log.d("FILE", fileList[i].file);
				}
			}

			if (!firstLvl) {
				Item temp[] = new Item[fileList.length + 1];
				for (int i = 0; i < fileList.length; i++) {
					temp[i + 1] = fileList[i];
				}
				temp[0] = new Item("Up", R.drawable.directory_up);
				fileList = temp;
			}
		} else {
			Log.e(TAG, "path does not exist");
		}

		adapter = new ArrayAdapter<Item>(this,
				android.R.layout.select_dialog_item, android.R.id.text1,
				fileList) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// creates view
				View view = super.getView(position, convertView, parent);
				TextView textView = (TextView) view
						.findViewById(android.R.id.text1);

				// put the image on the text view
				textView.setCompoundDrawablesWithIntrinsicBounds(
						fileList[position].icon, 0, 0, 0);

				// add margin between image and text (support various screen
				// densities)
				int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
				textView.setCompoundDrawablePadding(dp5);

				return view;
			}
		};

	}

	private class Item {
		public String file;
		public int icon;

		public Item(String file, Integer icon) {
			this.file = file;
			this.icon = icon;
		}

		@Override
		public String toString() {
			return file;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new Builder(this);

		if (fileList == null) {
			Log.e(TAG, "No files loaded");
			dialog = builder.create();
			return dialog;
		}

		switch (id) {
		case DIALOG_LOAD_FILE:
			builder.setTitle("Choose your PDF");
			builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					chosenFile = fileList[which].file;
					File sel = new File(path + "/" + chosenFile);
					if (sel.isDirectory()) {
						firstLvl = false;

						// Adds chosen directory to list
						str.add(chosenFile);
						fileList = null;
						path = new File(sel + "");

						loadFileList();

						removeDialog(DIALOG_LOAD_FILE);
						showDialog(DIALOG_LOAD_FILE);
						Log.d(TAG, path.getAbsolutePath());

					}

					// Checks if 'up' was clicked
					else if (chosenFile.equalsIgnoreCase("up") && !sel.exists()) {

						// present directory removed from list
						String s = str.remove(str.size() - 1);

						// path modified to exclude present directory
						path = new File(path.toString().substring(0,
								path.toString().lastIndexOf(s)));
						fileList = null;

						// if there are no more directories in the list, then
						// its the first level
						if (str.isEmpty()) {
							firstLvl = true;
						}
						loadFileList();

						removeDialog(DIALOG_LOAD_FILE);
						showDialog(DIALOG_LOAD_FILE);
						Log.d(TAG, path.getAbsolutePath());

					}
					// File picked
					else {
						
						String pdfPath=path + "/" + chosenFile;
						pdfPath=pdfPath.toString();
						
						try
						{
							String pdf = chosenFile.substring(Math.max(0, chosenFile.length() - 3));
							
							String[] getName=chosenFile.split("\\.");
							String name = getName[0];
							
							
							
							if(pdf.equalsIgnoreCase("pdf"))
							{
								dbHelper.createTodo(pdfPath,name);
								long id=dbHelper.maxId();
								
								Intent i=new Intent(LoadFileList.this, LoadFileList.class);
							    
							   
							    startActivity(i);

								  
								
								
								
								
							}
							else
							{
						
								Toast.makeText(LoadFileList.this,"Error occured. You should select PDF file. Try again." ,Toast.LENGTH_LONG).show();
							}
						}
						catch(Exception e)
						{
							Toast.makeText(LoadFileList.this,"Error occured when selecting the PDF. Please select a PDF again"+e ,Toast.LENGTH_LONG).show();
						}
						

						
						// Perform action with file picked
					}

				}
			});
			break;
		}
		dialog = builder.show();
		return dialog;
	}
	
	/*
	    **
	    * end File Explorer
	    * */
   
}
