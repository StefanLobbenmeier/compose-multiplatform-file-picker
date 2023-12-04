package com.darkrockstudios.libraries.mpfilepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import platform.AppKit.NSOpenPanel
import platform.AppKit.setAllowedFileTypes
import platform.Foundation.NSURL

public data class MacOSFile(
	override val path: String,
	override val platformFile: NSURL,
) : MPFile<NSURL>

@Composable
public actual fun FilePicker(
	show: Boolean, initialDirectory: String?, fileExtensions: List<String>, title: String?, onFileSelected: FileSelected
) {
	LaunchedEffect(show) {
		if (show) {
			with(NSOpenPanel()) {
				if (initialDirectory != null) directoryURL = NSURL.fileURLWithPath(initialDirectory, true)
				allowsMultipleSelection = false
				setAllowedFileTypes(fileExtensions)
				allowsOtherFileTypes = true
				canChooseDirectories = false
				canChooseFiles = true
				runModal()

				val fileURL = URL
				val filePath = fileURL?.path
				if (filePath != null) onFileSelected(MacOSFile(filePath, fileURL))
				else onFileSelected(null)
			}
		}
	}
}

@Composable
public actual fun DirectoryPicker(
	show: Boolean, initialDirectory: String?, title: String?, onFileSelected: (String?) -> Unit,
) {
	LaunchedEffect(show) {
		if (show) {
			with(NSOpenPanel()) {
				if (initialDirectory != null) directoryURL = NSURL.fileURLWithPath(initialDirectory, true)
				allowsMultipleSelection = false
				canChooseDirectories = true
				canChooseFiles = false
				canCreateDirectories = true
				runModal()

				val fileURL = URL
				val filePath = fileURL?.path
				onFileSelected(filePath)
			}
		}
	}
}