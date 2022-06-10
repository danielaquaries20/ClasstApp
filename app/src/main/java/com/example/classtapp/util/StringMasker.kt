package com.example.classtapp.util

import android.util.Log
import com.crocodic.core.widget.textdrawable.TextDrawable

class StringMasker {


    fun generateTextDrawable(word: String, color: Int, size: Int): TextDrawable {
        return TextDrawable
            .builder()
            .beginConfig()
            .width(size)
            .height(size)
            .endConfig()
            .buildRound(word, color)
    }

    fun getInitial(name: String?): String {

        Log.d("CheckNamaGetInitial", "Ini Datanya : $name")

        if (name.isNullOrEmpty()) return "-"

        var initial = ""

        val charArray = name.toCharArray()
        var initialAll = ""
        var prev = name[0]

        for (i in charArray.indices) {
            val cur = charArray[i]

            if (cur == ' ' && prev == ' ') {

            } else {
                initialAll += cur.uppercaseChar()
            }
            prev = cur
        }


        for (word in initialAll.split(" ")) {
//            initial += word.uppercase(Locale.getDefault())
            initial += word[0].uppercaseChar()

        }

        if (initial.length > 1) initial = "${initial[0]}${initial[initial.lastIndex]}"

        return if (initial.isEmpty()) "-" else initial
    }

}