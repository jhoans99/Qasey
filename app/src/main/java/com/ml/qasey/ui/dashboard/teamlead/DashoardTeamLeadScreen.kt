/**
 * Team Lead Dashboard - Pantalla de gestión de usuarios para líderes de equipo
 * 
 * Esta pantalla permite a los Team Leads visualizar todos los usuarios del sistema
 * y gestionar su estado de activación (activo/inactivo). Implementa el patrón MVVM
 * con Jetpack Compose para una UI reactiva y moderna.
 * 
 * @author Qasey Team
 * @since 1.0.0
 */

package com.ml.qasey.ui.dashboard.teamlead

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ml.qasey.model.response.User
import com.ml.qasey.ui.components.Loader

/**
 * Punto de entrada principal del Dashboard del Team Lead
 * 
 * Este composable maneja la inyección del ViewModel y el estado de carga global.
 * Actúa como el contenedor principal que coordina entre el ViewModel y la UI.
 * 
 * Características:
 * - Inyección automática del ViewModel con Hilt
 * - Observación reactiva del estado UI
 * - Manejo centralizado del estado de carga
 * 
 * @param viewModel ViewModel inyectado automáticamente por Hilt que maneja la lógica de negocio
 */
@Composable
fun TeamLeadDashboardRoute(
    viewModel: TeamLeadViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> Loader()
    }

    TeamLeadDashboardScreen(uiState)
}

/**
 * Estructura principal de la pantalla del Dashboard
 * 
 * Implementa el layout base usando Scaffold de Material Design 3,
 * proporcionando una estructura consistente con padding automático
 * para barras de sistema y navegación.
 * 
 * Características:
 * - Layout responsive que se adapta al contenido
 * - Padding consistente siguiendo Material Design
 * - Estructura base para el contenido principal
 * 
 * @param uiState Estado actual de la UI que contiene la lista de usuarios y estado de carga
 */
@Composable
fun TeamLeadDashboardScreen(uiState: DashboardTeamLeadUiState) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            TeamLeadBody(
                Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 20.dp),
                uiState
            )
        }
    }
}

/**
 * Contenido principal del Dashboard del Team Lead
 * 
 * Renderiza el título principal y la lista scrolleable de usuarios.
 * Maneja la interacción directa con el ViewModel para cambios de estado.
 * 
 * Funcionalidades:
 * - Muestra título descriptivo "Usuarios Activos"
 * - Lista optimizada con LazyColumn para rendimiento
 * - Gestión de clics para cambiar estado de usuarios
 * - Espaciado consistente entre elementos
 * 
 * @param modifier Modificador para styling y layout
 * @param uiState Estado actual con la lista de usuarios
 * @param viewModel ViewModel para ejecutar acciones de negocio
 */
@Composable
fun TeamLeadBody(
    modifier: Modifier,
    uiState: DashboardTeamLeadUiState,
    viewModel: TeamLeadViewModel = hiltViewModel()
    ) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Título principal de la sección
        Text(
            text = "Usuarios Activos",
            style = MaterialTheme.typography.titleMedium
        )

        // Lista scrolleable optimizada de usuarios
        LazyColumn {
            items(uiState.listUsers) { user ->
                UserListItem(
                    user = user, 
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                ) {
                    // Cambiar estado: si está activo lo desactiva, si está inactivo lo activa
                    viewModel.changeStatusCustomer(user.id ?: "", !user.isEnabled!!)
                }
            }
        }
    }
}

/**
 * Componente de Card individual para mostrar información de usuario
 * 
 * Renderiza un elemento de lista que muestra la información del usuario
 * y permite cambiar su estado de activación con feedback visual intuitivo.
 * 
 * Características de Diseño:
 * - Card con elevación y esquinas redondeadas
 * - Layout horizontal con información y acción
 * - Iconografía semántica (Check/Close)
 * - Colores que indican estado (Verde/Rojo)
 * - Indicador textual del estado actual
 * 
 * UX/UI:
 * - Botón claramente identificable para la acción
 * - Feedback visual inmediato del estado
 * - Diseño consistente con Material Design 3
 * 
 * @param user Objeto User con toda la información del usuario
 * @param modifier Modificador para customizar layout y styling
 * @param onChangeStatusCustomer Callback que se ejecuta al tocar el botón de cambio de estado
 */
@Composable
fun UserListItem(
    user: User,
    modifier: Modifier,
    onChangeStatusCustomer: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(5.dp)
        ) {
            // Fila principal con nombre de usuario y botón de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Nombre completo del usuario
                Text(
                    text = "${user.names} ${user.lastNames}"
                )

                // Botón de acción para cambiar estado
                IconButton(
                    onClick = {
                        onChangeStatusCustomer()
                    }
                ) {
                    Icon(
                        imageVector = if(user.isEnabled == true) {
                            Icons.Default.Close  // X roja para desactivar
                        } else {
                            Icons.Default.Check  // Check verde para activar
                        },
                        contentDescription = if(user.isEnabled == true) {
                            "Desactivar usuario"
                        } else {
                            "Activar usuario"
                        },
                        tint = if(user.isEnabled == true) {
                            Color.Red    // Rojo para acción de desactivar
                        } else {
                            Color.Green  // Verde para acción de activar
                        }
                    )
                }
            }

            // Indicador textual del estado actual
            Text(
                text = if(user.isEnabled == true) "Activo" else "Inactivo"
            )
        }
    }
}

/**
 * ═══════════════════════════════════════════════════════════════════════════════════
 * 📋 RESUMEN DE FUNCIONALIDADES DEL TEAM LEAD DASHBOARD
 * ═══════════════════════════════════════════════════════════════════════════════════
 * 
 * Este archivo implementa la pantalla completa del Dashboard para Team Leads con:
 * 
 * 🏗️ ARQUITECTURA:
 * - Patrón MVVM con separación clara de responsabilidades
 * - UI reactiva con StateFlow y Jetpack Compose
 * - Inyección de dependencias con Hilt
 * 
 * 🎨 COMPONENTES UI:
 * - TeamLeadDashboardRoute: Punto de entrada con manejo de estados
 * - TeamLeadDashboardScreen: Estructura base con Scaffold
 * - TeamLeadBody: Contenido principal con lista de usuarios
 * - UserListItem: Card individual para cada usuario
 * 
 * ⚡ FUNCIONALIDADES:
 * - Visualización de todos los usuarios del sistema
 * - Cambio de estado de usuarios (activo/inactivo)
 * - Feedback visual inmediato
 * - Estados de carga con loader
 * 
 * 🎯 UX/UI:
 * - Diseño Material Design 3
 * - Iconografía intuitiva (Check/Close)
 * - Colores semánticos (Verde/Rojo)
 * - Accesibilidad con content descriptions
 * 
 * 🔄 FLUJO DE DATOS:
 * 1. ViewModel obtiene usuarios al inicializar
 * 2. UI se actualiza reactivamente
 * 3. Acciones del usuario se envían al ViewModel
 * 4. Cambios se persisten y actualizan automáticamente
 * 
 * ═══════════════════════════════════════════════════════════════════════════════════
 */



