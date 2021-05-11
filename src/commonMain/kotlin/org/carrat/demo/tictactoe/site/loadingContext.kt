package org.carrat.demo.tictactoe.site

import org.carrat.context.Contextual
import org.carrat.context.HasContext
import org.carrat.flow.flow
import org.carrat.flow.property.property

val HasContext.isLoading by Contextual { flow.property(true) }