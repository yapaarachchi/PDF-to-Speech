package susl.cis.yapaarachchi;




import susl.cis.yapaarachchi.db.TodoDbAdapter;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TodoDbAdapter dbHelper = new TodoDbAdapter(this);
		dbHelper.open();
        
        /* TabHost will have Tabs */
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        
        /* TabSpec used to create a new tab. 
         * By using TabSpec only we can able to setContent to the tab.
         * By using TabSpec setIndicator() we can set name to tab. */
        
        /* tid1 is firstTabSpec Id. Its used to access outside. */
        TabSpec firstTabSpec = tabHost.newTabSpec("tid1");
        TabSpec secondTabSpec = tabHost.newTabSpec("tid1");
        
        /* TabSpec setIndicator() is used to set name for the tab. */
        /* TabSpec setContent() is used to set content for a particular tab. */
        firstTabSpec.setIndicator("Load PDF",getResources().getDrawable(R.drawable.ic_tab_loadfile)).setContent(new Intent(this,LoadFile.class));
        secondTabSpec.setIndicator("Create PDF").setContent(new Intent(this,VoiceRecognitionDemo.class));
        
        /* Add tabSpec to the TabHost to display. */
        tabHost.addTab(firstTabSpec);
       // tabHost.addTab(secondTabSpec);
        
    }
    
    
    
}