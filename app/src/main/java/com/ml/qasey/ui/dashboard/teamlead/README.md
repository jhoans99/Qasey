# 🏢 Team Lead Dashboard

## 📖 Descripción General

El **Team Lead Dashboard** es una pantalla de administración que permite a los líderes de equipo gestionar el estado de activación de los usuarios en el sistema Qasey. Proporciona una interfaz intuitiva para visualizar todos los usuarios y cambiar su estado entre activo e inactivo.

## 🏗️ Arquitectura

### Patrón MVVM
```
📱 UI (Compose) ↔️ ViewModel ↔️ Repository ↔️ DataSource
```

### Componentes Principales

| Componente | Responsabilidad |
|------------|----------------|
| `DashoardTeamLeadScreen.kt` | UI y composables de la pantalla |
| `TeamLeadViewModel.kt` | Lógica de negocio y manejo de estado |
| `DashboardTeamLeadUiState.kt` | Estado de la UI |
| `User.kt` | Modelo de datos del usuario |

## 🎨 Componentes UI

### 1. TeamLeadDashboardRoute
**Propósito**: Punto de entrada principal con manejo de estado de carga.

```kotlin
@Composable
fun TeamLeadDashboardRoute(
    viewModel: TeamLeadViewModel = hiltViewModel()
)
```

**Características**:
- ✅ Inyección automática del ViewModel con Hilt
- ✅ Manejo de estado de carga con `Loader()`
- ✅ Observación reactiva del estado UI

### 2. TeamLeadDashboardScreen
**Propósito**: Estructura base de la pantalla con Scaffold.

```kotlin
@Composable
fun TeamLeadDashboardScreen(uiState: DashboardTeamLeadUiState)
```

**Características**:
- ✅ Layout base con `Scaffold`
- ✅ Padding consistente
- ✅ Estructura responsive

### 3. TeamLeadBody
**Propósito**: Contenido principal con lista de usuarios.

```kotlin
@Composable
fun TeamLeadBody(
    modifier: Modifier,
    uiState: DashboardTeamLeadUiState,
    viewModel: TeamLeadViewModel = hiltViewModel()
)
```

**Características**:
- ✅ Título descriptivo "Usuarios Activos"
- ✅ Lista scrolleable optimizada (`LazyColumn`)
- ✅ Interacción directa con ViewModel

### 4. UserListItem
**Propósito**: Card individual para cada usuario.

```kotlin
@Composable
fun UserListItem(
    user: User,
    modifier: Modifier,
    onChangeStatusCustomer: () -> Unit
)
```

**Características**:
- ✅ Diseño en card con elevación
- ✅ Información del usuario (nombre completo)
- ✅ Botón de acción con iconografía intuitiva
- ✅ Indicadores visuales de estado

## 📊 Modelo de Datos

### User
```kotlin
data class User(
    val Rol: String? = null,
    val lastNames: String? = null,
    val names: String? = null,
    var isEnabled: Boolean? = null,
    val id: String? = null
)
```

### DashboardTeamLeadUiState
```kotlin
data class DashboardTeamLeadUiState(
    val isLoading: Boolean = false,
    val listUsers: List<User> = emptyList()
)
```

## ⚡ Funcionalidades

### 📋 Visualización de Usuarios
- **Lista dinámica**: Muestra todos los usuarios del sistema
- **Información clara**: Nombre completo y estado actual
- **UI responsive**: Se adapta al contenido disponible

### 🔄 Gestión de Estado
- **Activar usuario**: Cambiar de inactivo a activo
- **Desactivar usuario**: Cambiar de activo a inactivo
- **Feedback visual**: Iconos y colores que indican el estado

### 🔄 Estados de Carga
- **Loader**: Indicador visual durante operaciones asíncronas
- **Actualización automática**: Refresco de datos tras cambios

## 🎨 Diseño Visual

### Iconografía
| Estado | Icono | Color | Acción |
|--------|-------|-------|--------|
| Activo | ❌ Close | Rojo | Desactivar |
| Inactivo | ✅ Check | Verde | Activar |

### Layout
- **Padding**: 24dp horizontal, 20dp vertical
- **Spacing**: 10dp entre elementos de lista
- **Elevación**: 2dp para cards
- **Radio**: 8dp para esquinas redondeadas

## 🔧 Configuración Técnica

### Dependencias
```kotlin
// Compose
implementation "androidx.compose.ui:ui"
implementation "androidx.compose.material3:material3"

// ViewModel & Navigation
implementation "androidx.hilt:hilt-navigation-compose"
implementation "androidx.lifecycle:lifecycle-viewmodel-compose"

// Dependency Injection
implementation "com.google.dagger:hilt-android"
```

### Inyección de Dependencias
```kotlin
@HiltViewModel
class TeamLeadViewModel @Inject constructor(
    private val userRepository: UserRepository
)
```

## 🚀 Flujo de Trabajo

### Inicialización
1. El ViewModel se inyecta automáticamente
2. Se ejecuta `getAllUsers()` en el init
3. La UI se renderiza reactivamente

### Cambio de Estado de Usuario
1. Usuario toca el botón de acción
2. Se ejecuta `changeStatusCustomer(id, newStatus)`
3. Se actualiza en el repositorio
4. Se refresca automáticamente la lista

### Manejo de Estados
```kotlin
when(result) {
    is Result.Loading -> // Mostrar loader
    is Result.Success -> // Actualizar UI
    is Result.Error -> // Ocultar loader
}
```

## 📱 UX/UI Consideraciones

### ✅ Buenas Prácticas Implementadas
- **Feedback inmediato**: Loaders durante operaciones
- **Iconografía intuitiva**: Check/Close para acciones
- **Colores semánticos**: Verde/Rojo para estados
- **Responsive design**: Adaptable a diferentes pantallas
- **Accesibilidad**: Content descriptions para iconos

### 🎯 Estados de Usuario
- **Activo**: Puede usar la aplicación normalmente
- **Inactivo**: Acceso restringido o suspendido

## 🔍 Casos de Uso

1. **Supervisor revisa usuarios activos**
   - Entra al dashboard
   - Ve lista completa de usuarios
   - Identifica rápidamente estados

2. **Desactivación de usuario problemático**
   - Localiza usuario en la lista
   - Toca botón rojo (❌)
   - Usuario queda inactivo inmediatamente

3. **Reactivación de usuario**
   - Encuentra usuario inactivo
   - Toca botón verde (✅)
   - Usuario recupera acceso

## 🛠️ Mantenimiento

### Extensibilidad
- Fácil agregar nuevos campos de usuario
- Modular para nuevas funcionalidades
- Arquitectura desacoplada

### Testing
- Componentes aislados testeable
- ViewModels con lógica pura
- UI components sin side effects

## 🔒 Consideraciones de Seguridad

- ✅ Solo Team Leads tienen acceso
- ✅ Validación de permisos en backend
- ✅ Operaciones auditadas
- ✅ Estados consistentes entre cliente/servidor

---

*Esta documentación cubre la implementación completa del Team Lead Dashboard en la aplicación Qasey.* 