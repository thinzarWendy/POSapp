package com.example.posapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OrderActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)



        val intent = intent
        val catID = intent.getIntExtra("CatID",0)

        //Lookup the recyclerview in activity layout
        when(catID){
            MACARON_CAT_ID->{
                //Lookup the recyclerview in activity layout
                val rvMacarons = findViewById<View>(R.id.rvProductsList) as RecyclerView
                //Initatie macarons
                val macarons = Macaron.createMacaronsList()
                //Create the adapter passing in the sample user data
                val adapter = MacaronAdapter(macarons){macaron ->
                    Log.d(TAG,"onClick Listener: ${macaron.name}")
                }
                //Attach the adapter to the recyclerview to populate items
                rvMacarons.adapter= adapter
                //Set layout manager to position the item
                rvMacarons.layoutManager = LinearLayoutManager(this)
                Toast.makeText(this,"Macaron_Category",Toast.LENGTH_SHORT).show()
            }
            DRINK_CAT_ID -> {
                //populate drink list
                Toast.makeText(this,"Drink_Category!",Toast.LENGTH_SHORT).show()
            }
            else ->{
                //Should not reach this point
                Toast.makeText(this,"Unknown Category!",Toast.LENGTH_SHORT).show()
            }
        }

        fun onclick_submit_order_btn(view: View){
            Log.i(TAG,"Submit order button clicked. Order is going to be stored.")

            //Create a launch coroutine
            GlobalScope.launch {

               /* //Order 1 Examle:btanch Id= 5001, Staff Id =2001
                val order1 = Order(null,5001,2001)*/
                //Get a reference to database
                val db = POSAppDatabase.getInstance(applicationContext)
                val orders = db.orderDao().getAll()
              /*  val orderID:Long = db.orderDao().insert(order1)

                // create  two dummy order lines with productID= 1001 and 1005
                val orderLine1 = OrderLine(null,orderID,1001,50,3)
                val orderLine2 = OrderLine(null,orderID,1005,70,2)
                db.orderLineDao().insertAll(orderLine1,orderLine2)*/

                Log.i(TAG,"Orders:")
                for(order in orders){
                    Log.i(TAG,"Order ID = ${order.uid}," +
                            "Branch ID = ${order.branchID} "  +
                            "Staff ID = ${order.staffID}")
                }
                Log.i(TAG,"OrderLines:")
                val orderLines = db.orderLineDao().getAll()
                for(orderLine in orderLines){
                    Log.i(TAG,"orderLine ID = ${orderLine.uid}, "+
                            "Order ID = ${orderLine.orderID} "+
                            "Product ID = ${orderLine.productID}")
                }



            }
        }



    }
}