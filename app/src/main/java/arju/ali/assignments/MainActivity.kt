package arju.ali.assignments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onClickButtonAssignment1()
    }
    private fun onClickButtonAssignment1()
    {
        findViewById<Button>(R.id.btnAssignment1).setOnClickListener {
            startActivity(Intent(this, Assignment1::class.java))
        }
    }
}