package edu.dongguk.cs25server.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Log {
    companion object{
        val <reified T> T.log: Logger
            inline get() = LoggerFactory.getLogger(T::class.java)
    }
}