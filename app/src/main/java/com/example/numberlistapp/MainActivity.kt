package com.example.numberlistapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var etLimit: EditText
    private lateinit var rvNumbers: RecyclerView
    private lateinit var tvNoResult: TextView

    private lateinit var rbOdd: RadioButton
    private lateinit var rbEven: RadioButton
    private lateinit var rbPrime: RadioButton
    private lateinit var rbPerfect: RadioButton
    private lateinit var rbSquare: RadioButton
    private lateinit var rbFibonacci: RadioButton

    private lateinit var numberAdapter: NumberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ views
        etLimit = findViewById(R.id.etLimit)
        rvNumbers = findViewById(R.id.rvNumbers)
        tvNoResult = findViewById(R.id.tvNoResult)

        rbOdd = findViewById(R.id.rbOdd)
        rbEven = findViewById(R.id.rbEven)
        rbPrime = findViewById(R.id.rbPrime)
        rbPerfect = findViewById(R.id.rbPerfect)
        rbSquare = findViewById(R.id.rbSquare)
        rbFibonacci = findViewById(R.id.rbFibonacci)

        setupRecyclerView()
        setupListeners()

        // Cập nhật danh sách lần đầu
        updateNumberList()
    }

    private fun setupRecyclerView() {
        numberAdapter = NumberAdapter(emptyList())
        rvNumbers.layoutManager = LinearLayoutManager(this)
        rvNumbers.adapter = numberAdapter
    }

    private fun setupListeners() {
        // Lắng nghe thay đổi text trong EditText
        etLimit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateNumberList()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Lắng nghe thay đổi RadioButton
        val radioButtons = listOf(rbOdd, rbEven, rbPrime, rbPerfect, rbSquare, rbFibonacci)
        radioButtons.forEach { rb ->
            rb.setOnClickListener {
                // Tắt hết RadioButton trước khi bật cái mới
                radioButtons.forEach { it.isChecked = false }
                rb.isChecked = true
                updateNumberList()
            }
        }
    }

    private fun updateNumberList() {
        val limitStr = etLimit.text.toString()
        var limit = limitStr.toIntOrNull() ?: 0
        limit = (limit - 1).coerceAtLeast(0)
        val numbers = when {
            rbOdd.isChecked -> (1..limit).filter { it % 2 != 0 }
            rbEven.isChecked -> (1..limit).filter { it % 2 == 0 }
            rbPrime.isChecked -> getPrimes(limit)
            rbPerfect.isChecked -> getPerfects(limit)
            rbSquare.isChecked -> getSquares(limit)
            rbFibonacci.isChecked -> getFibonacci(limit)
            else -> emptyList()
        }

        // Hiển thị kết quả
        if (numbers.isEmpty()) {
            rvNumbers.visibility = View.GONE
            tvNoResult.visibility = View.VISIBLE
        } else {
            rvNumbers.visibility = View.VISIBLE
            tvNoResult.visibility = View.GONE
            numberAdapter.updateData(numbers)
        }
    }

    // --- Các hàm trợ giúp để tạo danh sách số ---

    private fun isPrime(n: Int): Boolean {
        if (n <= 1) return false
        for (i in 2..sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) return false
        }
        return true
    }

    private fun getPrimes(limit: Int): List<Int> {
        return (2..limit).filter { isPrime(it) }
    }

    private fun getSquares(limit: Int): List<Int> {
        val list = mutableListOf<Int>()
        var i = 1
        while (i * i <= limit) {
            list.add(i * i)
            i++
        }
        return list
    }

    private fun getFibonacci(limit: Int): List<Int> {
        if (limit < 1) return emptyList()
        val list = mutableListOf(1)
        var a = 0
        var b = 1
        while (b <= limit) {
            val next = a + b
            a = b
            b = next
            if (b <= limit) {
                list.add(b)
            }
        }
        return list
    }

    private fun getPerfects(limit: Int): List<Int> {
        // Số hoàn hảo rất hiếm, chỉ có vài số nhỏ (6, 28, 496, 8128,...)
        val perfectNumbers = listOf(6, 28, 496, 8128, 33550336)
        return perfectNumbers.filter { it <= limit }
    }
}
