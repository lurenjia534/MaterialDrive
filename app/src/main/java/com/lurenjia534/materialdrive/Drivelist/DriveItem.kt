package com.lurenjia534.materialdrive.Drivelist

data class DriveItem(
    val id: String,
    val name: String,
    val createdDateTime: String,
    val lastModifiedDateTime: String,
    val createdBy: User,
    val lastModifiedBy: User,
    val parentReference: ParentReference,
    val webUrl: String,
    val fileSystemInfo: FileSystemInfo,
    val folder: Folder?,
    val size: Long
)

data class User(
    val email: String,
    val id: String,
    val displayName: String
)

data class ParentReference(
    val driveType: String,
    val driveId: String,
    val id: String,
    val name: String,
    val path: String,
    val siteId: String
)

data class FileSystemInfo(
    val createdDateTime: String,
    val lastModifiedDateTime: String
)

data class Folder(
    val childCount: Int
)

data class DriveItemsResponse(
    val value: List<DriveItem>
)
