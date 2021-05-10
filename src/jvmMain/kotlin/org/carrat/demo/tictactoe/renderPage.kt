package org.carrat.demo.tictactoe

import kotlinx.html.stream.appendHTML
import org.carrat.demo.tictactoe.site.myDocument
import org.carrat.web.css.render
import java.io.Writer

private const val jsPath = "/static/js.js"

fun renderPage(out : Writer){
    out.append("<!DOCTYPE html>\n")
    val context = rootContext()
    out.appendHTML(prettyPrint = false).render(myDocument, context, jsPath)
}