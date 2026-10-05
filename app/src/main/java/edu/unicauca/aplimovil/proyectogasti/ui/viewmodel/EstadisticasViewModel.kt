package edu.unicauca.aplimovil.proyectogasti.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.GastoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.GastoEntity
import edu.unicauca.aplimovil.proyectogasti.data.repository.PresupuestoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import java.util.Locale

// =============================================================
// PERIODOS
// =============================================================

enum class PeriodoEstadisticas {

    SEMANA,

    MES,

    ANIO;

    val titulo: String
        get() {
            return when (this) {

                SEMANA ->
                    "Semana actual"

                MES ->
                    "Mes actual"

                ANIO ->
                    "Año actual"
            }
        }
}


// =============================================================
// CATEGORIA
// =============================================================

data class CategoriaEstadistica(
    val nombre: String,
    val monto: Double
) {

    val porcentaje: Float
        get() = 0f
}


// =============================================================
// PUNTO GRAFICA DIARIA
// =============================================================

data class PuntoGraficaDiaria(
    val etiqueta: String,
    val monto: Double
)


// =============================================================
// PUNTO GRAFICA MENSUAL
// =============================================================

data class PuntoGraficaMensual(
    val etiqueta: String,
    val monto: Double
)


// =============================================================
// ESTADO DE LA PANTALLA
// =============================================================

data class EstadisticasUiState(

    val periodoSeleccionado:
    PeriodoEstadisticas =
        PeriodoEstadisticas.MES,

    val totalGastado: Double = 0.0,

    val presupuestoTotal: Double = 0.0,

    val porcentajePresupuesto: Float = 0f,

    val gastosPorCategoria:
    List<CategoriaEstadistica> =
        emptyList(),

    val gastosDelPeriodo:
    List<GastoEntity> =
        emptyList(),

    val graficaDiaria:
    List<PuntoGraficaDiaria> =
        emptyList(),

    val graficaMensual:
    List<PuntoGraficaMensual> =
        emptyList(),

    val mayorCategoria:
    CategoriaEstadistica? =
        null
)


// =============================================================
// VIEWMODEL
// =============================================================

/**
 * ViewModel encargado del análisis estadístico de gastos y presupuesto.
 * Filtra gastos por periodo (semana, mes, año), calcula porcentajes de ejecución presupuestal,
 * agrupa gastos por categoría, genera series de datos para gráficas y detecta insights.
 */
class EstadisticasViewModel(
    private val gastoDao: GastoDao,
    private val presupuestoRepository:
    PresupuestoRepository,
    private val correoUsuario: String,
    private val mesAnio: String
) : ViewModel() {

    // ---------------------------------------------------------
    // PERIODO SELECCIONADO
    // ---------------------------------------------------------

    private val _periodoSeleccionado =
        MutableStateFlow(
            PeriodoEstadisticas.MES
        )

    // ---------------------------------------------------------
    // GASTOS DEL USUARIO
    // ---------------------------------------------------------

    private val gastosFlow =
        gastoDao.obtenerGastosPorUsuario(
            correoUsuario
        )

    // ---------------------------------------------------------
    // PRESUPUESTO DEL MES ACTUAL
    // ---------------------------------------------------------

    private val presupuestoFlow =
        presupuestoRepository
            .obtenerSumaPresupuestos(
                correoUsuario,
                mesAnio
            )

    // ---------------------------------------------------------
    // ESTADO FINAL
    // ---------------------------------------------------------

    val uiState:
            StateFlow<EstadisticasUiState> =

        combine(
            gastosFlow,
            presupuestoFlow,
            _periodoSeleccionado
        ) { gastos, presupuesto, periodo ->

            calcularEstado(
                gastos = gastos,
                presupuesto = presupuesto ?: 0.0,
                periodo = periodo
            )

        }.stateIn(
            scope = viewModelScope,
            started =
                SharingStarted.WhileSubscribed(
                    5000
                ),
            initialValue =
                EstadisticasUiState()
        )


    // =========================================================
    // CAMBIAR PERIODO
    // =========================================================

    fun seleccionarPeriodo(
        periodo: PeriodoEstadisticas
    ) {

        _periodoSeleccionado.value =
            periodo
    }


    // =========================================================
    // CALCULAR ESTADISTICAS
    // =========================================================

    /**
     * Calcula dinámicamente las métricas de gasto y presupuesto:
     * - Filtra gastos según el periodo seleccionado.
     * - Suma el gasto total y determina el porcentaje de uso del presupuesto.
     * - Agrupa gastos por categoría y determina la categoría con mayor egreso.
     * - Construye los datos para las gráficas diaria y comparativa mensual.
     */
    private fun calcularEstado(
        gastos: List<GastoEntity>,
        presupuesto: Double,
        periodo: PeriodoEstadisticas
    ): EstadisticasUiState {

        // -----------------------------------------------------
        // FILTRAR GASTOS
        // -----------------------------------------------------

        val gastosPeriodo =
            gastos.filter { gasto ->

                perteneceAlPeriodo(
                    fecha = gasto.fecha,
                    periodo = periodo
                )
            }

        // -----------------------------------------------------
        // TOTAL
        // -----------------------------------------------------

        val totalGastado =
            gastosPeriodo.sumOf {
                it.monto
            }

        // -----------------------------------------------------
        // PORCENTAJE DEL PRESUPUESTO
        // -----------------------------------------------------

        val porcentajePresupuesto =

            if (presupuesto > 0) {

                (
                        totalGastado /
                                presupuesto
                        ) * 100.0

            } else {

                0.0
            }

        // -----------------------------------------------------
        // CATEGORIAS
        // -----------------------------------------------------

        val categorias =
            gastosPeriodo
                .groupBy {
                    it.categoria
                }
                .map { (categoria, lista) ->

                    CategoriaEstadistica(
                        nombre = categoria,
                        monto =
                            lista.sumOf {
                                it.monto
                            }
                    )
                }
                .sortedByDescending {
                    it.monto
                }

        // -----------------------------------------------------
        // MAYOR CATEGORIA
        // -----------------------------------------------------

        val mayorCategoria =
            categorias.firstOrNull()

        // -----------------------------------------------------
        // GRAFICA DIARIA
        // -----------------------------------------------------

        val graficaDiaria =
            construirGraficaDiaria(
                gastosPeriodo
            )

        // -----------------------------------------------------
        // GRAFICA MENSUAL
        // -----------------------------------------------------

        val graficaMensual =
            construirGraficaMensual(
                gastos
            )

        // -----------------------------------------------------
        // ESTADO
        // -----------------------------------------------------

        return EstadisticasUiState(

            periodoSeleccionado =
                periodo,

            totalGastado =
                totalGastado,

            presupuestoTotal =
                presupuesto,

            porcentajePresupuesto =
                porcentajePresupuesto
                    .coerceIn(
                        0.0,
                        100.0
                    )
                    .toFloat(),

            gastosPorCategoria =
                categorias,

            gastosDelPeriodo =
                gastosPeriodo,

            graficaDiaria =
                graficaDiaria,

            graficaMensual =
                graficaMensual,

            mayorCategoria =
                mayorCategoria
        )
    }


    // =========================================================
    // FILTRAR POR PERIODO
    // =========================================================

    private fun perteneceAlPeriodo(
        fecha: String,
        periodo: PeriodoEstadisticas
    ): Boolean {

        val calendario =
            convertirFecha(fecha)
                ?: return false

        val hoy =
            Calendar.getInstance()

        return when (periodo) {

            // -------------------------------------------------
            // SEMANA
            // -------------------------------------------------

            PeriodoEstadisticas.SEMANA -> {

                val inicioSemana =
                    hoy.clone() as Calendar

                inicioSemana.set(
                    Calendar.DAY_OF_WEEK,
                    Calendar.MONDAY
                )

                inicioSemana.set(
                    Calendar.HOUR_OF_DAY,
                    0
                )

                inicioSemana.set(
                    Calendar.MINUTE,
                    0
                )

                inicioSemana.set(
                    Calendar.SECOND,
                    0
                )

                inicioSemana.set(
                    Calendar.MILLISECOND,
                    0
                )

                val finSemana =
                    inicioSemana.clone()
                            as Calendar

                finSemana.add(
                    Calendar.DAY_OF_YEAR,
                    6
                )

                finSemana.set(
                    Calendar.HOUR_OF_DAY,
                    23
                )

                finSemana.set(
                    Calendar.MINUTE,
                    59
                )

                finSemana.set(
                    Calendar.SECOND,
                    59
                )

                !calendario.before(
                    inicioSemana
                ) &&
                        !calendario.after(
                            finSemana
                        )
            }

            // -------------------------------------------------
            // MES
            // -------------------------------------------------

            PeriodoEstadisticas.MES -> {

                calendario.get(
                    Calendar.MONTH
                ) ==
                        hoy.get(
                            Calendar.MONTH
                        ) &&

                        calendario.get(
                            Calendar.YEAR
                        ) ==
                        hoy.get(
                            Calendar.YEAR
                        )
            }

            // -------------------------------------------------
            // AÑO
            // -------------------------------------------------

            PeriodoEstadisticas.ANIO -> {

                calendario.get(
                    Calendar.YEAR
                ) ==
                        hoy.get(
                            Calendar.YEAR
                        )
            }
        }
    }


    // =========================================================
    // CONVERTIR FECHA
    // =========================================================

    private fun convertirFecha(
        fecha: String
    ): Calendar? {

        return try {

            /*
             * La aplicación guarda las fechas
             * con un formato parecido a:
             *
             * 28 de septiembre de 2026
             */

            val texto =
                fecha
                    .trim()
                    .lowercase(
                        Locale("es", "ES")
                    )

            val partes =
                texto.split(" de ")

            if (partes.size != 3) {
                return null
            }

            val dia =
                partes[0]
                    .trim()
                    .toIntOrNull()
                    ?: return null

            val mesTexto =
                partes[1]
                    .trim()

            val anio =
                partes[2]
                    .trim()
                    .toIntOrNull()
                    ?: return null

            val meses =
                listOf(
                    "enero",
                    "febrero",
                    "marzo",
                    "abril",
                    "mayo",
                    "junio",
                    "julio",
                    "agosto",
                    "septiembre",
                    "octubre",
                    "noviembre",
                    "diciembre"
                )

            val numeroMes =
                meses.indexOf(
                    mesTexto
                )

            if (numeroMes < 0) {
                return null
            }

            Calendar.getInstance().apply {

                clear()

                set(
                    Calendar.YEAR,
                    anio
                )

                set(
                    Calendar.MONTH,
                    numeroMes
                )

                set(
                    Calendar.DAY_OF_MONTH,
                    dia
                )

                set(
                    Calendar.HOUR_OF_DAY,
                    12
                )
            }

        } catch (
            e: Exception
        ) {

            null
        }
    }


    // =========================================================
    // GRAFICA DIARIA
    // =========================================================

    private fun construirGraficaDiaria(
        gastos: List<GastoEntity>
    ): List<PuntoGraficaDiaria> {

        val agrupados =
            gastos
                .mapNotNull { gasto ->

                    val fecha =
                        convertirFecha(
                            gasto.fecha
                        )

                    if (fecha == null) {
                        null
                    } else {

                        val dia =
                            fecha.get(
                                Calendar.DAY_OF_MONTH
                            )

                        dia to gasto.monto
                    }
                }
                .groupBy {
                    it.first
                }
                .mapValues { (_, valores) ->

                    valores.sumOf {
                        it.second
                    }
                }

        return agrupados
            .toList()
            .sortedBy {
                it.first
            }
            .map { (dia, total) ->

                PuntoGraficaDiaria(
                    etiqueta = dia.toString(),
                    monto = total
                )
            }
    }


    // =========================================================
    // GRAFICA MENSUAL
    // =========================================================

    private fun construirGraficaMensual(
        gastos: List<GastoEntity>
    ): List<PuntoGraficaMensual> {

        val meses = listOf(
            "Ene",
            "Feb",
            "Mar",
            "Abr",
            "May",
            "Jun",
            "Jul",
            "Ago",
            "Sep",
            "Oct",
            "Nov",
            "Dic"
        )

        val agrupados =
            gastos
                .mapNotNull { gasto ->

                    val fecha =
                        convertirFecha(
                            gasto.fecha
                        )

                    if (fecha == null) {
                        null
                    } else {

                        val anio =
                            fecha.get(
                                Calendar.YEAR
                            )

                        val mes =
                            fecha.get(
                                Calendar.MONTH
                            )

                        Triple(
                            anio,
                            mes,
                            gasto.monto
                        )
                    }
                }
                .groupBy {
                    Pair(
                        it.first,
                        it.second
                    )
                }
                .mapValues { (_, valores) ->

                    valores.sumOf {
                        it.third
                    }
                }

        return agrupados
            .toList()
            .sortedBy {
                it.first.first * 12 +
                        it.first.second
            }
            .takeLast(4)
            .map { (periodo, total) ->

                PuntoGraficaMensual(

                    etiqueta =
                        meses[
                            periodo.second
                        ],

                    monto =
                        total
                )
            }
    }


    // =========================================================
    // FACTORY
    // =========================================================

    class Factory(
        private val gastoDao: GastoDao,
        private val presupuestoRepository:
        PresupuestoRepository,
        private val correoUsuario: String,
        private val mesAnio: String
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {

            if (
                modelClass.isAssignableFrom(
                    EstadisticasViewModel::class.java
                )
            ) {

                return EstadisticasViewModel(
                    gastoDao =
                        gastoDao,

                    presupuestoRepository =
                        presupuestoRepository,

                    correoUsuario =
                        correoUsuario,

                    mesAnio =
                        mesAnio

                ) as T
            }

            throw IllegalArgumentException(
                "Unknown ViewModel class"
            )
        }
    }
}