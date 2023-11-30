package edu.dongguk.cs25server.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import edu.dongguk.cs25server.domain.Image
import edu.dongguk.cs25server.domain.type.Extension
import edu.dongguk.cs25server.domain.type.ImageCategory
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ImageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.util.*

@Component
class FileUtil(
    private val imageRepository: ImageRepository,
    private val amazonS3Client: AmazonS3Client,
    @Value("\${file.dir}")
    private val fileDir: String
) {

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

    fun storeFileS3(file: MultipartFile): String? {
        if (file.isEmpty) {
            return null
        }

        return getUuidName(file.originalFilename)
    }

    // 파일을 삭제
    fun deleteFile(uuidName: String?): Boolean {
        if (uuidName.isNullOrBlank())
            return false

        return File(fileDir + uuidName).delete()
    }

    fun toEntity(imageFile: MultipartFile): Image {
        if (imageFile.isEmpty) {
            throw GlobalException(ErrorCode.EMPTY_IMAGE_ERROR)
        }

        val originName = imageFile.originalFilename
        val extension = getFileExtension(originName)
        val uuidName = storeFile(imageFile) ?: throw GlobalException(ErrorCode.IMAGE_SAVING_ERROR)
        return imageRepository.save(
            Image(
                originName = originName,
                uuidName = uuidName,
                extension = Extension.valueOf(extension.uppercase()),
                imageCategory = ImageCategory.ITEM_HQ,
                accessUrl = ""
            )
        )
    }

    @Transactional
    fun toEntityS3(imageFile: MultipartFile): Image {
        if (imageFile.isEmpty) {
            throw GlobalException(ErrorCode.EMPTY_IMAGE_ERROR)
        }
        val originName = imageFile.originalFilename
        val extension = getFileExtension(originName)
        val uuidName = storeFileS3(imageFile)?:throw GlobalException(ErrorCode.IMAGE_SAVING_ERROR)
        val objectMetadata = ObjectMetadata()

        objectMetadata.contentType = imageFile.contentType
        objectMetadata.contentLength = imageFile.inputStream.available().toLong()

        amazonS3Client.putObject(fileDir, uuidName, imageFile.inputStream, objectMetadata)
        val accessUrl: String = amazonS3Client.getUrl(fileDir, uuidName).toString()

        return imageRepository.save(
            Image(
                originName = originName,
                uuidName = uuidName,
                extension = Extension.valueOf(extension.uppercase()),
                imageCategory = ImageCategory.ITEM_HQ,
                accessUrl = accessUrl
            )
        )
    }
}
