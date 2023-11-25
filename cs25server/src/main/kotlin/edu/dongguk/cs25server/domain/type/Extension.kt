package edu.dongguk.cs25server.domain.type

enum class Extension(
    private val extension: String
) {
    PNG("png"), JPG("jpg"), JPEG("jpeg");

    override fun toString(): String {
        return extension
    }
}