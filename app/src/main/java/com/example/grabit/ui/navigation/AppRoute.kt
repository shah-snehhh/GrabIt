package com.example.grabit.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppRoute {
    val arguments: List<NamedNavArgument>
    val path: String
}

object AppDestinations {

    val intro = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "intro"
    }
    val login = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "login"
    }
    val signup = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "signup"
    }

    val home = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "home"
    }

    val dashboard = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "dashboard"
    }

    val favorites = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "favorites"
    }

    val profile = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "profile"
    }

    val settings = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "settings"
    }

    val cart = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "cart"
    }

    val search = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "search"
    }

    //parameter can have value or can be null as well
    val productList = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "product-list"
    }

    val orderList = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "order-list"
    }

    object ProductDetail {
        const val KEY_PRODUCT_ID = "productId"

        private const val PATH = "productDetail"
        const val path = "$PATH/{$KEY_PRODUCT_ID}"

        fun setArgs(productId: Int) = object : AppRoute {

            override val arguments = listOf(
                navArgument(KEY_PRODUCT_ID) { type = NavType.IntType }
            )

            override val path = "$PATH/$productId"
        }
    }

    val notification = object : AppRoute {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val path: String = "notification"
    }
}
