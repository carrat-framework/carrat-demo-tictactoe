package org.carrat.demo.tictactoe.site

import kotlinx.html.DIV
import kotlinx.html.NOSCRIPT
import kotlinx.html.br
import org.carrat.demo.tictactoe.style.LoadingStyleSheet.css
import org.carrat.demo.tictactoe.style.LoadingStyleSheet.loadingBoxStyle
import org.carrat.demo.tictactoe.style.LoadingStyleSheet.loadingOverlayStyle
import org.carrat.web.builder.CBlock
import org.carrat.web.builder.css
import org.carrat.web.builder.dynamic
import org.carrat.web.builder.tag

val loadingOverlay: CBlock = {
    dynamic(isLoading) {
        if(it) {
            tag(::DIV) {
                css {
                    classes += loadingOverlayStyle()
                }
                tag(::DIV) {
                    css {
                        classes += loadingBoxStyle()
                    }
                    +"Loading..."
//            tag(::NOSCRIPT) {
                br()
                +"This site requires JavaScript"
//            }
                }
            }
        }
    }
}

