package com.razbyte.architectureexample

import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {

    single { App() }

}
