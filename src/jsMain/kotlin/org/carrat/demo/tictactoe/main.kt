package org.carrat.demo.tictactoe

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.carrat.demo.tictactoe.site.isLoading
import org.carrat.demo.tictactoe.site.myDocument
import org.carrat.web.css.hydrate
import org.carrat.web.css.render
import org.carrat.web.webapi.document
import org.carrat.web.webapi.window

fun main() {
    window.addEventListener("DOMContentLoaded", { load() })
}

private fun load() {
    GlobalScope.launch {
        if (isPrerendered()) {
            document.hydrate(myDocument, rootContext)
        } else {
            document.render(myDocument, rootContext)
        }
        rootContext.isLoading.value = false
    }
}

private fun isPrerendered() = document.body?.attributes?.getNamedItem("prerendered")?.value != "false"
