package susl.cis.yapaarachchi;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class About extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about);
        
        
        Button home = (Button)findViewById(R.id.home);
		 home.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
	    
					Intent i=new Intent(About.this, Start.class);
				    startActivity(i);
				}
			});
        
}
}
