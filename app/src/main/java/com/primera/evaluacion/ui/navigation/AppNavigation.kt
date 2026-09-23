package com.primera.evaluacion.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.primera.evaluacion.ui.detail.ProductDetailScreen
import com.primera.evaluacion.ui.login.LoginScreen
import com.primera.evaluacion.ui.product.ProductListScreen

object AppRoutes {

    const val LOGIN = "login"
    const val PRODUCT_LIST = "product_list"
    const val PRODUCT_DETAIL = "product_detail/{productId}"

    fun productDetailRoute(productId: String): String {
        return "product_detail/$productId"
    }
}

@Composable
fun AppNavigator(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = AppRoutes.LOGIN,
        modifier = modifier
    ) {

        composable(AppRoutes.LOGIN) {

            LoginScreen(
                onLoginSuccess = {

                    navController.navigate(
                        AppRoutes.PRODUCT_LIST
                    ) {

                        popUpTo(AppRoutes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }


        composable(AppRoutes.PRODUCT_LIST) {

            ProductListScreen(

                onProductClick = { productId ->

                    navController.navigate(
                        AppRoutes.productDetailRoute(productId)
                    )
                },

                onLogoutClick = {

                    navController.navigate(
                        AppRoutes.LOGIN
                    ) {

                        popUpTo(AppRoutes.PRODUCT_LIST) {
                            inclusive = true
                        }
                    }
                }
            )
        }


        composable(AppRoutes.PRODUCT_DETAIL) { backStackEntry ->

            val productId =
                backStackEntry.arguments
                    ?.getString("productId")
                    ?: ""

            ProductDetailScreen(
                productId = productId,

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}