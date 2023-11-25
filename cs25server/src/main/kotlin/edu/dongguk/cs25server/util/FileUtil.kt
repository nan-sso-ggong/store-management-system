package edu.dongguk.cs25server.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.util.*

@Component
class FileUtil {
    @Value("\${file.dir}")
    private val fileDir: String? = null

    // 파일 전체 경로 반환
    private fun getFullPath(fileName: String): String {
        return fileDir + fileName
    }

    // 파일 확장자 반환
    fun getFileExtension(originalFileName: String?): String {
        return originalFileName!!.substring(originalFileName.lastIndexOf('.') + 1)
    }

    // 파일 경로에 저장되는 실제 파일명 반환
    private fun getUuidName(originalFileName: String?): String {
        return UUID.randomUUID().toString() + '.' + getFileExtension(originalFileName)
    }

    // 파일을 파일 경로에 저장
    fun storeFile(file: MultipartFile): String? {
        if (file.isEmpty) {
            return null
        }

        val originalFileName = file.originalFilename
        val uuidFileName = getUuidName(originalFileName)

        try {
            file.transferTo(File(getFullPath(uuidFileName)))
        } catch (e: IOException) {
            return null
        }
        return uuidFileName
    }
}