package com.example.budgee.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgee.ui.theme.BorderSubtle
import com.example.budgee.ui.theme.SheetModalBackground

/**
 * Shared bottom sheet shell for Budgee: fixed background color, standard
 * horizontal/bottom padding, and navigation-bar-aware spacing. Specific
 * sheets (settings, add transaction, etc.) provide their own [content].
 * Skips the "partially expanded" intermediate state so the sheet opens
 * straight to its full wrap-content height (or fully expanded if taller
 * than the screen), and the content column scrolls if it doesn't fit.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgeeBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        containerColor = SheetModalBackground,
        dragHandle = { BudgeeSheetDragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun BudgeeSheetDragHandle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(4.dp)
                .background(BorderSubtle, RoundedCornerShape(2.dp))
        )
    }
}