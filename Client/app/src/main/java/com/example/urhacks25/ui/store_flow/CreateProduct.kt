package com.example.urhacks25.ui.store_flow

import android.content.Context
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.CameraSelector.DEFAULT_FRONT_CAMERA
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OutputFileResults
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.core.takePicture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.urhacks25.components.store_flow.create_product.StoreCreateProductComponent
import com.example.urhacks25.ui.util.ErrorDialog
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import java.io.File
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProduct(component: StoreCreateProductComponent) {
    val scope = rememberCoroutineScope()
    val cxViewModel = viewModel<CxViewModel>()

    val isLoading by component.isLoading.subscribeAsState()
    val canContinue by component.canContinue.subscribeAsState()

    val photoPath by component.photoPath.subscribeAsState()
    val name by component.name.subscribeAsState()
    val price by component.price.subscribeAsState()
    val expiryDate by component.expiryDate.subscribeAsState()
    val error by component.error.subscribeAsState()

    var isCameraSheetActive by remember { mutableStateOf(false) }
    var isDatePickerActive by remember { mutableStateOf(false) }

    ErrorDialog(error, component::dismissError)

    if (isCameraSheetActive) {
        ModalBottomSheet(onDismissRequest = { isCameraSheetActive = false }, contentWindowInsets = {
            WindowInsets(0)
        }, sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true), dragHandle = {

        }) {
            Box(modifier = Modifier.fillMaxSize()) {
                CxPreview(
                    viewModel = cxViewModel,
                    lifecycleOwner = LocalLifecycleOwner.current,
                    modifier = Modifier.fillMaxSize()
                )

                Surface(
                    modifier = Modifier
                        .padding(vertical = 22.dp)
                        .align(Alignment.TopCenter)
                        .statusBarsPadding(),
                    color = Color.White,
                    shape = CircleShape,
                    shadowElevation = 2.dp
                ) {
                    Box(Modifier.size(width = 32.0.dp, height = 4.0.dp))
                }

                val context = LocalContext.current
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            val file = File(context.filesDir, "upload.jpg")
                            if (file.exists()) file.delete()
                            cxViewModel.captureImage(file)
                            component.setPhotoPath(file.absolutePath)
                            isCameraSheetActive = false
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                        .padding(16.dp),
                    contentColor = Color.Black,
                    containerColor = Color.White
                ) {
                    Icon(Icons.Default.Camera, contentDescription = null)
                }
            }
        }
    }

    if (isDatePickerActive) {
        val datePickerState =
            rememberDatePickerState(initialSelectedDateMillis = expiryDate.toEpochMilliseconds())

        val confirmEnabled = remember {
            derivedStateOf {
                datePickerState.selectedDateMillis != null
            }
        }

        DatePickerDialog(
            onDismissRequest = { isDatePickerActive = false },
            confirmButton = {
                TextButton(onClick = {
                    isDatePickerActive = false
                    component.setExpiryDate(Instant.fromEpochMilliseconds(datePickerState.selectedDateMillis!!))
                }, enabled = confirmEnabled.value) {
                    Text("Set")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isDatePickerActive = false
                }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Create Product")
                }, navigationIcon = {
                    IconButton(onClick = component::onBackPressed) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }, bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        enabled = canContinue,
                        onClick = component::dispatchCreation,
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Box(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    AsyncImage(
                        model = if (photoPath.isNotEmpty()) {
                            "file://$photoPath"
                        } else null,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    FilledIconButton(onClick = {
                        isCameraSheetActive = true
                    }, modifier = Modifier.align(Alignment.Center), shape = CircleShape) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null)
                    }
                }
            }

            item {
                OutlinedTextField(
                    shape = MaterialTheme.shapes.medium,
                    maxLines = 1,
                    value = name,
                    onValueChange = component::setName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    placeholder = {
                        Text("Product name")
                    }, keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
            }

            item {
                OutlinedTextField(
                    shape = MaterialTheme.shapes.medium,
                    maxLines = 1,
                    value = price,
                    onValueChange = component::setPrice,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    placeholder = {
                        Text("Price")
                    }, keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ), suffix = {
                        Text("CA$")
                    }
                )
            }

            item {
                val ctx = LocalContext.current

                val pDate = remember(expiryDate) {
                    android.text.format.DateFormat.getDateFormat(ctx)
                        .also { it.timeZone = TimeZone.getTimeZone("UTC") }
                        .format(expiryDate.toEpochMilliseconds()).toString()
                }

                ListItem(
                    headlineContent = {
                        Text("Best Before")
                    }, leadingContent = {
                        Icon(Icons.Default.DateRange, contentDescription = null)
                    }, supportingContent = {
                        Text(pDate)
                    }, trailingContent = {
                        Icon(Icons.Default.Edit, contentDescription = null)
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isDatePickerActive = true
                        }
                )
            }
        }
    }
}

//

@Composable
private fun CxPreview(
    viewModel: CxViewModel,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier
) {
    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    surfaceRequest?.let { request ->
        CameraXViewfinder(
            surfaceRequest = request,
            modifier = modifier
        )
    }
}

class CxViewModel : ViewModel() {
    // Used to set up a link between the Camera and your UI.
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest

    private val cameraPreviewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest ->
            _surfaceRequest.update { newSurfaceRequest }
        }
    }

    private val imageCaptureUseCase = ImageCapture.Builder().apply {

    }.build()

    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)

        processCameraProvider.bindToLifecycle(
            lifecycleOwner, DEFAULT_BACK_CAMERA, cameraPreviewUseCase, imageCaptureUseCase
        )

        // Cancellation signals we're done with the camera
        try {
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
        }
    }

    suspend fun captureImage(file: File): OutputFileResults {
        return imageCaptureUseCase.takePicture(
            outputFileOptions = ImageCapture.OutputFileOptions.Builder(file)
                .build()
        )
    }
}