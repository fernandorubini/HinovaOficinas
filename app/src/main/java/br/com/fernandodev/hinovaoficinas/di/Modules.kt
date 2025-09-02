package br.com.fernandodev.hinovaoficinas.di

import android.content.pm.ApplicationInfo
import br.com.fernandodev.hinovaoficinas.core.database.AppDatabase
import br.com.fernandodev.hinovaoficinas.core.database.DatabaseProvider
import br.com.fernandodev.hinovaoficinas.core.database.dao.CheckinPhotoDao
import br.com.fernandodev.hinovaoficinas.core.database.dao.PaymentDao
import br.com.fernandodev.hinovaoficinas.core.database.dao.ServiceOrderDao
import br.com.fernandodev.hinovaoficinas.core.database.dao.ServiceOrderItemDao
import br.com.fernandodev.hinovaoficinas.core.network.HinovaApi
import br.com.fernandodev.hinovaoficinas.core.network.HinovaWebRepository
import br.com.fernandodev.hinovaoficinas.data.repository.CustomerRepository
import br.com.fernandodev.hinovaoficinas.data.repository.CustomerRepositoryImpl
import br.com.fernandodev.hinovaoficinas.data.repository.ServiceCatalogRepository
import br.com.fernandodev.hinovaoficinas.data.repository.ServiceCatalogRepositoryImpl
import br.com.fernandodev.hinovaoficinas.data.repository.ServiceOrderRepositoryImpl
import br.com.fernandodev.hinovaoficinas.data.repository.StockRepository
import br.com.fernandodev.hinovaoficinas.data.repository.StockRepositoryImpl
import br.com.fernandodev.hinovaoficinas.data.repository.VehicleRepository
import br.com.fernandodev.hinovaoficinas.data.repository.VehicleRepositoryImpl
import br.com.fernandodev.hinovaoficinas.domain.repository.ServiceOrderRepository
import br.com.fernandodev.hinovaoficinas.domain.usecase.GetRevenueByPeriodUseCase
import br.com.fernandodev.hinovaoficinas.domain.usecase.ObservePaymentsByOrderUseCase
import br.com.fernandodev.hinovaoficinas.ui.os.OSViewModel
import br.com.fernandodev.hinovaoficinas.ui.oficinas.OficinasViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/* -------------------- Database + DAOs -------------------- */
val databaseModule = module {
    single<AppDatabase> { DatabaseProvider.get(androidContext()) }
    single<CheckinPhotoDao> { get<AppDatabase>().checkinPhotoDao() }

    // DAOs expostos pelo AppDatabase atual
    single<ServiceOrderDao> { get<AppDatabase>().serviceOrderDao() }
    single<ServiceOrderItemDao> { get<AppDatabase>().serviceOrderItemDao() }
    single<PaymentDao> { get<AppDatabase>().paymentDao() }
}

/* -------------------- Network -------------------- */
private const val BASE_URL =
    "http://app.hinovamobile.com.br/ProvaConhecimentoWebApi/"

val networkModule = module {
    single<Gson> {
        GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd")
            .create()
    }

    single {
        val isDebug =
            (androidContext().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0

        val logging = HttpLoggingInterceptor().apply {
            level = if (isDebug) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.BASIC
        }

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single<HinovaApi> { get<Retrofit>().create(HinovaApi::class.java) }

    // Repositório web que consome a API (Oficinas, Indicação, etc.)
    single { HinovaWebRepository(api = get()) }
}

/* -------------------- Repositories -------------------- */
val repositoryModule = module {
    single<ServiceOrderRepository> {
        ServiceOrderRepositoryImpl(
            orderDao = get(),
            itemDao = get(),
            paymentDao = get()
        )
    }
    // Os abaixo permanecem se você ainda os utiliza em outras telas
    single<CustomerRepository> { CustomerRepositoryImpl(get()) }
    single<VehicleRepository> { VehicleRepositoryImpl(get()) }
    single<ServiceCatalogRepository> { ServiceCatalogRepositoryImpl(get()) }
    single<StockRepository> { StockRepositoryImpl(get()) }
}

/* -------------------- UseCases -------------------- */
val useCaseModule = module {
    single { ObservePaymentsByOrderUseCase(get<ServiceOrderRepository>()) }
    single { GetRevenueByPeriodUseCase(get<ServiceOrderRepository>()) }
}

/* -------------------- ViewModels -------------------- */
val viewModelModule = module {
    viewModel { OSViewModel(get()) }
    // VM para a tela de Oficinas (consome HinovaWebRepository)
    viewModel { OficinasViewModel(get()) }
}

/* -------------------- Aggregator -------------------- */
val appModules = listOf(
    databaseModule,
    networkModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)
