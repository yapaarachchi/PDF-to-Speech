package susl.cis.yapaarachchi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TodoDbAdapter {

	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_PATH = "path";
	public static final String KEY_NAME = "name";
	private static final String DB_TABLE = "pdf_path";
	private Context context;
	private SQLiteDatabase db;
	private TodoDatabaseHelper dbHelper;

	public TodoDbAdapter(Context context) {
		this.context = context;
	}

	public TodoDbAdapter open() throws SQLException {
		dbHelper = new TodoDatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	
/** * Create a new todo If the todo is successfully created return the new * rowId for that note, otherwise return a -1 to indicate failure. */

	public long createTodo(String path,String name) {
		ContentValues values = createContentValues(path,name);

		return db.insert(DB_TABLE, null, values);
	}

	
/** * Update the todo */

	public boolean reNamePdf(long rowId,String name) {
		ContentValues values = renameValues( name);

		return db.update(DB_TABLE, values, KEY_ROWID + "=" + rowId, null) > 0;
	}

	
/** * Deletes todo */

	public boolean deleteTodo(long rowId) {
		return db.delete(DB_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	
/** * Return a Cursor over the list of all todo in the database * * @return Cursor over all notes */

	public Cursor fetchAllTodos() {
		return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_PATH, KEY_NAME}, null, null, null, null, null);
	}

	
/** * Return a Cursor positioned at the defined todo 
 * @param mRowId */

	public Cursor fetchTodo(long rowId)throws SQLException{
		
		
		
		Cursor mCursor= db.query(true, DB_TABLE, new String[] { KEY_ROWID,KEY_PATH, KEY_NAME}, KEY_ROWID + "="+ rowId, null, null, null, null, null);
		
		if (mCursor != null) 
		{
			mCursor.moveToFirst();
			
		}
		
		
		return mCursor;
		
		
	}
	
	
public long maxId(){
		
		
		
		String query = "SELECT MAX(_id) AS max_id FROM pdf_path";
		Cursor cursor = db.rawQuery(query, null);

		
		int id = 0;     
	    if (cursor.moveToFirst())
	    {
	        do
	        {           
	            id = cursor.getInt(0);                  
	        } while(cursor.moveToNext());           
	    }
	    return id;

		
		
	}

	private ContentValues createContentValues(String path, String name) {
		ContentValues values = new ContentValues();
		values.put(KEY_PATH, path);
		values.put(KEY_NAME,name);
		return values;
	}
	
	private ContentValues renameValues(String name) {
		ContentValues values = new ContentValues();
		
		values.put(KEY_NAME,name);
		return values;
	}

}
