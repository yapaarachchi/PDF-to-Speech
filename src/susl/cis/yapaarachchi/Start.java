package susl.cis.yapaarachchi;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;



public class Start extends Activity {
    /** Called when the activity is first created. */
	
	private Button play;
	private Button show;
	private Button list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);
        
        
        	
        	play = (Button) findViewById(R.id.play);
        	
        	play.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
    
				Intent i=new Intent(Start.this, LoadFile.class);
				Bundle b = new Bundle();
			    b.putLong("CHOICE", 1);
			    i.putExtras(b);
			    startActivity(i);
			}
		});
        
        	
show = (Button) findViewById(R.id.show);
        	
show.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
    
				Intent i=new Intent(Start.this, LoadFileShowPdf.class);
				Bundle b = new Bundle();
			    b.putLong("CHOICE2", 2);
			    i.putExtras(b);
			    startActivity(i);
			}
		});
        
    
    
    Button lists = (Button) findViewById(R.id.list);
    lists.setOnClickListener(new OnClickListener() {			
		@Override
		public void onClick(View v) {

			Intent i=new Intent(Start.this, LoadFileList.class);
			Bundle b = new Bundle();
		    b.putLong("CHOICE3", 3);
		    i.putExtras(b);
		    startActivity(i);
		}
	});
    
    Button about = (Button) findViewById(R.id.about);
    about.setOnClickListener(new OnClickListener() {			
		@Override
		public void onClick(View v) {

			Intent i=new Intent(Start.this, About.class);
			
		    startActivity(i);
		}
	});
    
}
    
    
    
    
}