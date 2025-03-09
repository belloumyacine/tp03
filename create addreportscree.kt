package com.example.imatah.presentation

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.imatah.presentation.viewmodel.AddReportViewModel
import com.example.imatah.presentation.viewmodel.AddReportEvent

@Composable
fun AddReportScreen(
    navController: NavController,
    viewModel: AddReportViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        uri?.let { viewModel.onEvent(AddReportEvent.ImageUrlChanged(it.toString())) }
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: ImageBitmap? ->
        bitmap?.let {
            // Handle the bitmap and convert it to a Uri if needed
            // For simplicity, we assume the bitmap is converted to a Uri and set it
            // imageUri = convertedUri
            // viewModel.onEvent(AddReportEvent.ImageUrlChanged(convertedUri.toString()))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = state.title,
            onValueChange = { viewModel.onEvent(AddReportEvent.TitleChanged(it)) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = state.description,
            onValueChange = { viewModel.onEvent(AddReportEvent.DescriptionChanged(it)) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = state.category,
            onValueChange = { viewModel.onEvent(AddReportEvent.CategorySelected(it)) },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val gmmIntentUri = Uri.parse("geo:0,0?q=")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            navController.context.startActivity(mapIntent)
        }) {
            Text("Select Location on Map")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("Select from Gallery")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { cameraLauncher.launch(null) }) {
                Text("Take Photo")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        imageUri?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = state.status,
            onValueChange = { viewModel.onEvent(AddReportEvent.StatusChanged(it)) },
            label = { Text("Status") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.onEvent(AddReportEvent.Submit) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        if (state.error != null) {
            Text(state.error, color = MaterialTheme.colors.error)
        }
        if (state.isSuccess) {
            Text("Report added successfully!", color = MaterialTheme.colors.primary)
        }
    }
}
