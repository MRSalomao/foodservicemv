package br.com.leomarvic;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.leomarvic.HTTPHandler;

public class BuyItemScreen extends Activity {
    /** Called when the activity is first created. */
	TextView currentBalance;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyitem);
        
        currentBalance = (TextView) findViewById(R.id.tvCurrentBalance);
        
        Item current_item = ItemListScreen.selectedItem;
        
        //Set text content of screen
        
        //Item Name
        TextView itemNameTextView = (TextView) findViewById(R.id.textViewBuyItemName);
        itemNameTextView.setText(current_item.getName());
        
        //Item Image
        File imgFile = new  File(HTTPHandler.PATH_IMAGES+current_item.getImage().getID());
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = (ImageView) findViewById(R.id.imageBuyItem);
            myImage.setImageBitmap(myBitmap);
        }

        //Item Price
        TextView itemPriceTextView = (TextView) findViewById(R.id.textViewBuyItemPrice);
        itemPriceTextView.setText("Preço: R$ "+current_item.getPrice());
        
        //Item Description
        TextView itemDescTextView = (TextView) findViewById(R.id.textViewBuyItemDesc);
        itemDescTextView.setText("Descrição: "+current_item.getDesc());
        
        //Check if logged user, then change text view to show which user
        //TextView loggedUserTextView = (TextView) findViewById(R.id.textViewLoggedUser);
        if (FoodService.active_user == null)
        {
        	currentBalance.setText("Nenhum Usuário Ativo");
        	currentBalance.setTextColor(Color.RED);
        }
        else
        {
            currentBalance.setText("Saldo: " + FoodService.hHandler.getUserBalance(FoodService.active_user));
        	//loggedUserTextView.setTextColor(Color.GREEN);
        }

        
        ( (Button)findViewById(R.id.btRefresh) ).setOnClickListener(new View.OnClickListener() {	

        	public void onClick(View v) {
        		currentBalance.setText("Saldo: " + FoodService.hHandler.getUserBalance(FoodService.active_user));
        	}
        });
        
        ( (Button)findViewById(R.id.btBuyItem) ).setOnClickListener(new View.OnClickListener() {	

        	public void onClick(View v) {/*
        		String user = getuser.getText().toString();
        		String password = getpasswd.getText().toString();
        		String check_response = FoodService.hHandler.checkUserPassword(user, password);
				
        		//If OK, register active user
        		if (check_response.equalsIgnoreCase("True")) 
    			{
    				FoodService.active_user = user;
    				//Toast OK
    				Context context = getApplicationContext();
    				CharSequence text = "Usuário e Senha Ok!";
    				int duration = Toast.LENGTH_SHORT;

    				Toast toast = Toast.makeText(context, text, duration);
    				toast.show();
    			}
        		else
        		{
    				FoodService.active_user = null;
    				Context context = getApplicationContext();
    				CharSequence text = "Usuário ou Senha Inaválidos!";
    				int duration = Toast.LENGTH_SHORT;


        		}*/
        		//Check if user is logged in
        		if (FoodService.active_user == null)
        		{
        			//Complain
    				Toast toast = Toast.makeText(getApplicationContext(), "Nenhum Usuário Ativo!", Toast.LENGTH_SHORT);
    				toast.show();
        		}
        		else
        		{
        			//Check if user has balance
        			float user_balance = Float.valueOf(FoodService.hHandler.getUserBalance(FoodService.active_user)).floatValue();
        			float item_price = Float.valueOf(ItemListScreen.selectedItem.getPrice()).floatValue();
        			if (user_balance > item_price)
        			{
        				String response = FoodService.hHandler.setUserBalance(FoodService.active_user, user_balance-item_price);
        				if (response.equalsIgnoreCase("ok"))
        				{
            				Toast toast = Toast.makeText(getApplicationContext(), "Compra Efetuada com Sucesso!", Toast.LENGTH_SHORT);
            				toast.show();
            				currentBalance.setText("Saldo: " + FoodService.hHandler.getUserBalance(FoodService.active_user));
        				}
        				else
        				{
            				Toast toast = Toast.makeText(getApplicationContext(), "Houve um Problema na Compra!", Toast.LENGTH_SHORT);
            				toast.show();
        				}
        			}
        			else
        			{
        				Toast toast = Toast.makeText(getApplicationContext(), "Saldo Insuficiente!", Toast.LENGTH_SHORT);
        				toast.show();
        			}
        		}
        	}
        });
    }
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}
}