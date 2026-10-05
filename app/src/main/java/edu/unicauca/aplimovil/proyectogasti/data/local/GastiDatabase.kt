package edu.unicauca.aplimovil.proyectogasti.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.GastoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.PresupuestoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.UsuarioDao
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.GastoEntity
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.PresupuestoEntity
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.UsuarioEntity
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.IngresoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.IngresoEntity
@Database(
    entities = [
        UsuarioEntity::class,
        GastoEntity::class,
        PresupuestoEntity::class,
        IngresoEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class GastiDatabase : RoomDatabase() {

    abstract fun gastoDao(): GastoDao
    abstract fun presupuestoDao(): PresupuestoDao
    abstract fun ingresoDao(): IngresoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: GastiDatabase? = null

        fun getDatabase(context: Context): GastiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GastiDatabase::class.java,
                    "gasti_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
