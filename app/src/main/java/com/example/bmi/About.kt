package com.example.bmi

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        title = "About"

        val gmail:ImageButton = findViewById(R.id.gmail)
        val github:ImageButton = findViewById(R.id.github)
        val linkedin:ImageButton = findViewById(R.id.linkedin)

        gmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:code.with.lazylad@gmail.com")
            startActivity(emailIntent);
        }

        linkedin.setOnClickListener {
            val uri: Uri = Uri.parse("https://www.linkedin.com/in/tathagat7k/") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        github.setOnClickListener {
            val uri: Uri = Uri.parse("https://github.com/Lazy-Lad") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        setupLink()
    }

    private fun setupLink() {
        val linkTextView = findViewById<TextView>(R.id.textView7)
        linkTextView.setOnClickListener {
            val uri: Uri = Uri.parse("https://www.flaticon.com/") // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}
