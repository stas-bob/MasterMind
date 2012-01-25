package de.stas.mm.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.stas.mm.InvalidLineException;
import de.stas.mm.LineEmptyException;
import de.stas.mm.LineNotFullException;
import de.stas.mm.LoseException;
import de.stas.mm.R;
import de.stas.mm.board.Board;
import de.stas.mm.board.PinColor;
import de.stas.mm.view.BoardView;
import de.stas.mm.view.ColorButton;
import de.stas.mm.view.ColorButtonGroup;

public class Main extends Activity implements OnClickListener {
	private ColorButtonGroup cbg;
	private Board board;
	private BoardView boardView;
	
	private static final int DIALOG_WIN = 0;
	private static final int DIALOG_LOST = 1;
	
	private static final int LINEAR_LAYOUT_LOST_ID = 12311;
	
	@Override
	public Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_WIN:
			AlertDialog.Builder b = new AlertDialog.Builder(this);
			b.setMessage("you have got the answer");
			b.setTitle("Win");
			b.setPositiveButton("ok", new DialogInterface.OnClickListener() {
				
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.dismiss();
				}
			});
			return b.create();
		case DIALOG_LOST: 
			LinearLayout dialogLayout = new LinearLayout(this);
			dialogLayout.setOrientation(LinearLayout.VERTICAL);
			
	    	LinearLayout pinsLayout = new LinearLayout(this);
	    	pinsLayout.setId(LINEAR_LAYOUT_LOST_ID);
	    	pinsLayout.setOrientation(LinearLayout.HORIZONTAL);
	    	for (int i = 0; i < board.getSecretLine().getPins().length; i++) {
	    		ImageView img = new ImageView(this);
	    		pinsLayout.addView(img);
	    	}
	    	TextView tv = new TextView(this);
	    	tv.setText("the code was:");
	    	
	    	Dialog dialog = new Dialog(this);
	    	dialog.setTitle("You lost");
	    	
	    	dialogLayout.addView(tv);
	    	dialogLayout.addView(pinsLayout);
	    	dialog.setContentView(dialogLayout);
			return dialog;
		}
		return null;
	}
	
	@Override
	public void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		switch(id) {
		case DIALOG_LOST:
			LinearLayout ll = (LinearLayout) dialog.findViewById(LINEAR_LAYOUT_LOST_ID);
			if (ll != null) {
				for (int i = 0; i < board.getSecretLine().getPins().length; i++) {
					switch (board.getSecretLine().getPins()[i].getColor()) {
					case PinColor.ORANGE: ((ImageView)ll.getChildAt(i)).setImageDrawable(getResources().getDrawable(R.drawable.orange)); break;
					case PinColor.PURPLE: ((ImageView)ll.getChildAt(i)).setImageDrawable(getResources().getDrawable(R.drawable.purple)); break;
					case PinColor.BLACK: ((ImageView)ll.getChildAt(i)).setImageDrawable(getResources().getDrawable(R.drawable.black)); break;
					case PinColor.BLUE: ((ImageView)ll.getChildAt(i)).setImageDrawable(getResources().getDrawable(R.drawable.blue)); break;
					case PinColor.CYAN: ((ImageView)ll.getChildAt(i)).setImageDrawable(getResources().getDrawable(R.drawable.cyan)); break;
					case PinColor.GREEN: ((ImageView)ll.getChildAt(i)).setImageDrawable(getResources().getDrawable(R.drawable.green)); break;
					case PinColor.RED: ((ImageView)ll.getChildAt(i)).setImageDrawable(getResources().getDrawable(R.drawable.red)); break;
					case PinColor.YELLOW: ((ImageView)ll.getChildAt(i)).setImageDrawable(getResources().getDrawable(R.drawable.yellow)); break;
					}
				}
			}
		}
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        boardView = (BoardView) findViewById(R.id.board);
        newBoard();
        boardView.setMain(this);
        ((Button)findViewById(R.id.blue_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.green_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.yellow_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.orange_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.purple_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.cyan_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.black_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.red_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.ok_button)).setOnClickListener(this);
        cbg = new ColorButtonGroup();
        cbg.add((ColorButton)findViewById(R.id.blue_button));
        cbg.add((ColorButton)findViewById(R.id.green_button));
        cbg.add((ColorButton)findViewById(R.id.yellow_button));
        cbg.add((ColorButton)findViewById(R.id.orange_button));
        cbg.add((ColorButton)findViewById(R.id.purple_button));
        cbg.add((ColorButton)findViewById(R.id.cyan_button));
        cbg.add((ColorButton)findViewById(R.id.black_button));
        cbg.add((ColorButton)findViewById(R.id.red_button));
        try {
			cbg.select(PinColor.BLUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void newBoard() {
        boardView.setBoard(board = new Board(10, 4));
        boardView.invalidate();
    }
    


	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.blue_button :
			try {
				cbg.select(PinColor.BLUE);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case R.id.green_button : 
			try {
				cbg.select(PinColor.GREEN);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case R.id.red_button :
			try {
				cbg.select(PinColor.RED);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case R.id.black_button : 
			try {
				cbg.select(PinColor.BLACK);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			break;
		case R.id.cyan_button : 
			try {
				cbg.select(PinColor.CYAN);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case R.id.yellow_button : 
			try {
				cbg.select(PinColor.YELLOW);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			break;
		case R.id.purple_button : 
			try {
				cbg.select(PinColor.PURPLE);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			break;
		case R.id.orange_button : 
			try {
				cbg.select(PinColor.ORANGE);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			break;
		case R.id.ok_button: 
			try {
				if (board.testCodeLineWithSecretLine()) {
					Toast.makeText(this, "Woohoo!", Toast.LENGTH_LONG).show();
					newBoard();
				}
				boardView.invalidate();
			} catch (LineNotFullException e) {
				handleError(e);
			} catch (LineEmptyException e) {
				handleError(e);
			} catch (LoseException e) {
				handleLose(e);
			}
			break;
		}
	}
	
	private void handleLose(LoseException e) {
		showDialog(DIALOG_LOST);
		newBoard();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem newGame = menu.add("new");
		newGame.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				newBoard();
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}
	
	private void handleError(LineEmptyException e) {
		Toast.makeText(this, "line empty!", Toast.LENGTH_LONG).show();
	}

	private void handleError(LineNotFullException e) {
		Toast.makeText(this, "line not full!", Toast.LENGTH_LONG).show();
	}

	public int getSelectedColor() {
		return cbg.getSelectedColorButton().getColor();
	}

	public void handleError(InvalidLineException e) {
		Toast.makeText(this, "invalid line", Toast.LENGTH_LONG).show();
	}
}