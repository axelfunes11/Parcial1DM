package com.primera.evaluacion.ui.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    productListViewModel: ProductListViewModel = viewModel(),
    onProductClick: (String) -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {

    val state by productListViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        productListViewModel.cargarProductos()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Productos",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            OutlinedButton(
                onClick = onLogoutClick
            ) {
                Text("Salir")
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
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

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = state.error,
                            color = MaterialTheme.colorScheme.error
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Button(
                            onClick = {
                                productListViewModel.cargarProductos()
                            }
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            else -> {

                LazyColumn(
                    verticalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    items(state.productos) { producto ->

                        ProductCard(
                            producto = producto,
                            onClick = {
                                onProductClick(producto.id)
                            }
                        )
                    }
                }
            }
        }
    }
}