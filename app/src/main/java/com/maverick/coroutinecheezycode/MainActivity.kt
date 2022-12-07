package com.maverick.coroutinecheezycode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.maverick.coroutinecheezycode.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MAVERICK2022"
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * flow 1
         */

//        val data = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).asFlow().flowOn(Dispatchers.IO)

//        lifecycleScope.launch {
//            runBlocking {
//                data
//                    .map {
//                        it * it
//                    }
//                    .filter {
//                        it % 2 == 0
//                    }
//                    .collect {
//                        Log.d(TAG, it.toString())
//                        binding.tvNumber.text = it.toString()
//                    }
//            }
//        }

        /**
         * flow 2 - STATE FLOW
         */

        init()
        updateUi()

    }

    /**
     * flow 1
     */
//    private fun getData() = flowOf(1, 2, 3, 4, 5).flowOn(Dispatchers.IO)
////    {
////        for (i in 1..5) {
////            delay(1000L)
////            emit(i)
////        }
////    }.flowOn(Dispatchers.IO)

    /**
     * flow 2 - STATE FLOW
     */

    private fun init() {

        binding.apply {
            btnIncrease.setOnClickListener {
                lifecycleScope.launch {
                    isNetworkAvailable().collect{
                        if (it){
                            mainViewModel.increaseState()
                        }
                    }
                }
            }
            btnDecrease.setOnClickListener { mainViewModel.decreaseState() }
        }

    }

    private fun updateUi() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.counter.collect {
                binding.tvNumber.text = it.toString()
            }
        }
    }
}