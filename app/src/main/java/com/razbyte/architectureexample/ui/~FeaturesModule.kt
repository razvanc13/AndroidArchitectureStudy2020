package com.razbyte.architectureexample.ui

import com.razbyte.architectureexample.ui.details.DetailsFragment
import com.razbyte.architectureexample.ui.details.DetailsViewModel
import com.razbyte.architectureexample.ui.main.MainFragment
import com.razbyte.architectureexample.ui.main.MainViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val featuresModule: Module = module {

    factory { MainFragment() }
    factory { MainViewModel(get()) }

    factory { DetailsFragment() }
    factory { DetailsViewModel(get()) }

}
