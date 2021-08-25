package org.carrat.demo.tictactoe.site

import org.carrat.demo.tictactoe.style.LoadingStyleSheet.css
import org.carrat.demo.tictactoe.style.LoadingStyleSheet.loadingBoxStyle
import org.carrat.demo.tictactoe.style.LoadingStyleSheet.loadingOverlayStyle
import org.carrat.web.builder.html.BlockContent
import org.carrat.web.builder.html.br
import org.carrat.web.builder.html.div
import org.carrat.web.builder.html.dynamic

val loadingOverlay: BlockContent = {
    dynamic(isLoading) {
        if (it) {
            div {
                css {
                    classes += loadingOverlayStyle()
                }
                div {
                    css {
                        classes += loadingBoxStyle()
                    }
                    +"Loading..."
//            tag(::NOSCRIPT) {
                    br {}
                    +"This site requires JavaScript"
//            }
                }
            }
        }
    }
}

