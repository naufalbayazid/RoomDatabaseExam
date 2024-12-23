package com.example.roomdatabaseexam.ui.Navigasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roomdatabaseexam.ui.View.Dosen.DestinasiInsert
import com.example.roomdatabaseexam.ui.View.Dosen.HomeDosenView
import com.example.roomdatabaseexam.ui.View.Dosen.InsertDosenView
import com.example.roomdatabaseexam.R
import com.example.roomdatabaseexam.ui.View.Matkul.DestinasiInsertMatkul
import com.example.roomdatabaseexam.ui.View.Matkul.DetailMatkulView
import com.example.roomdatabaseexam.ui.View.Matkul.HomeMatkulView
import com.example.roomdatabaseexam.ui.View.Matkul.InsertMatkul
import com.example.roomdatabaseexam.ui.View.Matkul.UpdateMatkulView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "HalamanMenu") {
        composable(
            route = "HalamanMenu"
        ) {
            HalamanMenu(navController = navController)
        }

        composable(
            route = DestinasiHomeDosen.route
        ) {
            HomeDosenView(
                onAddDsn = {
                    navController.navigate(DestinasiInsert.route)
                },
                modifier = modifier
            )
        }


        composable(
            route = DestinasiInsert.route
        ) {
            InsertDosenView(
                onBack = { navController.popBackStack() }, // Navigasi kembali
                onNavigate = {}
            )
        }

        composable(
            route = DestinasiHomeMatkul.route
        ) {
            HomeMatkulView(
                onDetailClick = { kdMk ->
                    navController.navigate("${DestinasiDetail.route}/$kdMk")
                    println("PengelolaHalaman: kdMk = $kdMk")
                },
                onAddMk = {
                    navController.navigate(DestinasiInsertMatkul.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertMatkul.route
        ) {
            InsertMatkul(
                onBack = { navController.popBackStack() }, // Navigasi kembali
                onNavigate = {}
            )
        }

        composable(
            route = DestinasiDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.KDMK) {
                    type = NavType.StringType
                }
            )
        ) {
            val kdMk = it.arguments?.getString(DestinasiDetail.KDMK)
            kdMk?.let { kdMk ->
                DetailMatkulView(
                    onBack = { navController.popBackStack() },
                    onEditClick = { navController.navigate("${DestinasiUpdate.route}/$it") },
                    onDeleteClick = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        composable(
            DestinasiUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.KDMK) {
                    type = NavType.StringType
                }
            )
        ) {

            UpdateMatkulView(
                // Pass the list to UpdateMatkul
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun HalamanMenu(navController: NavHostController) {
    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            Spacer(modifier = Modifier.height(24.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = androidx.compose.ui.graphics.Color(0xFFF0F0F0))
                    .padding(16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { navController.navigate(DestinasiHomeDosen.route) },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = androidx.compose.ui.graphics.Color.Black
                        )
                    ) {
                        Text(
                            text = "Menu Dosen",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.ui.graphics.Color.White
                        )
                    }
                    Button(
                        onClick = { navController.navigate(DestinasiHomeMatkul.route) },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = androidx.compose.ui.graphics.Color.Red
                        )
                    ) {
                        Text(
                            text = "Menu Mata Kuliah",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.ui.graphics.Color.White
                        )
                    }
                }
            }
        }
    }
}