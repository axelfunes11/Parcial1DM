package com.primera.evaluacion.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductDetailScreen(
    productId: String,
    modifier: Modifier = Modifier,
    productDetailViewModel: ProductDetailViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {

    val state by productDetailViewModel.state.collectAsState()

    LaunchedEffect(productId) {
        productDetailViewModel.cargarProducto(productId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedButton(
            onClick = onBackClick
        ) {
            Text("← Volver")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        when {

            state.cargando -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error.isNotEmpty() -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            state.producto != null -> {

                val producto = state.producto!!

                Text(
                    text = producto.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        DetailRow(
                            label = "ID",
                            value = producto.id
                        )

                        producto.data?.let { data ->

                            data.color?.let {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                DetailRow(
                                    label = "Color",
                                    value = it
                                )
                            }

                            data.capacity?.let {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                DetailRow(
                                    label = "Capacidad",
                                    value = it
                                )
                            }

                            data.price?.let {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                DetailRow(
                                    label = "Precio",
                                    value = "$$it"
                                )
                            }

                            data.generation?.let {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                DetailRow(
                                    label = "Generación",
                                    value = it
                                )
                            }

                            data.year?.let {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                DetailRow(
                                    label = "Año",
                                    value = it.toString()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}