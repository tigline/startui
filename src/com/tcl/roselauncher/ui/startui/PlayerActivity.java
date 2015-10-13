/**
 * 
 */
package com.tcl.roselauncher.ui.startui;




import com.tcl.roselauncher.ui.startui.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @Project SimpleGame-android	
 * @author houxb
 * @Date 2015-9-9
 */
public class PlayerActivity extends Activity implements OnClickListener{
	private Button startButtom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        startButtom = (Button) findViewById(R.id.back);




        startButtom.setOnClickListener(this);
    }
	/* (non-Javadoc)
	 * @see android.view.View.OnCli ckListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
//			Intent intent = new Intent(PlayerActivity.this, MainActivity.class);
//			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}
}
