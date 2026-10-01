package edu.unicauca.aplimovil.proyectogasti.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.GastoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.dao.PresupuestoDao
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.GastoEntity
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.PresupuestoEntity
import edu.unicauca.aplimovil.proyectogasti.data.local.entity.UsuarioEntity

@Database(
    entities = [UsuarioEntity::class, GastoEntity::class, PresupuestoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GastiDatabase : RoomDatabase() {

    abstract fun gastoDao(): GastoDao
    abstract fun presupuestoDao(): PresupuestoDao

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
